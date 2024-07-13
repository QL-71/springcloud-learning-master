package com.example.architecturemaster.dao;

import com.example.architecturemaster.common.dao.LogicDAO;
import com.example.architecturemaster.entity.Shop;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShopDao extends LogicDAO<Shop,Long> {
    @Query("SELECT shop FROM Shop shop WHERE shop.shopName LIKE %:keywordName% OR EXISTS " +
            "(SELECT shopItem FROM ShopItem shopItem WHERE " +
            "shopItem.shop = shop AND shopItem.item.itemName LIKE %:keywordName% and shopItem.state = true )" +
            "and (:businessId is null or shop.business.id = :businessId)")
    List<Shop> searchShops(@Param("keywordName") String keywordName,@Param("businessId") Long businessId);
}
