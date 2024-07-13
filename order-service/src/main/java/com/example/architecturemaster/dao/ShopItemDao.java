package com.example.architecturemaster.dao;

import com.example.architecturemaster.common.dao.LogicDAO;
import com.example.architecturemaster.entity.ShopItem;
import org.springframework.transaction.annotation.Transactional;

public interface ShopItemDao extends LogicDAO<ShopItem,Long> {
    @Transactional
    Integer deleteAllByItemId(Long itemId);
}
