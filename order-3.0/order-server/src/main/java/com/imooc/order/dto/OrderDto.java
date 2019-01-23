package com.imooc.order.dto;

import com.imooc.order.dataObject.OrderDetail;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 数据传输对象：考虑将detial和主表master集合成一个对象，对象处理包
 */
@Data
public class OrderDto {
		/** 订单id. */
		private String orderId;

		/** 买家名字. */
		private String buyerName;

		/** 买家手机号. */
		private String buyerPhone;

		/** 买家地址. */
		private String buyerAddress;

		/** 买家微信Openid. */
		private String buyerOpenid;

		/** 订单总金额. */
		private BigDecimal orderAmount;

		/** 订单状态, 默认为0新下单. */
		private Integer orderStatus;

		/** 支付状态, 默认为0未支付. */
		private Integer payStatus;

		//由于master跟detail是一对多的关系，所以可以设置一个list
		private List<OrderDetail> orderDetailList;
}
