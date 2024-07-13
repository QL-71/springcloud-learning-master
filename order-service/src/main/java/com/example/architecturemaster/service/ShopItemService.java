package com.example.architecturemaster.service;

import com.example.architecturemaster.common.service.LogicService;
import com.example.architecturemaster.dao.ShopItemDao;
import com.example.architecturemaster.entity.ShopItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopItemService extends LogicService<ShopItemDao, ShopItem,Long> {
    public ShopItemService(@Autowired ShopItemDao lr) {
        super(lr);
    }
}
