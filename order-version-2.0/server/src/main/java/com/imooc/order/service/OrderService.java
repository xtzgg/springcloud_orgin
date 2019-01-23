package com.imooc.order.service;

import com.imooc.order.dto.OrderDto;

public interface OrderService {
		//这里新建一个对象，统一两个对象传值为一个对象
		/**
		 * 创建订单
		 * @param orderDto
		 * @return
		 */
		OrderDto create(OrderDto orderDto);
}
