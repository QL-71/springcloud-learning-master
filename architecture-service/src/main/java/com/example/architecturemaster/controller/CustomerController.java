package com.example.architecturemaster.controller;

import com.example.architecturemaster.VO.Exception.MessageException;
import com.example.architecturemaster.common.config.authConfig.AuthAccess;
import com.example.architecturemaster.common.controller.LogicController;
import com.example.architecturemaster.common.utils.OrderMapper;
import com.example.architecturemaster.dao.CustomerDao;
import com.example.architecturemaster.dto.CartItemDTO;
import com.example.architecturemaster.dto.DeliveryAddressDTO;
import com.example.architecturemaster.dto.OrderDTO;
import com.example.architecturemaster.dto.UserDTO;
import com.example.architecturemaster.entity.*;
import com.example.architecturemaster.service.CustomerService;
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
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/CustomerController")
@Tag(name = "顾客服务接口")
public class CustomerController extends LogicController<CustomerService, CustomerDao, Customer,Long> {
    public CustomerController(@Autowired CustomerService ls) {
        super(ls);
    }
    @Resource
    private ModelMapper modelMapper;
    @Resource
    private OrderMapper orderMapper;

    @AuthAccess
    @PostMapping("/register")
    @Parameter(name = "customer",description = "用户信息json",required = true)
    public UserDTO register(@RequestBody Customer customer) throws MessageException {
        User user = getService().register(customer);
        return modelMapper.map(user,UserDTO.class);
    }

    @Transactional
    @PostMapping("/selectShopItem")
    @Operation(summary = "挑选商品")
    @Parameter(name = "cartDTO",description = "购物车的一条记录",required = true)
    public CartItemDTO selectShopItem(@RequestBody CartItemDTO cartItemDTO){
        Long customerId = cartItemDTO.getCustomerId();
        Long shopItemId = cartItemDTO.getShopItemId();
        Integer quantity = cartItemDTO.getQuantity();
        CartItem cartItem = getService().selectShopItem(customerId, shopItemId, quantity);
        return modelMapper.map(cartItem, CartItemDTO.class);
    }

    @Transactional
    @GetMapping("/listMyOrders")
    @Operation(summary = "获取所有订单")
    @Parameter(name = "customerId",description = "顾客id",required = true)
    public Set<OrderDTO> listMyOrders(@RequestParam Long customerId){
        Set<Orders> orders = getService().listMyOrders(customerId);
        return orders.stream().map(orders1 -> orderMapper.mapOrderToDTO(orders1)).collect(Collectors.toSet());
    }

    @Transactional
    @GetMapping("/listMyCarts")
    @Operation(summary = "获取在某个门店的购物车")
    @Parameters({
            @Parameter(name = "customerId",description = "顾客id",required = true),
            @Parameter(name = "shopId",description = "门店id",required = true)
    })
    public List<CartItemDTO> listMyCarts(@RequestParam Long customerId, @RequestParam Long shopId){
        List<CartItem> cartItems = getService().listMyCarts(customerId, shopId);
        return cartItems.stream().map(cart -> modelMapper.map(cart, CartItemDTO.class)).collect(Collectors.toList());
    }

    @Transactional
    @GetMapping("/listMyDeliveryAddress")
    @Operation(summary = "获取我的收货地址")
    @Parameter(name = "customerId",description = "顾客id",required = true)
    public Set<DeliveryAddressDTO> listMyDeliveryAddress(Long customerId){
        Set<DeliveryAddress> deliveryAddresses = getService().listMyDeliveryAddress(customerId);
        return deliveryAddresses.stream()
                .map(deliveryAddress -> modelMapper.map(deliveryAddress,DeliveryAddressDTO.class)).collect(Collectors.toSet());
    }

    @Transactional
    @PostMapping("/createDeliveryAddress")
    @Operation(summary = "新建收货地址")
    @Parameter(name = "deliveryAddressDTO",description = "收货地址信息json",required = true)
    public DeliveryAddressDTO createDeliveryAddress(@RequestBody DeliveryAddressDTO deliveryAddressDTO){
        Long customerId = deliveryAddressDTO.getCustomerId();
//        System.out.println(deliveryAddressDTO.getAddress());
        DeliveryAddress deliveryAddress = modelMapper.map(deliveryAddressDTO, DeliveryAddress.class);
        DeliveryAddress deliveryAddress1 = getService().createDeliveryAddress(customerId, deliveryAddress);
        return modelMapper.map(deliveryAddress1,DeliveryAddressDTO.class);
    }

    @Transactional
    @PostMapping("/payOrder")
    @Operation(summary = "订单支付")
    @Parameter(name = "orderDTO",description = "支付的订单内容",required = true)
    public OrderDTO pay(@RequestBody OrderDTO orderDTO){
        Long customerId = orderDTO.getCustomerId();
        Long orderId = orderDTO.getId();
        Orders order = getService().payOrder(customerId, orderId);
        return orderMapper.mapOrderToDTO(order);
    }

    @Transactional
    @GetMapping("/getCustomerInfo")
    @Operation(summary = "获取顾客个人信息")
    @Parameter(name = "customerId",description = "顾客id",required = true,example = "1")
    public UserDTO listMyShops(@RequestParam Long customerId){
        User customer = getService().GET(customerId);
        return modelMapper.map(customer,UserDTO.class);
    }

    @Transactional
    @PostMapping("/removeSelectedItem")
    @Operation(summary = "取消选择的商品")
    @Parameters({
            @Parameter(name = "customerId",description = "顾客id",required = true),
            @Parameter(name = "cartId",description = "购物车id",required = true)
    })
    public CartItemDTO removeSelectedItem(@RequestParam Long customerId, @RequestParam Long shopItemId){
        CartItem cartItem = getService().removeSelectedItem(customerId, shopItemId);
        return modelMapper.map(cartItem, CartItemDTO.class);
    }
}
