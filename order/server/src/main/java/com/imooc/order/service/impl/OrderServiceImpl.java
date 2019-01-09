package com.imooc.order.service.impl;

import com.imooc.order.dataObject.OrderDetail;
import com.imooc.order.dataObject.OrderMaster;
import com.imooc.order.dataObject.ProductInfo;
import com.imooc.order.dto.CartDto;
import com.imooc.order.dto.OrderDto;
import com.imooc.order.enums.OrderStatus;
import com.imooc.order.enums.PayStatus;
import com.imooc.order.repository.OrderDetailRepository;
import com.imooc.order.repository.OrderMasterRepository;
import com.imooc.order.service.OrderService;
import com.imooc.order.utils.KeyUtil;
import com.imooc.product.client.ProductClient;
import com.imooc.product.common.DecreaseStockInput;
import com.imooc.product.common.ProductInfoOutPut;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
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
		public OrderDto create(OrderDto orderDto) {
				String orderId = KeyUtil.genUniqueKey();
				//TODO  2 查询商品信息(调用商品服务)
				List<String> productIdList = orderDto.getOrderDetailList().stream().map(OrderDetail::getProductId)
						.collect(Collectors.toList());
				List<ProductInfoOutPut> productInfoOutPutList = productClient.listForOrder(productIdList);
				log.info("【商品信息】: productInfoList={}",productInfoOutPutList);
				//TODO 3 计算总价 根据接口暂时获取不了商品的全部信息，(需要调用商品服务去查询)
				BigDecimal orderAmount = new BigDecimal(0);
				for(OrderDetail orderDetail : orderDto.getOrderDetailList()){
						for (ProductInfoOutPut productInfoOutPut : productInfoOutPutList){
								if(productInfoOutPut.getProductId().equals(orderDetail.getProductId())){
										//单价*数量
										orderAmount =
												productInfoOutPut.getProductPrice().multiply(new BigDecimal(orderDetail.
														getProductQuantity())).add(orderAmount);
										BeanUtils.copyProperties(productInfoOutPut,orderDetail);
										orderDetail.setOrderId(orderId);
										orderDetail.setDetailId(KeyUtil.genUniqueKey());
										//订单详情入库
										orderDetailRepository.save(orderDetail);
								}
						}

				}
				//TODO 4 扣库存(调用商品服务)
				List<DecreaseStockInput> decreaseStockInputList = orderDto.getOrderDetailList().stream().
						map(e -> new DecreaseStockInput(e.getProductId(), e.getProductQuantity())).collect(Collectors.toList());
				productClient.decreaseStock(decreaseStockInputList);
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
}
