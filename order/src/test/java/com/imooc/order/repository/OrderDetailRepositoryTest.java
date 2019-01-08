package com.imooc.order.repository;

import com.imooc.order.OrderApplicationTests;
import com.imooc.order.dataObject.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class OrderDetailRepositoryTest extends OrderApplicationTests {

	@Autowired
	private OrderDetailRepository orderDetailRepository;

	@Test
	public void testSave(){
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setDetailId("123456");
			orderDetail.setOrderId("12234343");
			orderDetail.setProductIcon("icon");
			orderDetail.setProductName("小菜熟");
			orderDetail.setProductId("8888888888");
			orderDetail.setProductPrice(new BigDecimal(0.01));
			orderDetail.setProductQuantity(2);
			OrderDetail result = orderDetailRepository.save(orderDetail);
			Assert.assertTrue(result!=null);
	}
}
