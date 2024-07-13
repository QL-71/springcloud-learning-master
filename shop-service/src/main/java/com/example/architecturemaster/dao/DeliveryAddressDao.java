package com.example.architecturemaster.dao;

import com.example.architecturemaster.common.dao.LogicDAO;
import com.example.architecturemaster.entity.DeliveryAddress;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface DeliveryAddressDao extends LogicDAO<DeliveryAddress,Long> {
    @Modifying
    @Transactional
    Integer removeDeliveryAddressById(Long deliveryAddressId);
}
