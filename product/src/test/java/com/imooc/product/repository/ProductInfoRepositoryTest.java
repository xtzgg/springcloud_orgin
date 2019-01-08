package com.imooc.product.repository;

import com.imooc.product.ProductApplicationTests;
import com.imooc.product.dataObject.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class ProductInfoRepositoryTest extends ProductApplicationTests {
		@Autowired
		ProductInfoRepository productInfoRepository;
		@Test
		public void findByProductStatus() {
				List<ProductInfo> byProductStatus = productInfoRepository.findByProductStatus(1);
				System.out.println(byProductStatus.toString());
				Assert.assertTrue(byProductStatus.size()>0);
		}
}
