package com.example.architecturemaster.controller;

import com.example.architecturemaster.common.controller.LogicController;
import com.example.architecturemaster.dao.DeliveryAddressDao;
import com.example.architecturemaster.dto.DeliveryAddressDTO;
import com.example.architecturemaster.entity.DeliveryAddress;
import com.example.architecturemaster.service.DeliveryAddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/DeliveryAddressController")
@Tag(name = "配送地址服务接口")
public class DeliveryAddressController extends LogicController<DeliveryAddressService, DeliveryAddressDao, DeliveryAddress,Long> {
    public DeliveryAddressController(@Autowired DeliveryAddressService ls) {
        super(ls);
    }
    @Resource
    private ModelMapper modelMapper;

    @Transactional
    @GetMapping("/getDeliveryAddressById")
    @Operation(summary = "根据id获取配送地址")
    @Parameter(name = "daId",description = "地址编号",required = true)
    public DeliveryAddressDTO getDeliveryAddressById(@RequestParam Long daId){
        DeliveryAddress deliveryAddress = getService().GET(daId);
        return modelMapper.map(deliveryAddress, DeliveryAddressDTO.class);
    }

    @Transactional
    @PutMapping("/update")
    @Operation(summary = "更新收货地址")
    @Parameter(name = "deliveryAddressDTO",description = "更新后的收货地址json",required = true)
    public DeliveryAddressDTO updateDeliveryAddressInfo(@RequestBody DeliveryAddressDTO deliveryAddressDTO){
        DeliveryAddress deliveryAddress = modelMapper.map(deliveryAddressDTO, DeliveryAddress.class);
        DeliveryAddress deliveryAddress1 = getService().PUT(deliveryAddress);
        return modelMapper.map(deliveryAddress1,DeliveryAddressDTO.class);
    }

    @DeleteMapping("/deleteDeliveryAddress")
    @Operation(summary = "根据配送地址id删除配送地址")
    @Parameter(name = "deliveryAddressId",description = "收货地址id",required = true)
    public Integer deleteDeliveryAddress(@RequestParam Long deliveryAddressId){
        return getService().removeDeliverAddress(deliveryAddressId);
    }

}
