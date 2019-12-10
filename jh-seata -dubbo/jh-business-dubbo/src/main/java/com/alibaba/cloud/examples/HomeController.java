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

import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author xiaojing
 */
@RestController
public class HomeController {

	private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

	private static final String SUCCESS = "SUCCESS";
	private static final String FAIL = "FAIL";
	private static final String USER_ID = "U100001";
	private static final String COMMODITY_CODE = "C00321";
	private static final int ORDER_COUNT = 2;

	@Resource
	private  RestTemplate restTemplate;
	@Lazy
	@Reference(version = "1.0.0",protocol = "dubbo")
	private  OrderService orderService;
	@Lazy
	@Reference(version = "1.0.0",protocol = "dubbo")
	private  StorageService storageService;

//	@GlobalTransactional(timeoutMills = 300000, name = "spring-cloud-demo-tx")
	@RequestMapping(value = "/seata/dubbo", method = RequestMethod.GET, produces = "application/json")
	public String feign() {

		String result = storageService.echo(COMMODITY_CODE, ORDER_COUNT);

		if (!SUCCESS.equals(result)) {
			throw new RuntimeException();
		}

		result = orderService.order(USER_ID, COMMODITY_CODE, ORDER_COUNT);

		if (!SUCCESS.equals(result)) {
			throw new RuntimeException();
		}

		return SUCCESS;
	}

}
