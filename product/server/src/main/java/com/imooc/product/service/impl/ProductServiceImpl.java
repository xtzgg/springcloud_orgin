package com.imooc.product.service.impl;

import com.imooc.product.common.DecreaseStockInput;
import com.imooc.product.common.ProductInfoOutPut;
import com.imooc.product.dataObject.ProductInfo;
import com.imooc.product.enums.ProductStatusEnum;
import com.imooc.product.enums.ResultEnum;
import com.imooc.product.exception.ProductException;
import com.imooc.product.repository.ProductInfoRepository;
import com.imooc.product.service.ProductService;
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
				List<ProductInfoOutPut> outPut = byProductIdIn.stream().map(e -> {
						ProductInfoOutPut productInfoOutPut = new ProductInfoOutPut();
						BeanUtils.copyProperties(e, productInfoOutPut);
						return productInfoOutPut;
				}).collect(Collectors.toList());
				return outPut;
		}

		@Override
		public List<ProductInfoOutPut> decreaseStock(List<DecreaseStockInput> cartDtoList) {
				List<ProductInfo> productInfoList = decreaseStockProcess(cartDtoList);
				return productInfoList.stream().map(e->{
						ProductInfoOutPut productInfoOutPut = new ProductInfoOutPut();
						BeanUtils.copyProperties(e,productInfoOutPut);
						return productInfoOutPut;
				}).collect(Collectors.toList());
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
