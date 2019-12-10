package com.alibaba.cloud.examples;

/**
 * @author wangjihuan
 * @date 2019/11/22.
 */
public interface OrderService {

    String order(String userId, String commodityCode, int orderCount);

}
