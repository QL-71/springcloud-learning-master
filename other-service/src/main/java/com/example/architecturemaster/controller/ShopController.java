package com.example.architecturemaster.controller;

import com.example.architecturemaster.common.controller.LogicController;
import com.example.architecturemaster.common.utils.OrderMapper;
import com.example.architecturemaster.dao.ShopDao;
import com.example.architecturemaster.dto.OrderDTO;
import com.example.architecturemaster.dto.ShopDTO;
import com.example.architecturemaster.dto.ShopItemDTO;
import com.example.architecturemaster.entity.Orders;
import com.example.architecturemaster.entity.Shop;
import com.example.architecturemaster.entity.ShopItem;
import com.example.architecturemaster.service.ShopService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.annotation.Resource;import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ShopController")
@Tag(name = "门店服务接口")
public class ShopController extends LogicController<ShopService, ShopDao, Shop,Long> {
    public ShopController(@Autowired ShopService ls) {
        super(ls);
    }
    @Resource
    private ModelMapper modelMapper;
    @Resource
    private OrderMapper orderMapper;

    @GetMapping("/getShopInfo")
    @Transactional
    @Operation(summary = "获取门店信息")
    @Parameter(name = "shopId",description = "门店id",required = true,example = "1")
    public ShopDTO listMyShops(@RequestParam Long shopId){
        Shop shop = this.GET(shopId);
        return modelMapper.map(shop,ShopDTO.class);
    }

    @Transactional
    @GetMapping("/listShopItems")
    @Operation(summary = "获取门店商品列表")
    @Parameter(name = "shopId",description = "门店id",required = true)
    public Set<ShopItemDTO> listMyShopItems(@RequestParam Long shopId){
        Set<ShopItem> shopItems = getService().listMyShopItems(shopId);
        return shopItems.stream().map(shopItem -> modelMapper.map(shopItem, ShopItemDTO.class)).collect(Collectors.toSet());
    }

    @Transactional
    @GetMapping("/listShopOrders")
    @Operation(summary = "获取门店订单列表")
    @Parameter(name = "shopId",description = "门店id",required = true)
    public Set<OrderDTO> listMyOrders(@RequestParam Long shopId){
        Set<Orders> orders = getService().listMyOrders(shopId);
        return orders.stream().map(orders1 -> orderMapper.mapOrderToDTO(orders1))
                .collect(Collectors.toSet());
    }

    @Transactional
    @PostMapping("/confirmOrder")
    @Operation(summary = "确认订单")
    @Parameter(name = "orderDTO",description = "订单dto",required = true)
    public OrderDTO confirmOrder(@RequestBody OrderDTO orderDTO){
        Long shopId = orderDTO.getShopId();
        Long orderId = orderDTO.getId();
        Orders order = getService().confirmOrders(shopId, orderId);
        return orderMapper.mapOrderToDTO(order);
    }

    @Transactional
    @PostMapping("/cancelOrder")
    @Operation(summary = "取消订单")
    @Parameter(name = "orderDTO",description = "订单dto",required = true)
    public OrderDTO cancelOrders(@RequestBody OrderDTO orderDTO){
        Long shopId = orderDTO.getShopId();
        Long orderId = orderDTO.getId();
        Orders order = getService().cancelOrders(shopId, orderId);
        return orderMapper.mapOrderToDTO(order);
    }

    @Transactional
    @GetMapping("/listAllShops")
    @Operation(summary = "获取所有门店信息")
    @Parameter(name = "customerId",description = "顾客id")
    public List<ShopDTO> listAllShops(){
        List<Shop> shops = getService().getAll();
        return shops.stream().map(shop -> modelMapper.map(shop, ShopDTO.class)).collect(Collectors.toList());
    }

    @Transactional
    @PostMapping("/putShopItem")
    @Operation(summary = "上架新商品")
    @Parameter(name = "shopItemDTO",description = "上架商品信息",required = true)
    public ShopItemDTO putShopItem(@RequestBody ShopItemDTO shopItemDTO){
        Long shopId = shopItemDTO.getShopId();
        Long itemId = shopItemDTO.getItemId();
        Double shopItemPrice = shopItemDTO.getShopItemPrice();
        ShopItem shopItem = getService().putItem(shopId, itemId, shopItemPrice);
        return modelMapper.map(shopItem, ShopItemDTO.class);
    }

    @Transactional
    @PostMapping("/changeShopItemState")
    @Operation(summary = "更改上架商品状态")
    @Parameter(name = "shopItemDTO",description = "上架过的商品的dto",required = true)
    public ShopItemDTO changeShopItemState(@RequestBody ShopItemDTO shopItemDTO){
        Long shopId = shopItemDTO.getShopId();
        Long shopItemId = shopItemDTO.getId();
        ShopItem shopItem = getService().changeShopItemState(shopId, shopItemId);
        return modelMapper.map(shopItem,ShopItemDTO.class);
    }

    @Transactional
    @PostMapping("/update")
    @Operation(summary = "更新门店信息")
    @Parameter(name = "shop",description = "门店信息",required = true)
    public ShopDTO update(@RequestBody Shop shop) throws IOException {
        Shop shop1 = shop;
        if (shop1.getShopImg() != null) {
            shop1 = getService().updateShopImg(shop1);
        }
        Shop shop2 = getService().PUT(shop1);
        return modelMapper.map(shop2,ShopDTO.class);
    }

    @Transactional
    @GetMapping("/getShopsBySearch")
    @Operation(summary = "根据门店名或商品名搜索门店")
    @Parameters({
            @Parameter(name = "searchContent",description = "搜索关键字",required = true),
            @Parameter(name = "shopId",description = "商家id")
    })
    public List<ShopDTO> getShopsBySearch(@RequestParam String searchContent,@RequestParam Optional<Long> businessId){
        List<Shop> shops = getService().searchShops(searchContent,businessId.orElse(null));
        return shops.stream().map(shop -> modelMapper.map(shop, ShopDTO.class)).collect(Collectors.toList());
    }

    @Transactional
    @GetMapping("/listNoHandledOrders")
    @Operation(summary = "获取未处理的订单")
    @Parameter(name = "shopId",description = "门店id",required = true)
    public List<OrderDTO> listNoHandledOrders(@RequestParam Long shopId){
        List<Orders> orders = getService().listNoHandledOrders(shopId);
        return orders.stream().map(orders1 -> orderMapper.mapOrderToDTO(orders1))
                .collect(Collectors.toList());
    }

    @Transactional
    @GetMapping("/listAllShopsPage")
    @Operation(summary = "分页获取所有门店信息")
    @Parameters({
            @Parameter(name = "page",description = "第几页",required = true),
            @Parameter(name = "size",description = "每页的大小",required = true)
    })
    public List<ShopDTO> listAllShopsPage(@RequestParam Integer page,@RequestParam Integer size){
        Page<Shop> shops = getService().getAll(page, size);
        return shops.stream().map(shop -> modelMapper.map(shop, ShopDTO.class)).collect(Collectors.toList());
    }

}
