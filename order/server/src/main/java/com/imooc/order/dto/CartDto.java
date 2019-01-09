package com.imooc.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
		/**
		 * 商品id
		 */
		private String productId;
		/**
		 * 商品数量
		 */
		private Integer productQuantity;
}
