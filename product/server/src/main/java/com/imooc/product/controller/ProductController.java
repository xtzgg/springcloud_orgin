package com.imooc.product.controller;

import com.imooc.product.common.DecreaseStockInput;
import com.imooc.product.common.ProductInfoOutPut;
import com.imooc.product.VO.ProductInfoVO;
import com.imooc.product.VO.ProductVO;
import com.imooc.product.VO.ResultVO;
import com.imooc.product.dataObject.ProductCategory;
import com.imooc.product.dataObject.ProductInfo;
import com.imooc.product.service.ProductCategoryService;
import com.imooc.product.service.ProductService;
import com.imooc.product.utils.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 *
 * 统一标记为   xin-xue
 * 新的写法需要学习：List获取
 */
@RestController
@RequestMapping("/product")
public class ProductController {
		@Autowired
		private ProductService productService;

		@Autowired
		private ProductCategoryService productCategoryService;

		/**
		 * //1查询所有在架的商品 status=1
		 * //2获取类目type列表
		 * //3查询类目
		 * //4构造数据
		 */
		@GetMapping("/list")
		public ResultVO<ProductVO> productList(){
				//1查询所有在架的商品 status=1
				List<ProductInfo> productInfoList = productService.findUpAll();
				//新的写法需要学习：List获取   xin-xue
				//将type取出来，放入到一个list里面

				//2获取类目type列表
				List<Integer> categoryTypeList =
						productInfoList.stream().
								map(ProductInfo::getCategoryType).
								collect(Collectors.toList());
				//3查询类目
				List<ProductCategory> categoryList = productCategoryService.findByCategoryTypeIn(categoryTypeList);

				//4构造数据
				ArrayList<ProductVO> productVOList = new ArrayList<>();
				for(ProductCategory productCategory : categoryList){
						ProductVO productVO = new ProductVO();
						productVO.setCategoryName(productCategory.getCategoryName());
						productVO.setCategoryType(productCategory.getCategoryType());
						List<ProductInfoVO> productInfoVOList = new ArrayList<>();
						for (ProductInfo productInfo : productInfoList){
								if(productInfo.getCategoryType().equals(productCategory.getCategoryType())){
										ProductInfoVO productInfoVO = new ProductInfoVO();
		//								productInfoVO.setProductId(productInfo.getProductId());
		//								productInfoVO.setProductName(productInfo.getProductName());
		//								productInfoVO.setProductPrice(productInfo.getProductPrice());
		//								productInfoVO.setProductDescription(productInfo.getProductDescription());
		//								productInfoVO.setProductIcon(productInfo.getProductIcon());
										BeanUtils.copyProperties(productInfo,productInfoVO);//简写拷贝    xin-xue
										productInfoVOList.add(productInfoVO);
								}
						}
						productVO.setProductInfoVOList(productInfoVOList);
						productVOList.add(productVO);
				}

				return ResultVOUtil.success(productVOList);
		}

		/**
		 * 获取商品列表(给订单服务用的)
		 * @param productIdList
		 * @return
		 */
		@PostMapping("/listForOrder")
		public List<ProductInfoOutPut> listForOrder(@RequestBody List<String> productIdList){
//			try { //检测hystrix性能
//				Thread.sleep(2000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
			return productService.findProductListById(productIdList);
		}
		/**
		 * 扣库存
		 */
		@PostMapping("/decreaseStock")
		public void decreaseStock(@RequestBody List<DecreaseStockInput> cartDtoList){
				productService.decreaseStock(cartDtoList);
		}
}
