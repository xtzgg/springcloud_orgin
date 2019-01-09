package com.imooc.product.client;


import com.imooc.product.common.DecreaseStockInput;
import com.imooc.product.common.ProductInfoOutPut;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

//name指定我调用的是哪个服务
@Component
@FeignClient(name = "product",fallback =ProductClient.ProductClientCallBack.class )
public interface ProductClient {
		@GetMapping("/msg") //声明调用该服务(应用的)接口名字
		String productMsg();

		/**
		 * 获取商品列表(给订单服务用的)
		 * @param productIdList
		 * @return
		 */
		@PostMapping("/product/listForOrder")
		List<ProductInfoOutPut> listForOrder(@RequestBody List<String> productIdList);

		/**
		 * 扣库存
		 */
		@PostMapping("/product/decreaseStock")
		void decreaseStock(@RequestBody List<DecreaseStockInput> cartDtoList);


		@Component
		static class ProductClientCallBack implements ProductClient{

				@Override
				public String productMsg() {
						return null;
				}

				@Override
				public List<ProductInfoOutPut> listForOrder(List<String> productIdList) {
						return null;
				}

				@Override
				public void decreaseStock(List<DecreaseStockInput> cartDtoList) {

				}
		}
}
