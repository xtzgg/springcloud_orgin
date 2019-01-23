package com.imooc.order.controller;

import com.imooc.order.client.ProductClient;
import com.imooc.order.config.RestTemplateConfig;
import com.imooc.order.dataObject.ProductInfo;
import com.imooc.order.dto.CartDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * 由于讲foreign 所以该类废除，保留后续做笔记查看
 */
@RestController
@Slf4j
public class ClientController {
/*		@Autowired
		private LoadBalancerClient loadBalancerClient;*/
//		@Autowired
//		private RestTemplate restTemplate;

		@Autowired
		ProductClient productClient;

		@GetMapping("/getProductMsg")
		public String getProductMsg(){
				//1 第一种方式：直接使用restTemplate，url写死
				/**	缺点：url写死
				 *  	  配置多个服务地址，该如何访问
				 *  	  		这个时候使用第二种方式
				 */
//				RestTemplate restTemplate = new RestTemplate();
//				String response = restTemplate.getForObject("http://localhost:2001/msg", String.class);

				//2 第二种方式 负载均衡调用其中一台服务：通过应用名获取端口号，然后再使用restTemplate
						//缺点：书写复杂，每次调用都得这样去写
//				RestTemplate restTemplate = new RestTemplate();
//				ServiceInstance serviceInstance = loadBalancerClient.choose("product");
//				String url =
//						String.format("http://%s:%s", serviceInstance.getHost(), serviceInstance.getPort()+"/msg");
//				String response = restTemplate.getForObject(url, String.class);

				//3.第三种方式：通过配置文件，将对象写入配置文件：
						//应用@LoadBalanced注解，可在restTemplate中使用应用名字product
//				String response = restTemplate.getForObject("http://product/msg", String.class);

				//4 使用feign来做
				String response = productClient.productMsg();

				log.info("response={}",response);
				return response;
		}

		/**
		 * 获取商品列表(给订单服务用的)
		 */
		@GetMapping("/productList")
		public String getProductList(){
				List<ProductInfo> productInfos = productClient.listForOrder(Arrays.asList("13243nfweigwe32323"));
				log.info("【商品信息】: productInfos={}",productInfos);
				return "ok";
		}

		@GetMapping("/decreaseStock")
		public String decreaseStock(){
				CartDto cartDto = new CartDto("13243nfweigwe32324", 3);
				productClient.decreaseStock(Arrays.asList(cartDto));
				return "ok decreaseStock success";
		}
}
/**
 * 三种方式都是通过restTemplate来调用，
 * 		后两种使用了负载均衡
 */


