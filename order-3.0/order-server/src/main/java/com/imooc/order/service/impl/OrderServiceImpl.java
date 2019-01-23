package com.imooc.order.service.impl;

import com.imooc.order.client.ProductClient;
import com.imooc.order.dataObject.OrderDetail;
import com.imooc.order.dataObject.OrderMaster;
import com.imooc.order.dataObject.ProductInfo;
import com.imooc.order.dto.CartDto;
import com.imooc.order.dto.OrderDto;
import com.imooc.order.enums.OrderStatus;
import com.imooc.order.enums.PayStatus;
import com.imooc.order.enums.ResultEnum;
import com.imooc.order.exception.OrderException;
import com.imooc.order.repository.OrderDetailRepository;
import com.imooc.order.repository.OrderMasterRepository;
import com.imooc.order.service.OrderService;
import com.imooc.order.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
		@Autowired
		OrderDetailRepository orderDetailRepository;
		@Autowired
		OrderMasterRepository orderMasterRepository;
		@Autowired
		ProductClient productClient;

		@Override
		@Transactional
		public OrderDto create(OrderDto orderDto) {
				String orderId = KeyUtil.genUniqueKey();
				//TODO  2 查询商品信息(调用商品服务)
				List<String> productIdList = orderDto.getOrderDetailList().stream().map(OrderDetail::getProductId)
						.collect(Collectors.toList());
				List<ProductInfo> productInfoList = productClient.listForOrder(productIdList);
				log.info("【商品信息】: productInfoList={}",productInfoList);
				//TODO 3 计算总价 根据接口暂时获取不了商品的全部信息，(需要调用商品服务去查询)
				BigDecimal orderAmount = new BigDecimal(0);
				for(OrderDetail orderDetail : orderDto.getOrderDetailList()){
						for (ProductInfo productInfo : productInfoList){
								if(productInfo.getProductId().equals(orderDetail.getProductId())){
										//单价*数量
										orderAmount =
												productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.
														getProductQuantity())).add(orderAmount);
										BeanUtils.copyProperties(productInfo,orderDetail);
										orderDetail.setOrderId(orderId);
										orderDetail.setDetailId(KeyUtil.genUniqueKey());
										//订单详情入库
										orderDetailRepository.save(orderDetail);
								}
						}

				}
				//TODO 4 扣库存(调用商品服务)
				List<CartDto> cartDtoList = orderDto.getOrderDetailList().stream().
						map(e -> new CartDto(e.getProductId(), e.getProductQuantity())).collect(Collectors.toList());
				productClient.decreaseStock(cartDtoList);
				// 5 订单入库
				OrderMaster orderMaster = new OrderMaster();
				orderDto.setOrderId(orderId);
				BeanUtils.copyProperties(orderDto,orderMaster);

				orderMaster.setOrderAmount(orderAmount);
				orderMaster.setOrderStatus(OrderStatus.NEW.getStatus());
				orderMaster.setPayStatus(PayStatus.WAIT.getStatus());

				orderMasterRepository.save(orderMaster);
				return orderDto;
		}

    @Override
	@Transactional
    public OrderDto finish(String orderId) {
		//1 查询订单
		Optional<OrderMaster> orderOption = orderMasterRepository.findById(orderId);
		if(orderOption == null){
			throw new OrderException(ResultEnum.ORDER_NOT_EXIST);
		}
		//2 获得订单状态()只有新订单才可以完结
		OrderMaster orderMaster = orderOption.get();
		if(!OrderStatus.NEW.getStatus().equals(orderMaster.getOrderStatus())){
			throw new OrderException(ResultEnum.ORDER_STATUS_ERROR);
		}
		//3 修改订单状态
		orderMaster.setOrderStatus(OrderStatus.FINISHED.getStatus());
		orderMasterRepository.save(orderMaster);
		//4 获取orderDto 获取订单详情
		List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderMaster.getOrderId());
		if(CollectionUtils.isEmpty(orderDetailList)){
			throw new OrderException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
		}
		OrderDto orderDto = new OrderDto();
		BeanUtils.copyProperties(orderMaster,orderDto);
		orderDto.setOrderDetailList(orderDetailList);
		return orderDto;
    }
}
