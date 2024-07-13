package com.example.architecturemaster.common.utils;

import com.example.architecturemaster.dto.DeliveryAddressDTO;
import com.example.architecturemaster.dto.LineItemDTO;
import com.example.architecturemaster.dto.OrderDTO;
import com.example.architecturemaster.entity.Orders;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class OrderMapper {
    @Resource
    private ModelMapper modelMapper;
    public OrderDTO mapOrderToDTO(Orders order){
        OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
        Set<LineItemDTO> lineItemDTOS = order.getLineItems().stream().map(lineItem -> modelMapper.map(lineItem, LineItemDTO.class)).collect(Collectors.toSet());
        orderDTO.setLineItemDTOS(lineItemDTOS);
        DeliveryAddressDTO deliveryAddressDTO = modelMapper.map(order.getDeliveryAddress(), DeliveryAddressDTO.class);
        orderDTO.setDeliveryAddressDTO(deliveryAddressDTO);
        return orderDTO;
    }
}
