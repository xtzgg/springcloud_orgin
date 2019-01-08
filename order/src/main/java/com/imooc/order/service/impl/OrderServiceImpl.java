package com.imooc.order.service.impl;

import com.imooc.order.dataObject.OrderMaster;
import com.imooc.order.dto.OrderDto;
import com.imooc.order.enums.OrderStatus;
import com.imooc.order.enums.PayStatus;
import com.imooc.order.repository.OrderDetailRepository;
import com.imooc.order.repository.OrderMasterRepository;
import com.imooc.order.service.OrderService;
import com.imooc.order.utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderServiceImpl implements OrderService {
		@Autowired
		OrderDetailRepository orderDetailRepository;
		@Autowired
		OrderMasterRepository orderMasterRepository;
		@Override
		public OrderDto create(OrderDto orderDto) {
				//TODO  2 查询商品信息(调用商品服务)
				//TODO 3 计算总价 根据接口暂时获取不了商品的全部信息，(需要调用商品服务去查询)
				//TODO 4 扣库存(调用商品服务)
				// 5 订单入库
				OrderMaster orderMaster = new OrderMaster();
				orderDto.setOrderId(KeyUtil.genUniqueKey());
				BeanUtils.copyProperties(orderDto,orderMaster);

				orderMaster.setOrderAmount(new BigDecimal(5));
				orderMaster.setOrderStatus(OrderStatus.NEW.getStatus());
				orderMaster.setPayStatus(PayStatus.WAIT.getStatus());

				orderMasterRepository.save(orderMaster);
				return null;
		}
}
