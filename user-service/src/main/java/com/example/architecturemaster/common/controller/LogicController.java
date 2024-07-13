package com.example.architecturemaster.common.controller;

import com.example.architecturemaster.common.dao.LogicDAO;
import com.example.architecturemaster.common.entity.LogicEntity;
import com.example.architecturemaster.common.service.LogicService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;


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
    public T GET(@RequestParam ID id){
        T t = service.GET(id);
        return service.GET(id);
    }
    @GetMapping("/getall")
    public List<T> getAll(){
        return service.getAll();
    }
    @GetMapping("/getallPage")
    public Page<T> getAll(@RequestParam int page, @RequestParam int size){
        return service.getAll(page,size);
    }
    @PostMapping("/put")
    public T PUT(@RequestBody T entity){
        return service.PUT(entity);
    }

    @PostMapping("/post")
    public T POST(@RequestBody T  entity){
        return service.POST(entity);
    }

    @PostMapping("/delete")
    public void DELETE(@RequestBody T entity){
        service.DELETE(entity);
    }

    @GetMapping("/delete")
    public void DELETE(@RequestParam ID id){
        service.DELETE(id);
    }
}
