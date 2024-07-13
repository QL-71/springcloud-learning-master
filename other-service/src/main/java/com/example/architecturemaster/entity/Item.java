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
public class Item extends LogicEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"items"})
    private Business business;

    @Column(nullable = false)
    private String itemName;

    @Column
    private String itemImg;

    @Column
    private String itemRemark;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "item",
            cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnoreProperties(value = {"item"})
    private Set<ShopItem> shopItems;

}
