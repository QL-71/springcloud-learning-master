package com.example.architecturemaster.service;

import com.example.architecturemaster.common.service.LogicService;
import com.example.architecturemaster.dao.CartItemDao;
import com.example.architecturemaster.entity.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService extends LogicService<CartItemDao, CartItem,Long> {
    public CartService(@Autowired CartItemDao lr) {
        super(lr);
    }
}
