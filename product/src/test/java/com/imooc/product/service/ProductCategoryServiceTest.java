package com.imooc.product.service;

import com.imooc.product.ProductApplicationTests;
import com.imooc.product.dataObject.ProductCategory;
import com.imooc.product.repository.ProductCategoryRepository;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ProductCategoryServiceTest extends ProductApplicationTests {
		@Autowired
		ProductCategoryService productCategoryService;
		@Test
		public void findByCategoryTypeIn() {
				List<ProductCategory> byCategoryTypeIn =
						productCategoryService.findByCategoryTypeIn(Arrays.asList(1, 2, 3));
				System.out.println(byCategoryTypeIn.toString());
				Assert.assertTrue(byCategoryTypeIn.size()>0);
		}
}
