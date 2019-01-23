package com.imooc.product.service.impl;

import com.imooc.product.common.DecreaseStockInput;
import com.imooc.product.common.ProductInfoOutPut;
import com.imooc.product.dataObject.ProductInfo;
import com.imooc.product.enums.ProductStatusEnum;
import com.imooc.product.enums.ResultEnum;
import com.imooc.product.exception.ProductException;
import com.imooc.product.repository.ProductInfoRepository;
import com.imooc.product.service.ProductService;
import com.imooc.product.utils.JSONUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

		@Autowired
		private ProductInfoRepository productInfoRepository;

		@Autowired
		private RabbitTemplate rabbitTemplate;

		@Override
		public List<ProductInfo> findUpAll() {
				//枚举获取在架商品列表   //枚举使用要学习
				List<ProductInfo> byProductStatus =
						productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getStatus());
				return byProductStatus;
		}

		@Override
		public List<ProductInfoOutPut> findProductListById(List<String> productIdList) {

				List<ProductInfo> byProductIdIn = productInfoRepository.findByProductIdIn(productIdList);
			List<ProductInfoOutPut> productInfoOutPutList = byProductIdIn.stream().map(e -> {
				ProductInfoOutPut productInfoOutPut = new ProductInfoOutPut();
				BeanUtils.copyProperties(e, productInfoOutPut);
				return productInfoOutPut;
			}).collect(Collectors.toList());
			return productInfoOutPutList;
		}

		@Override
		public void decreaseStock(List<DecreaseStockInput> cartDtoList) {
			//一般在商品服务扣完库存之后再给订单服务发送mq消息，防止中间有异常的情况还进行扣库存
			List<ProductInfo> productInfoList = decreaseStockProcess(cartDtoList);
			List<ProductInfoOutPut> productInfoOutPutList = productInfoList.stream().map(e -> {
				ProductInfoOutPut productInfoOutPut = new ProductInfoOutPut();
				BeanUtils.copyProperties(e, productInfoOutPut);
				return productInfoOutPut;
			}).collect(Collectors.toList());
			//成功扣完库存，将该购物车通过rabbitmq返回返回
			rabbitTemplate.convertAndSend("productInfo","product_order_routing.stack",
					JSONUtil.toJSON(productInfoOutPutList));
		}

		@Transactional
		public List<ProductInfo> decreaseStockProcess(List<DecreaseStockInput> cartDtoList) {
				ArrayList<ProductInfo> productInfoList = new ArrayList<>();
				for(DecreaseStockInput decreaseStockInput : cartDtoList){
						Optional<ProductInfo> byId = productInfoRepository.findById(decreaseStockInput.getProductId());
						//查询商品是否存在
						if(!byId.isPresent()){
								throw new ProductException(ResultEnum.PRODUCT_NOT_EXIST);
						}
						ProductInfo productInfo = byId.get();
						Integer stock = productInfo.getProductStock() - decreaseStockInput.getProductQuantity();
						//库存是否足量
						if(stock<0){
								throw new ProductException(ResultEnum.PRODUCT_STOCK_ERROR);
						}
						//修改库存，并入库
						productInfo.setProductStock(stock);
						productInfoRepository.save(productInfo);
						productInfoList.add(productInfo);
				}
			return productInfoList;
		}

}
