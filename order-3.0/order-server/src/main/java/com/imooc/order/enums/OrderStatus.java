package com.imooc.order.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {
		NEW(0,"新订单"),
		FINISHED(1,"完结"),
		CANCEL(2,"取消"),
		;
		private Integer status;
		private String msg;

		OrderStatus(Integer status,String msg){
				this.msg=msg;
				this.status=status;
		}

}
