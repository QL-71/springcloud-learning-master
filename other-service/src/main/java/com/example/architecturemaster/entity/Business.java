package com.example.architecturemaster.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@DiscriminatorValue("0")
@Where(clause = "is_deleted = 0")
public class Business extends User{
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "business",
            cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnoreProperties(value = {"business"})
    private Set<Shop> shops;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "business",
            cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnoreProperties(value = {"business"})
    private Set<Item> items;
    @JsonIgnore
    public Shop createShop(Shop shop){
        shop.setBusiness(this);
        shops.add(shop);
        return shop;
    }
    @JsonIgnore
    public Item createItem(Item item){
        if (items.contains(item)){
            return null;
        }
        item.setBusiness(this);
        items.add(item);
        return item;
    }
    @JsonIgnore
    public Boolean hasItem(Item item){
        for (Item item1 : items) {
            if (item1.equals(item)) return true;
        }
        return false;
    }

}
