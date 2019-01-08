package com.imooc.product.repository;

import com.imooc.product.ProductApplicationTests;
import com.imooc.product.dataObject.ProductCategory;
import com.imooc.product.dataObject.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ProductCategoryRepositoryTest extends ProductApplicationTests {
		@Autowired
		ProductCategoryRepository productCategoryRepository;
		@Test
		public void findByCategoryTypeIn() {
				List<ProductCategory> byCategoryTypeIn =
						productCategoryRepository.findByCategoryTypeIn(Arrays.asList( 1, 2, 3));
				System.out.println(byCategoryTypeIn);
				Assert.assertTrue(byCategoryTypeIn.size()>0);
		}
}
