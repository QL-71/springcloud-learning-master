package com.example.architecturemaster.service;

import com.example.architecturemaster.common.service.LogicService;
import com.example.architecturemaster.dao.DeliveryAddressDao;
import com.example.architecturemaster.entity.DeliveryAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryAddressService extends LogicService<DeliveryAddressDao, DeliveryAddress,Long>{
    public DeliveryAddressService(@Autowired DeliveryAddressDao lr) {
        super(lr);
    }

    public Integer removeDeliverAddress(Long deliveryAddressId) {
        return getDAO().removeDeliveryAddressById(deliveryAddressId);
    }

}
