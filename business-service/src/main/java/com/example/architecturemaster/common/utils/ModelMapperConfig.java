package com.example.architecturemaster.common.utils;

import com.example.architecturemaster.dto.CartItemDTO;
import com.example.architecturemaster.dto.LineItemDTO;
import com.example.architecturemaster.dto.OrderDTO;
import com.example.architecturemaster.entity.CartItem;
import com.example.architecturemaster.entity.LineItem;
import com.example.architecturemaster.entity.Orders;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;


@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);

        modelMapper.addMappings(new PropertyMap<CartItem, CartItemDTO>() {
            @Override
            protected void configure(){
                map().setId(source.getId());
                map().setQuantity(source.getQuantity());
                map().setCustomerId(source.getCustomer().getId());
                map().setShopId(source.getShop().getId());
                map().setShopItemImg(source.getShopItem().getItem().getItemImg());
                map().setShopItemId(source.getShopItem().getId());
                map().setShopItemName(source.getShopItem().getItem().getItemName());
                map().setShopItemPrice(source.getShopItem().getShopItemPrice());
            }
        });

        modelMapper.addMappings(new PropertyMap<LineItem, LineItemDTO>() {
            @Override
            protected void configure(){
                map().setShopItemPrice(source.getShopItem().getShopItemPrice());
            }
        });

        modelMapper.addMappings(new PropertyMap<Orders, OrderDTO>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
                map().setOrderState(source.getOrderState());
                map().setShopId(source.getShop().getId());
                map().setCustomerId(source.getCustomer().getId());
                map().setOrderTotal(source.getOrderTotal());
                map().setShopName(source.getShop().getShopName());
                map().setDeliveryPrice(source.getShop().getDeliveryPrice());
                map().setLineItemDTOS(new HashSet<>());
            }
        });
        return modelMapper;
    }
}
