package com.imooc.product.service;

import com.imooc.product.dataObject.ProductCategory;

import java.util.List;

public interface ProductCategoryService {
		List<ProductCategory> findByCategoryTypeIn(List<Integer> productCategoryTypeList);
}
