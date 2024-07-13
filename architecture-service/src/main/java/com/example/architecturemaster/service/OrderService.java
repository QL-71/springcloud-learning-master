package com.example.architecturemaster.service;

import com.example.architecturemaster.common.service.LogicService;
import com.example.architecturemaster.dao.*;
import com.example.architecturemaster.dto.LineItemDTO;
import com.example.architecturemaster.dto.OrderDTO;
import com.example.architecturemaster.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class OrderService extends LogicService<OrderDao, Orders,Long>{
    public OrderService(@Autowired OrderDao lr) {
        super(lr);
    }
    @Resource
    private CartItemDao cartItemDao;
    @Resource
    private DeliveryAddressDao dDao;
    @Resource
    private ShopItemDao shopItemDao;
    @Resource
    private CustomerDao customerDao;
    @Resource
    private ShopDao shopDao;

    public Orders createOrder(Long customerId,Long shopId,Long deliveryAddressId,Double orderTotal){
        List<CartItem> cartItems = cartItemDao.findAllByCustomerIdAndShopId(customerId, shopId);
        DeliveryAddress deliveryAddress = Optional.of(dDao.findById(deliveryAddressId)).get().orElse(null);
        Orders order = new Orders();
        Orders order1 = order.createOder(cartItems,deliveryAddress,orderTotal);
        cartItemDao.removeAllByCustomerIdAndShopId(customerId, shopId);
        getDAO().save(order1);
        return order1;
    }

    public List<Orders> getNotPayOrder(Long customerId,Long shopId){
        return getDAO().getNotPayOrder(customerId, shopId);
    }

    public List<CartItem> orderAgainst(OrderDTO orderDTO){
        Set<LineItemDTO> lineItemDTOS = orderDTO.getLineItemDTOS();
        Long customerId = orderDTO.getCustomerId();
        Customer customer = Optional.of(customerDao.findById(customerId)).get().orElse(null);
        Shop shop = Optional.of(shopDao.findById(orderDTO.getShopId())).get().orElse(null);
        cartItemDao.removeAllByCustomerIdAndShopId(customerId,shop.getId());
        List<CartItem> cartItems = new ArrayList<>();
        for (LineItemDTO lineItemDTO : lineItemDTOS) {
            CartItem cartItem = new CartItem();
            ShopItem shopItem = Optional.of(shopItemDao.findById(lineItemDTO.getShopItemId())).get().orElse(null);
            cartItem.setShopItem(shopItem);
            cartItem.setQuantity(lineItemDTO.getQuantity());
            cartItem.setCustomer(customer);
            cartItem.setShop(shop);
            cartItemDao.save(cartItem);
            cartItems.add(cartItem);
        }
        return cartItems;
    }
}
