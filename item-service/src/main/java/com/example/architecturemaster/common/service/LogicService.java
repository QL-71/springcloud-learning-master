package com.example.architecturemaster.common.service;

import com.example.architecturemaster.common.dao.LogicDAO;
import com.example.architecturemaster.common.entity.LogicEntity;
import com.example.architecturemaster.common.utils.UpdateTool;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public class LogicService<D extends LogicDAO<T,ID>,T extends LogicEntity, ID extends Serializable>{

    protected D dao;
    public LogicService(D lr){
        this.dao = lr;
    }
    protected D getDAO(){
        return dao;
    }

    public T  GET(ID id){
        return Optional.of(dao.findById(id)).get().orElse(null);
    }
    public List<T> getAll(){
       return dao.findAll();
    }
    /** 基础的分页查询
     * @param page 第几页
     * @param size 每页的大小
     * @return 返回一个分页集合  */
    public Page<T> getAll(int page, int size){
        Pageable pageable = PageRequest.of(page,size, Sort.by("createTime").descending());
        return dao.findAll(pageable);
    }
    @Transactional
    public T  PUT(T entity){
        T sourceEntity = Optional.of(dao.findById((ID)entity.getId())).get().orElse(null);
        System.out.println("ID:"+sourceEntity.getId());
        /**
         * 支持部分字段更新
         */
        UpdateTool.copyNullProperties(sourceEntity, entity);
       return dao.save(entity);
    }
    public T  POST(T  entity){
        return dao.save(entity);
    }
    public void DELETE(T entity){
        dao.delete(entity);
    }
    public void DELETE(ID id){
        dao.deleteById(id);
    }
}
