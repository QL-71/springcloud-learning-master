package com.example.architecturemaster.dao;

import com.example.architecturemaster.common.dao.LogicDAO;
import com.example.architecturemaster.entity.Orders;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderDao extends LogicDAO<Orders,Long> {
    @Query("select o from Orders o where o.customer.id = :customerId and o.shop.id = :shopId and o.orderState = '未支付'")
    List<Orders> getNotPayOrder(@Param(value = "customerId") Long customerId, @Param(value = "shopId") Long shopId);
}
