package com.imooc.product.enums;

import lombok.Getter;

/**
 * 商品上下架状态：可以学习枚举类的接不上使用和原理
 */
@Getter
public enum ProductStatusEnum {
		UP(0,"在架"),DOWN(1,"下架");
		private Integer status;
		private String message;
		ProductStatusEnum(Integer status,String message){
			this.message=message;
			this.status=status;
		}
}
