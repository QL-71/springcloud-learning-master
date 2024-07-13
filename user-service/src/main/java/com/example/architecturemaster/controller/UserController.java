package com.example.architecturemaster.controller;

import com.example.architecturemaster.common.config.authConfig.AuthAccess;
import com.example.architecturemaster.dao.UserDao;
import com.example.architecturemaster.common.controller.LogicController;
import com.example.architecturemaster.dto.LoginDTO;
import com.example.architecturemaster.entity.User;
import com.example.architecturemaster.facade.UserserviceFacade;
import com.example.architecturemaster.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController extends LogicController<UserService, UserDao, User,Long> implements UserserviceFacade {

    public UserController(@Autowired UserService ls) {
        super(ls);
    }

    @Override
    @AuthAccess
    @PostMapping("/login")
    public User login(@RequestBody LoginDTO loginDTO) throws Exception {
        return getService().login(loginDTO);
        //return modelMapper.map(user1,UserDTO.class);
    }


    @Override
    @GetMapping("/getUserInfo/{id}")
    public User queryOutboundApprovePages(@PathVariable("id") Long id) {
        return getService().GET(id);
    }
}
