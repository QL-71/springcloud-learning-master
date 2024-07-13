package com.example.architecturemaster.controller;

import com.example.architecturemaster.common.controller.LogicController;
import com.example.architecturemaster.dao.CartItemDao;
import com.example.architecturemaster.entity.CartItem;
import com.example.architecturemaster.service.CartService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/CartController")
@Tag(name = "购物车服务接口")
public class CartItemController extends LogicController< CartService, CartItemDao, CartItem,Long> {
    public CartItemController(@Autowired CartService ls) {
        super(ls);
    }

}
