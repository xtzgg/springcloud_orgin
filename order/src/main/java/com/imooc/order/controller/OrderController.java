package com.imooc.order.controller;

import com.imooc.order.VO.ResultVO;
import com.imooc.order.converter.OrderFrom2OrderDto;
import com.imooc.order.dto.OrderDto;
import com.imooc.order.enums.ResultEnum;
import com.imooc.order.exception.OrderException;
import com.imooc.order.form.OrderForm;
import com.imooc.order.service.OrderService;
import com.imooc.order.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {
		@Autowired
		private OrderService orderService;
		/**
		 * 1参数检验
		 * 2 查询商品信息(调用商品服务)
		 * 3 计算总价
		 * 4 扣库存(调用商品服务)
		 * 5 订单入库
		 */
		public ResultVO create(@Valid OrderForm orderForm,BindingResult bindingResult){
				//1参数检验
				if(bindingResult.hasErrors()){
				log.error("【创建订单】参数不正确，orderForm={}",orderForm);
				throw new OrderException(ResultEnum.PARAM_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
				}
				//orderForm 转换 orderDto 写一个工具类
				OrderDto orderDto = OrderFrom2OrderDto.convert(orderForm);
				if(CollectionUtils.isEmpty(orderDto.getOrderDetailList())){
				log.error("【订单信息】购物车信息不能为空");
				throw new OrderException(ResultEnum.CART_EMPTY);
				}
				OrderDto result = orderService.create(orderDto);
				Map<String,String> map = new HashMap<>();
				map.put("orderId",result.getOrderId());
				return ResultVOUtil.success(map);
		}
}










