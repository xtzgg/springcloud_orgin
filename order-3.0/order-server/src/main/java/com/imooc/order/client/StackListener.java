package com.imooc.order.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.imooc.order.utils.JSONUtil;
import com.imooc.product.common.ProductInfoOutPut;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 接收到消息并存到redis里面去,使用springboot方式做
 */
@Component
@Slf4j
public class StackListener {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    private static final String PRODUCT_STACK_TEMPLATE = "product_stack_%s";


    @RabbitListener(bindings = @QueueBinding(
           value=@Queue(value = "order_stack",durable = "true",autoDelete = "false",exclusive = "false"),
           exchange = @Exchange(value="productInfo",type="topic"),
            key = "product_order_routing.#"
    ))
    @RabbitHandler
    public void getStack_product(Message message, Channel channel){
        String payload =(String) message.getPayload();
        List<ProductInfoOutPut> list= (List<ProductInfoOutPut>)JSONUtil.fromJSONList(payload, new TypeReference<List<ProductInfoOutPut>>() {
        });
        log.info("从队列中获取商品订单购物车为：{}",list);
        //将库存存入redis里面
        String sss = "";
        for(ProductInfoOutPut productInfoOutPut : list){
            stringRedisTemplate.opsForValue().set(
                    String.format(PRODUCT_STACK_TEMPLATE,productInfoOutPut.getProductId()),
                    String.valueOf(productInfoOutPut.getProductStock()));
            sss=String.format(PRODUCT_STACK_TEMPLATE,productInfoOutPut.getProductId());
        }
        String s = stringRedisTemplate.opsForValue().get(sss);
        log.info("从redis中获取成功数据为：{}",s);
    }
}
