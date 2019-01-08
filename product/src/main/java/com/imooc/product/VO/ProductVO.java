package com.imooc.product.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * 最外层返回对象包含的信息
 * 			避免把数据库映射的对象直接暴露出去，只暴露对应的字段内容
 */
@Data
public class ProductVO {

		@JsonProperty("name")//返回前端该字段就称为name，不影响语义
		private String categoryName;//实体类中定义对应的认识的名字，但是返回前端必须是name，则可以加个注解

		@JsonProperty("type")
		private Integer categoryType;

		@JsonProperty("foods")
		List<ProductInfoVO> productInfoVOList;

}
