package com.example.architecturemaster.entity;

import com.example.architecturemaster.common.entity.LogicEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Where(clause = "is_deleted = 0")
public class ShopItem extends LogicEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"shopItems"})
    private Shop shop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"shopItems"})
    private Item item;

    @Column
    private Double shopItemPrice;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "shopItem")
    @JsonIgnoreProperties(value = {"shopItem"})
    private Set<LineItem> lineItems;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "shopItem",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnoreProperties(value = {"shopItem"})
    private Set<CartItem> cartItems;

    @Column
    private Boolean state;

    public ShopItem(){
        this.state = true;   //首次创建时为上架状态
    }
}
