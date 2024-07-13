package com.example.architecturemaster.service;

import com.example.architecturemaster.common.service.LogicService;
import com.example.architecturemaster.common.utils.FileHandler.FileHandler;
import com.example.architecturemaster.dao.ItemDao;
import com.example.architecturemaster.dao.OrderDao;
import com.example.architecturemaster.dao.ShopDao;
import com.example.architecturemaster.dao.ShopItemDao;
import com.example.architecturemaster.entity.Item;
import com.example.architecturemaster.entity.Orders;
import com.example.architecturemaster.entity.Shop;
import com.example.architecturemaster.entity.ShopItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ShopService extends LogicService<ShopDao, Shop,Long>{
    public ShopService(@Autowired ShopDao lr) {
        super(lr);
    }
    @Resource
    private OrderDao oDao;
    @Resource
    private ShopItemDao shDao;
    @Resource
    private ItemDao iDao;
    @Resource
    private FileHandler fileHandler;
    @Value("${server.port}")
    private String serverPort;
    @Value("${server.servlet.context-path}")
    private String contextPath;

    public Set<Orders> listMyOrders(Long shopId) {
        Shop shop = Optional.of(getDAO().findById(shopId)).get().orElse(null);
        return shop.getOrders();
    }

    public Orders confirmOrders(Long shopId, Long orderId) {
        Shop shop = Optional.of(getDAO().findById(shopId)).get().orElse(null);
        Orders orders = Optional.of(oDao.findById(orderId)).get().orElse(null);
        Orders orders1 = shop.confirmOrders(orders);
        getDAO().save(shop);
        return orders1;
    }

    public Orders cancelOrders(Long shopId, Long orderId) {
        Shop shop = Optional.of(getDAO().findById(shopId)).get().orElse(null);
        Orders orders = Optional.of(oDao.findById(orderId)).get().orElse(null);
        Orders orders1 = shop.cancelOrders(orders);
        getDAO().save(shop);
        return orders1;
    }

    public Set<ShopItem> listMyShopItems(Long shopId) {
        Shop shop = Optional.of(getDAO().findById(shopId)).get().orElse(null);
        return shop.getShopItems();
    }

    public ShopItem putItem(Long shopId,Long itemId, Double shopItemPrice) {
        Shop shop = Optional.of(getDAO().findById(shopId)).get().orElse(null);
        Item item = Optional.of(iDao.findById(itemId)).get().orElse(null);
        ShopItem shopItem = shop.putItem(item, shopItemPrice);
        getDAO().save(shop);
        return shopItem;
    }

    public ShopItem changeShopItemState(Long shopId,Long shopItemId) {
        Shop shop = Optional.of(getDAO().findById(shopId)).get().orElse(null);
        ShopItem shopItem = Optional.of(shDao.findById(shopItemId)).get().orElse(null);
        ShopItem shopItem1 = shop.changeShopItemState(shopItem);
        getDAO().save(shop);
        return shopItem1;
    }

    @Transactional
    public Shop updateShopImg(Shop shop) throws IOException {
        String shopImg = fileHandler.convertBase64File(shop.getShopImg());
        shopImg = "http://localhost:"+serverPort+contextPath+"/"+shopImg;
        shop.setShopImg(shopImg);
        return shop;
    }

    public List<Shop> searchShops(String keywordName,Long businessId){
        return getDAO().searchShops(keywordName,businessId);
    }

    public List<Orders> listNoHandledOrders(Long shopId) {
        Shop shop = Optional.of(getDAO().findById(shopId)).get().orElse(null);
        return shop.listNoHandledOrders();
    }

}
