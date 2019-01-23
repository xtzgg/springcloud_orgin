package com.imooc.order.converter;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.imooc.order.dataObject.OrderDetail;
import com.imooc.order.dto.OrderDto;
import com.imooc.order.enums.ResultEnum;
import com.imooc.order.exception.OrderException;
import com.imooc.order.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 转换json
 */
@Slf4j
public class OrderFrom2OrderDto {
		public static OrderDto convert(OrderForm orderForm){
				OrderDto orderDto = new OrderDto();
				orderDto.setBuyerName(orderForm.getName());
				orderDto.setBuyerPhone(orderForm.getPhone());
				orderDto.setBuyerAddress(orderForm.getAddress());
				orderDto.setBuyerOpenid(orderForm.getOpenid());
				List<OrderDetail> orderDetailList = new ArrayList<>();
				Gson gson = new Gson();
				try {
						orderDetailList = gson.fromJson(orderForm.getItems(), new TypeToken<List<OrderDetail>>() {
						}.getType());
				} catch (JsonSyntaxException e) {
						log.error("【json转换错误】，string={}",orderForm.getItems());
						throw new OrderException(ResultEnum.PARAM_ERROR);
				}
				orderDto.setOrderDetailList(orderDetailList);
				return orderDto;
		}
}
