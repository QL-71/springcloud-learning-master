package com.example.architecturemaster.controller;

import com.example.architecturemaster.common.controller.LogicController;
import com.example.architecturemaster.common.utils.OrderMapper;
import com.example.architecturemaster.dao.OrderDao;
import com.example.architecturemaster.dto.CartItemDTO;
import com.example.architecturemaster.dto.OrderDTO;
import com.example.architecturemaster.entity.CartItem;
import com.example.architecturemaster.entity.Orders;
import com.example.architecturemaster.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/OrderController")
@Tag(name = "订单服务接口")
public class OrderController extends LogicController<OrderService, OrderDao, Orders,Long> {
    public OrderController(@Autowired OrderService ls) {
        super(ls);
    }
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private ModelMapper modelMapper;

    @Transactional
    @PostMapping("/createOrder")
    @Operation(summary = "生成订单")
    @Parameter(name = "orderDTO",description = "订单内容",required = true)
    public OrderDTO createOrder(@RequestBody OrderDTO orderDTO){
        Long customerId = orderDTO.getCustomerId();
        Long shopId = orderDTO.getShopId();
        Double orderTotal = orderDTO.getOrderTotal();
        Long deliveryAddressId = orderDTO.getDeliveryAddressDTO().getId();
        Orders order = getService().createOrder(customerId, shopId,deliveryAddressId,orderTotal);
        return orderMapper.mapOrderToDTO(order);
    }

    @Transactional
    @GetMapping("/getOrderInfoById")
    @Operation(summary = "查询订单")
    @Parameter(name = "orderId",description = "订单id",required = true)
    public OrderDTO getOrderInfoById(@RequestParam Long orderId){
        Orders order = getService().GET(orderId);
        return orderMapper.mapOrderToDTO(order);
    }

    @Transactional
    @GetMapping("/getNotPayOrder")
    @Operation(summary = "查询未支付订单")
    @Parameters({
            @Parameter(name = "customerId",description = "顾客id",required = true),
            @Parameter(name = "shopId",description = "门店id",required = true)
    })
    public List<OrderDTO> getNotPayOrder(@RequestParam Long customerId, @RequestParam Long shopId){
        List<Orders> orders = getService().getNotPayOrder(customerId, shopId);
        return orders.stream().map(order -> orderMapper.mapOrderToDTO(order)).collect(Collectors.toList());
    }

    @Transactional
    @PostMapping("/orderAgain")
    @Operation(summary = "再来一单")
    @Parameter(name = "orderDTO",description = "订单DTO",required = true)
    public List<CartItemDTO> orderAgain(@RequestBody OrderDTO orderDTO){
        List<CartItem> cartItems = getService().orderAgainst(orderDTO);
        return cartItems.stream().map(cartItem -> modelMapper.map(cartItem, CartItemDTO.class)).collect(Collectors.toList());
    }

}
