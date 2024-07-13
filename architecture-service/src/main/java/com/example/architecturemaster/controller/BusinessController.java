package com.example.architecturemaster.controller;

import com.example.architecturemaster.VO.Exception.MessageException;
import com.example.architecturemaster.common.config.authConfig.AuthAccess;
import com.example.architecturemaster.common.controller.LogicController;
import com.example.architecturemaster.dao.BusinessDao;
import com.example.architecturemaster.dto.ItemDTO;
import com.example.architecturemaster.dto.ShopDTO;
import com.example.architecturemaster.dto.UserDTO;
import com.example.architecturemaster.entity.Business;
import com.example.architecturemaster.entity.Item;
import com.example.architecturemaster.entity.Shop;
import com.example.architecturemaster.entity.User;
import com.example.architecturemaster.service.BusinessService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/BusinessController")
@Tag(name = "商家服务接口")
public class BusinessController extends LogicController<BusinessService, BusinessDao, Business,Long> {
    public BusinessController(@Autowired BusinessService ls) {
        super(ls);
    }
    @Resource
    private ModelMapper modelMapper;

    @AuthAccess
    @PostMapping("/register")
    @Operation(summary = "商家注册")
    @Parameter(name = "business",description = "商家信息json",required = true)
    public UserDTO register(@RequestBody Business business) throws MessageException {
        User user = getService().register(business);
        return modelMapper.map(business,UserDTO.class);
    }

    @Transactional
    @PostMapping("/createShop")
    @Operation(summary = "新建门店")
    @Parameters({
            @Parameter(name = "businessId",description = "商家id",required = true),
            @Parameter(name = "shopDTO",description = "门店属性json",required = true),
    })
    public ShopDTO createShop(@RequestParam Long businessId,
                              @RequestBody ShopDTO shopDTO) throws IOException {
        Shop shop1 = getService().createShop(businessId, shopDTO);
        return modelMapper.map(shop1,ShopDTO.class);
    }

    @Transactional
    @PostMapping("/createItem")
    @Operation(summary = "新增商品")
    @Parameters({
            @Parameter(name = "businessId",description = "商家id",required = true),
            @Parameter(name = "itemDTO",description = "商品属性json",required = true)
    })
    public ItemDTO createItem(@RequestParam Long businessId,@RequestBody ItemDTO itemDTO) throws IOException {
        Item item = getService().createItem(businessId, itemDTO);
        return modelMapper.map(item,ItemDTO.class);
    }

    @Transactional
    @GetMapping("/listMyShops")
    @Operation(summary = "获取商家的门店列表")
    @Parameter(name = "businessId",description = "商家id",required = true,example = "1")
    public Set<ShopDTO> listMyShops(@RequestParam Long businessId){
        Set<Shop> shops = getService().listMyShops(businessId);
        return shops.stream().map(shop -> modelMapper.map(shop, ShopDTO.class))
                .collect(Collectors.toSet());
    }

    @Transactional
    @GetMapping("/listMyItems")
    @Operation(summary = "获取商品列表")
    @Parameter(name = "businessId", description = "商家id",required = true)
    public Set<ItemDTO> listMyItems(@RequestParam Long businessId){
        Set<Item> items = getService().listMyItems(businessId);
        return items.stream().map(item -> modelMapper.map(item,ItemDTO.class)).collect(Collectors.toSet());
    }

    @GetMapping("/getBusinessInfo")
    @Operation(summary = "获取商家个人信息")
    @Parameter(name = "businessId",description = "商家id",required = true,example = "1")
    public UserDTO getBusinessInfo(@RequestParam Long businessId){
        User business = getService().GET(businessId);
        return modelMapper.map(business,UserDTO.class);
    }

}
