package com.example.architecturemaster.entity;

import com.example.architecturemaster.common.entity.LogicEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@Where(clause = "is_deleted = 0")
public class LineItem extends LogicEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"lineItems"})
    private Orders order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"lineItems"})
    private ShopItem shopItem;

    @Column
    private Integer quantity;
}
