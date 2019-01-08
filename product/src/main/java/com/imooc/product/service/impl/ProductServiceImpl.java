package com.imooc.product.service.impl;

import com.imooc.product.dataObject.ProductInfo;
import com.imooc.product.enums.ProductStatusEnum;
import com.imooc.product.repository.ProductInfoRepository;
import com.imooc.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductServiceImpl implements ProductService {
		@Autowired
		private ProductInfoRepository productInfoRepository;
		@Override
		public List<ProductInfo> findUpAll() {
				//枚举获取在架商品列表   //枚举使用要学习
				List<ProductInfo> byProductStatus =
						productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getStatus());
				return byProductStatus;
		}
}
