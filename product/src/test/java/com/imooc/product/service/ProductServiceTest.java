package com.imooc.product.service;

import com.imooc.product.ProductApplicationTests;
import com.imooc.product.dataObject.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.AssertTrue;
import java.util.List;

import static org.junit.Assert.*;

public class ProductServiceTest extends ProductApplicationTests {
		@Autowired
		ProductService productService;
		@Test
		public void findUpAll() {
				List<ProductInfo> upAll = productService.findUpAll();
				System.out.println(upAll.toString());
				Assert.assertTrue(upAll.size()>0);
		}
}
