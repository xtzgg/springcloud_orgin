package com.imooc.product.service;

import com.imooc.product.common.DecreaseStockInput;
import com.imooc.product.common.ProductInfoOutPut;
import com.imooc.product.dataObject.ProductInfo;

import java.util.List;

public interface ProductService {
		/**
		 * 查询所有在架商品列表
		 */
		List<ProductInfo> findUpAll();
		/**
		 * 根据productIds查询商品列表
		 */
		List<ProductInfoOutPut> findProductListById(List<String> productIdList);

		/**
		 * 扣库存
		 */
		List<ProductInfoOutPut> decreaseStock(List<DecreaseStockInput> cartDtoList);

}
