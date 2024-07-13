package com.example.architecturemaster.service;

import com.example.architecturemaster.VO.Exception.MessageException;
import com.example.architecturemaster.common.service.LogicService;
import com.example.architecturemaster.dao.*;
import com.example.architecturemaster.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CustomerService extends LogicService<CustomerDao, Customer,Long> {
    public CustomerService(@Autowired CustomerDao lr) {
        super(lr);
    }
    @Resource
    private UserDao uDao;
    @Resource
    private ShopItemDao shDao;
    @Resource
    private ShopDao sDao;
    @Resource
    private OrderDao oDao;

    public User register(User user) throws MessageException {
        User user1 = uDao.findUserByUserTelephone(user.getUserTelephone());
        if (user1 != null){
            throw new MessageException();
        }
        return this.POST((Customer) user);
    }

    public CartItem selectShopItem(Long customerId, Long shopItemId, Integer quantity) {
        Customer customer = Optional.of(getDAO().findById(customerId)).get().orElse(null);
        ShopItem shopItem = Optional.of(shDao.findById(shopItemId)).get().orElse(null);
        CartItem cartItem = customer.selectShopItem(shopItem, quantity);
        getDAO().save(customer);
        return cartItem;
    }

    public Set<Orders> listMyOrders(Long customerId) {
        Customer customer = Optional.of(getDAO().findById(customerId)).get().orElse(null);
        return customer.getOrders();
    }

    public List<CartItem> listMyCarts(Long customerId, Long shopId) {
        Customer customer = Optional.of(getDAO().findById(customerId)).get().orElse(null);
        Shop shop = Optional.of(sDao.findById(shopId)).get().orElse(null);
        return customer.listMyCarts(shop);
    }

    public DeliveryAddress createDeliveryAddress(Long customerId, DeliveryAddress deliveryAddress){
        Customer customer = Optional.of(getDAO().findById(customerId)).get().orElse(null);
        DeliveryAddress deliveryAddress1 = customer.createDeliveryAddress(deliveryAddress);
        getDAO().save(customer);
        return deliveryAddress1;
    }

    public Set<DeliveryAddress> listMyDeliveryAddress(Long customerId){
        Customer customer = Optional.of(getDAO().findById(customerId)).get().orElse(null);
        return customer.getDeliveryAddresses();
    }

    public Orders payOrder(Long customerId,Long orderId){
        Customer customer = Optional.of(getDAO().findById(customerId)).get().orElse(null);
        Orders order = Optional.of(oDao.findById(orderId)).get().orElse(null);
        Orders order1 = customer.payOrder(order);
        getDAO().save(customer);
        return order1;
    }

    public CartItem removeSelectedItem(Long customerId, Long shopItemId){
        Customer customer = Optional.of(getDAO().findById(customerId)).get().orElse(null);
        ShopItem shopItem = Optional.of(shDao.findById(shopItemId)).get().orElse(null);
        CartItem cartItem = customer.removeSelectedItem(shopItem);
        getDAO().save(customer);
        return cartItem;
    }

    public List<CartItem> clearCarts(Long customerId, Long shopId) {
        Customer customer = Optional.of(getDAO().findById(customerId)).get().orElse(null);
        Shop shop = Optional.of(sDao.findById(shopId)).get().orElse(null);
        List<CartItem> cartItems = customer.clearCarts(shop);
        getDAO().save(customer);
        return cartItems;
    }

}
