package com.alibaba.cloud.examples;


import io.seata.core.context.RootContext;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;

@Service(version = "1.0.0",protocol = "dubbo")
public class StorageServiceImpl implements StorageService{

    @Resource
    private JdbcTemplate jdbcTemplate;

    private static final String SUCCESS = "SUCCESS";
    private static final String FAIL = "FAIL";

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    public String echo(String commodityCode, int count) {
        LOGGER.info("Storage Service Begin ... xid: " + RootContext.getXID());
        int result = jdbcTemplate.update(
                "update storage_tbl set count = count - ? where commodity_code = ?",
                new Object[]{count, commodityCode});
        LOGGER.info("Storage Service End ... ");
        if (result == 1) {
            return SUCCESS;
        }
        return FAIL;
    }
}
