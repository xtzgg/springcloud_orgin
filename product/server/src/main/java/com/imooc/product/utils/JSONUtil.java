package com.imooc.product.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * json转换工具类
 */
public class JSONUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String toJSON(Object data){
        try {
            String json = objectMapper.writeValueAsString(data);
            return json;
        } catch (JsonProcessingException e) {
           return null;
        }
    }
    public static Object fromJSON(String json, Class classType){
        try {
            Object o = objectMapper.readValue(json, classType);
            return o;
        } catch (IOException e) {
            return null;
        }
    }
    public static Object fromJSONList(String json, TypeReference typeReference){
        try {
            Object o = objectMapper.readValue(json,typeReference);
            return o;
        } catch (IOException e) {
            return null;
        }
    }
}
