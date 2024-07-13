package com.example.architecturemaster.entity;

import com.example.architecturemaster.common.entity.LogicEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

@Entity
@Getter
@Setter
@Where(clause = "is_deleted = 0")
public class Shop extends LogicEntity {

    @Column(nullable = false)
    private String shopName;

    @Column
    private String shopImg;

    @Column
    private String shopTelephone;

    @Column
    private Float startPrice;

    @Column
    private Float deliveryPrice;

    @Column
    private String shopAddress;

    @Column
    private String shopExplain; //介绍

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"shops"})
    private Business business;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "shop",
            cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnoreProperties(value = {"shop"})
    private Set<CartItem> cartItems;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "shop",
            cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnoreProperties(value = {"shop"})
    private Set<ShopItem> shopItems;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "shop")
    @JsonIgnoreProperties(value = {"shop"})
    private Set<Orders> orders;

    @JsonIgnore
    public List<Orders> listNoHandledOrders(){
        List<Orders> results = new ArrayList<>();
        orders.forEach(orders1 -> {
            if (Objects.equals(orders1.getOrderState(), "已支付")) {
                results.add(orders1);
            }
        });
        return results;
    }

    @JsonIgnore
    public Orders confirmOrders(Orders order){
        AtomicReference<Orders> order2 = new AtomicReference<>(null);
        orders.forEach(orders1 -> {
            if (orders1.equals(order)) {
                orders1.setOrderState("已完成");
                order2.set(orders1);
            }
        });
        return order2.get();
    }

    @JsonIgnore
    public Orders cancelOrders(Orders order){
        AtomicReference<Orders> order2 = new AtomicReference<>(null);
        orders.forEach(orders1 -> {
            if (orders1.equals(order)) {
                orders1.setOrderState("已退款");
                order2.set(orders1);
            }
        });
        return order2.get();
    }

    @JsonIgnore
    public ShopItem putItem(Item item,Double shopItemPrice){
        if (!business.hasItem(item)) return null;
        ShopItem shopItem = new ShopItem();
        shopItem.setItem(item);
        shopItem.setShop(this);
        shopItem.setShopItemPrice(shopItemPrice);
        shopItems.add(shopItem);
        return shopItem;
    }

    @JsonIgnore
    public ShopItem changeShopItemState(ShopItem shopItem){
        for (ShopItem item : shopItems) {
            if (item.equals(shopItem)){
                item.setState(!item.getState());
                return shopItem;
            }
        }
        return null;
    }
    


}
