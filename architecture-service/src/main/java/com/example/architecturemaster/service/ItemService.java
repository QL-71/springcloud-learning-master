package com.example.architecturemaster.service;

import com.example.architecturemaster.common.service.LogicService;
import com.example.architecturemaster.common.utils.FileHandler.FileHandler;
import com.example.architecturemaster.dao.ItemDao;
import com.example.architecturemaster.dao.ShopItemDao;
import com.example.architecturemaster.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;

@Service
public class ItemService extends LogicService<ItemDao, Item,Long> {
    public ItemService(@Autowired ItemDao lr) {
        super(lr);
    }
    @Resource
    private ShopItemDao shopItemDao;
    @Resource
    private FileHandler fileHandler;
    @Value("${server.port}")
    private String serverPort;
    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Transactional
    public Item updateItemImg(Item item) throws IOException {
        String itemImg = fileHandler.convertBase64File(item.getItemImg());
        itemImg = "http://localhost:"+serverPort+contextPath+"/"+itemImg;
        item.setItemImg(itemImg);
        return item;
    }

    @Transactional
    public Integer deleteItem(Long itemId){
        Integer i = shopItemDao.deleteAllByItemId(itemId);
        getDAO().deleteById(itemId);
        return i;
    }

}
