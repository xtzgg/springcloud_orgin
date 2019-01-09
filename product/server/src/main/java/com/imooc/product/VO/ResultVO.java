package com.imooc.product.VO;

import lombok.Data;

/**
 * http请求返回的最外层对象：最外层返回页面的统一状态结果信息
 */
@Data
public class ResultVO<T> {
		/**
		 * 错误码
		 */
		private Integer code;
		/**
		 * 提示信息
		 */
		private String msg;
		/**
		 * 具体内容
		 */
		private T data;


}
