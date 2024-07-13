package com.example.architecturemaster.common.controller;

import com.example.architecturemaster.common.dao.LogicDAO;
import com.example.architecturemaster.common.entity.LogicEntity;
import com.example.architecturemaster.common.service.LogicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;


@Tag(name = "系统逻辑服务接口")
@CrossOrigin
public abstract class LogicController<S extends LogicService<D,T,ID>, D extends LogicDAO<T,ID>,T extends LogicEntity, ID extends Serializable>{

    protected S service;
    protected S getService(){
        return service;
    }
    public LogicController(S ls){
        this.service = ls;
    }
    @GetMapping("/get")
    @Operation(summary = "通过ID获取实体")
    public T GET(@RequestParam ID id){
        T t = service.GET(id);
        return service.GET(id);
    }
    @Operation(summary = "获取所有实体")
    @GetMapping("/getall")
    public List<T> getAll(){
        return service.getAll();
    }
    @Operation(summary = "分页获取所有实体")
    @GetMapping("/getallPage")
    public Page<T> getAll(@RequestParam int page, @RequestParam int size){
        return service.getAll(page,size);
    }
    @Operation(summary = "修改实体")
    @PostMapping("/put")
    public T PUT(@RequestBody T entity){
        return service.PUT(entity);
    }

    @Operation(summary = "创建实体")
    @PostMapping("/post")
    public T POST(@RequestBody T  entity){
        return service.POST(entity);
    }

    @Operation(summary = "删除实体")
    @PostMapping("/delete")
    public void DELETE(@RequestBody T entity){
        service.DELETE(entity);
    }

    @Operation(summary = "通过ID删除实体")
    @GetMapping("/delete")
    public void DELETE(@RequestParam ID id){
        service.DELETE(id);
    }
}
