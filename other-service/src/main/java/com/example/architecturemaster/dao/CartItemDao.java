package com.example.architecturemaster.dao;

import com.example.architecturemaster.common.dao.LogicDAO;
import com.example.architecturemaster.entity.CartItem;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CartItemDao extends LogicDAO<CartItem,Long> {
    List<CartItem> findAllByCustomerIdAndShopId(Long customerId, Long shopId);

    @Modifying
    @Transactional
    void removeAllByCustomerIdAndShopId(Long customerId,Long shopId);

}
