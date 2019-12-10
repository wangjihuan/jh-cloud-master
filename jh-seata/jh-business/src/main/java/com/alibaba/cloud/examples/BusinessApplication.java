/*
 * Copyright (C) 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.cloud.examples;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

/**
 * @author xiaojing
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class BusinessApplication {

    public static void main(String[] args) {
        SpringApplication.run(BusinessApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    //支持dubbo的feign调用
    @FeignClient(value = "jh-storage")
    public interface StorageService {

        @RequestMapping(path = "/storage/{commodityCode}/{count}")
        String storage(@PathVariable("commodityCode") String commodityCode,
                       @PathVariable("count") int count);

    }

    //支持dubbo的feign调用
    @FeignClient(value = "jh-order")
    public interface OrderService {

        @RequestMapping(path = "/order", method = RequestMethod.POST)
        String order(@RequestParam("userId") String userId,
                     @RequestParam("commodityCode") String commodityCode,
                     @RequestParam("orderCount") int orderCount);

    }
}
