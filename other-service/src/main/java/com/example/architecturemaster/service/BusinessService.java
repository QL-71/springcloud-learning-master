package com.example.architecturemaster.service;

import com.example.architecturemaster.VO.Exception.MessageException;
import com.example.architecturemaster.common.service.LogicService;
import com.example.architecturemaster.common.utils.FileHandler.FileHandler;
import com.example.architecturemaster.dao.BusinessDao;
import com.example.architecturemaster.dao.UserDao;
import com.example.architecturemaster.dto.ItemDTO;
import com.example.architecturemaster.dto.ShopDTO;
import com.example.architecturemaster.entity.Business;
import com.example.architecturemaster.entity.Item;
import com.example.architecturemaster.entity.Shop;
import com.example.architecturemaster.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;


@Service
public class BusinessService extends LogicService<BusinessDao, Business,Long> {

    public BusinessService(@Autowired BusinessDao lr) {
        super(lr);
    }
    @Resource
    private UserDao uDao;
    @Resource
    private FileHandler fileHandler;
    @Resource
    private ModelMapper modelMapper;
    @Value("${server.port}")
    private String serverPort;
    @Value("${server.servlet.context-path}")
    private String contextPath;

    public User register(User user) throws MessageException {
        User user1 = uDao.findUserByUserTelephone(user.getUserTelephone());
        if (user1 != null){
            throw new MessageException();
        }
        return this.POST((Business) user);
    }


    public Item createItem(Long businessId, ItemDTO itemDTO) throws IOException {
        String itemImg = fileHandler.convertBase64File(itemDTO.getItemImg());
        itemImg = "http://localhost:"+serverPort+contextPath+"/"+itemImg;
        itemDTO.setItemImg(itemImg);
        Item item = modelMapper.map(itemDTO, Item.class);
        Business business = Optional.of(getDAO().findById(businessId)).get().orElse(null);
        Item item1 = business.createItem(item);
        getDAO().save(business);
        return item1;
    }

    public Shop createShop(Long businessId, ShopDTO shopDTO) throws IOException {
        String shopImg = fileHandler.convertBase64File(shopDTO.getShopImg());
        shopImg = "http://localhost:"+serverPort+contextPath+"/"+shopImg;
        Shop shop = modelMapper.map(shopDTO, Shop.class);
        shop.setShopImg(shopImg);
        Business business = Optional.of(getDAO().findById(businessId)).get().orElse(null);
        Shop shop1 = business.createShop(shop);
        getDAO().save(business);
        return shop1;
    }


    public Set<Shop> listMyShops(Long businessId) {
        Business business = Optional.of(getDAO().findById(businessId)).get().orElse(null);
        return business.getShops();
    }

    public Set<Item> listMyItems(Long businessId){
        Business business = Optional.of(getDAO().findById(businessId)).get().orElse(null);
        return business.getItems();
    }


}
