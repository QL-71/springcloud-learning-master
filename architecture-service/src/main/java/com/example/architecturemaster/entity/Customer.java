package com.example.architecturemaster.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

@Entity
@Getter
@Setter
@DiscriminatorValue("1")
@Where(clause = "is_deleted = 0")
public class Customer extends User{

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "customer",orphanRemoval = true)
    @JsonIgnoreProperties(value = {"customer"})
    Set<Orders> orders;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "customer",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnoreProperties(value = {"customer"})
    private Set<DeliveryAddress> deliveryAddresses;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "customer",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnoreProperties(value = {"customer"})
    private Set<CartItem> cartItems;
    @JsonIgnore
    public CartItem selectShopItem(ShopItem shopItem, Integer quantity){
        if (quantity == 0 ) return removeSelectedItem(shopItem);
        AtomicReference<CartItem> result = new AtomicReference<>(null);
        cartItems.forEach(cart -> {
            if (cart.getShopItem().equals(shopItem)) {
                cart.setQuantity(quantity);
                result.set(cart);
            }
        });
        if (result.get() == null){
            CartItem cartItem = new CartItem();
            cartItem.setCustomer(this);
            cartItem.setQuantity(quantity);
            cartItem.setShopItem(shopItem);
            cartItem.setShop(shopItem.getShop());
            cartItems.add(cartItem);
            result.set(cartItem);
        }
        return result.get();
    }

    @JsonIgnore
    public List<CartItem> listMyCarts(Shop shop){
        List<CartItem> results = new ArrayList<>();
        cartItems.forEach(cart -> {
            if (cart.getShop().equals(shop)) {
                results.add(cart);
            }
        });
        return results;
    }

    @JsonIgnore
    public DeliveryAddress createDeliveryAddress(DeliveryAddress deliveryAddress){
        deliveryAddress.setCustomer(this);
        deliveryAddresses.add(deliveryAddress);
        return deliveryAddress;
    }

    @JsonIgnore
    public Orders payOrder(Orders order){
        for (Orders order1 : orders) {
            if (order1.equals(order)){
                order1.setOrderState("已支付");
                return order1;
            }
        }
        return null;
    }

    @JsonIgnore
    public CartItem removeSelectedItem(ShopItem shopItem){
        AtomicReference<CartItem> result = new AtomicReference<>(null);
        cartItems.forEach(cart -> {
            if (cart.getShopItem().equals(shopItem)) {
                cartItems.remove(cart);
                result.set(cart);
            }
        });
        return result.get();
    }

    @JsonIgnore
    public List<CartItem> clearCarts(Shop shop){
        List<CartItem> results = new ArrayList<>();
        cartItems.forEach(cart -> {
            if (cart.getShop().equals(shop)) {
                results.add(cart);
                cartItems.remove(cart);
            }
        });
        return results;
    }
}
