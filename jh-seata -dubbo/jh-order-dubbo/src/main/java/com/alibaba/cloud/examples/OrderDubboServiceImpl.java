package com.alibaba.cloud.examples;

import io.seata.core.context.RootContext;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

/**
 * @author wangjihuan
 * @date 2019/11/22.
 */
@Service(version = "1.0.0",protocol = "dubbo")
public class OrderDubboServiceImpl implements OrderService{

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    private static final String SUCCESS = "SUCCESS";
    private static final String FAIL = "FAIL";
    private static final String USER_ID = "U100001";
    private static final String COMMODITY_CODE = "C00321";

    @Resource
    private  JdbcTemplate jdbcTemplate;
    private Random random = new Random();

    @Override
    public String order(String userId, String commodityCode, int orderCount) {

        LOGGER.info("Order Service Begin ... xid: " + RootContext.getXID());

        int orderMoney = calculate(commodityCode, orderCount);

//        invokerAccountService(orderMoney);

        final Order order = new Order();
        order.userId = userId;
        order.commodityCode = commodityCode;
        order.count = orderCount;
        order.money = orderMoney;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        int result = jdbcTemplate.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection con)
                    throws SQLException {
                PreparedStatement pst = con.prepareStatement(
                        "insert into order_tbl (user_id, commodity_code, count, money) values (?, ?, ?, ?)",
                        PreparedStatement.RETURN_GENERATED_KEYS);
                pst.setObject(1, order.userId);
                pst.setObject(2, order.commodityCode);
                pst.setObject(3, order.count);
                pst.setObject(4, order.money);
                return pst;
            }
        }, keyHolder);

        order.id = keyHolder.getKey().longValue();

        if (random.nextBoolean()) {
            throw new RuntimeException("this is a mock Exception");
        }

        LOGGER.info("Order Service End ... Created " + order);

        if (result == 1) {
            return SUCCESS;
        }
        return FAIL;
    }


    private int calculate(String commodityId, int orderCount) {
        return 2 * orderCount;
    }

}
