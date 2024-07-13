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
public class DeliveryAddress extends LogicEntity {

    @Column(nullable = false)
    private String contactName;

    @Column(nullable = false)
    private Integer contactSex;

    @Column(nullable = false)
    private String contactTel;

    @Column(nullable = false)
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"deliveryAddresses"})
    private Customer customer;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "deliveryAddress")
    @JsonIgnoreProperties(value = {"deliveryAddress"})
    private Set<Orders> orders;
}