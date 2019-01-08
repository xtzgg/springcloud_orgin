package com.imooc.order.repository;

import com.imooc.order.OrderApplicationTests;
import com.imooc.order.dataObject.OrderMaster;
import com.imooc.order.enums.OrderStatus;
import com.imooc.order.enums.PayStatus;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.*;

public class OrderMasterRepositoryTest extends OrderApplicationTests {
	@Autowired
	OrderMasterRepository orderMasterRepository;
	@Test
	public void saveTest(){
			OrderMaster orderMaster = new OrderMaster("12234343","小熊菜瓜","123456"
			,"杭州市","oiwaehwh12323jaa",new BigDecimal(23),OrderStatus.NEW.getStatus(),PayStatus.WAIT.getStatus(),null,null);
			OrderMaster orderMaster1 = orderMasterRepository.save(orderMaster);
			System.out.println(orderMaster1);
			Assert.assertTrue(orderMaster1!=null);

	}
}
