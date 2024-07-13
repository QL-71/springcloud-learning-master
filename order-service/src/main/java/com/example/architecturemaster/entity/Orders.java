package com.example.architecturemaster.entity;

import com.example.architecturemaster.common.entity.LogicEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Where(clause = "is_deleted = 0")
public class Orders extends LogicEntity {
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "order",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnoreProperties(value = {"order"})
    private Set<LineItem> lineItems;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"orders"})
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"orders"})
    private DeliveryAddress deliveryAddress;

    @Column
    private Double orderTotal;

    @Column
    private String orderState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"orders"})
    private Shop shop;

    public Orders(){
        this.orderState = "未支付";
        this.lineItems = new HashSet<>();
    }

    @JsonIgnore
    public Orders createOder(List<CartItem> cartItems, DeliveryAddress deliveryAddress, Double orderTotal){
        if (!cartItems.isEmpty()){
            this.setShop(cartItems.get(0).getShop());
            this.setCustomer(cartItems.get(0).getCustomer());
            this.setDeliveryAddress(deliveryAddress);
            this.setOrderTotal(orderTotal);
            cartItems.forEach(cartItem -> {
                LineItem lineItem = new LineItem();
                lineItem.setShopItem(cartItem.getShopItem());
                lineItem.setQuantity(cartItem.getQuantity());
                lineItem.setOrder(this);
                lineItems.add(lineItem);
            });
        }
        return this;
    }
}
