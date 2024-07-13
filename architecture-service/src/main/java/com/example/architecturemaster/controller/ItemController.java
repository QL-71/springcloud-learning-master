package com.example.architecturemaster.controller;

import com.example.architecturemaster.common.controller.LogicController;
import com.example.architecturemaster.dao.ItemDao;
import com.example.architecturemaster.dto.ItemDTO;
import com.example.architecturemaster.entity.Item;
import com.example.architecturemaster.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;

@RestController
@RequestMapping("/ItemController")
public class ItemController extends LogicController<ItemService, ItemDao, Item,Long> {
    public ItemController(@Autowired ItemService ls) {
        super(ls);
    }
    @Resource
    private ModelMapper modelMapper;

    @PostMapping("/update")
    @Operation(summary = "更新商品信息")
    @Parameter(name = "item",description = "商品",required = true)
    public ItemDTO update(@RequestBody Item item) throws IOException {
        Item item1 = item;
        if (item.getItemImg() != null) {
            item1 = getService().updateItemImg(item1);
        }
        Item item2 = getService().PUT(item1);
        return modelMapper.map(item2,ItemDTO.class);
    }

    @GetMapping("deleteById")
    @Operation(summary = "删除商品")
    @Parameter(name = "itemId",description = "商品id",required = true)
    public Integer deleteById(@RequestParam Long itemId){
        return getService().deleteItem(itemId);
    }
}
