package com.example.architecturemaster.controller;

import com.example.architecturemaster.VO.Exception.MessageException;
import com.example.architecturemaster.common.config.authConfig.AuthAccess;
import com.example.architecturemaster.common.controller.LogicController;
import com.example.architecturemaster.dao.UserDao;
import com.example.architecturemaster.dto.LoginDTO;
import com.example.architecturemaster.dto.UserDTO;
import com.example.architecturemaster.entity.User;
import com.example.architecturemaster.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/UserController")
@Tag(name = "用户服务接口")
public class UserController extends LogicController<UserService, UserDao,User,Long> {
    @Resource
    private ModelMapper modelMapper;
    public UserController(@Autowired UserService ls) {
        super(ls);
    }

    @AuthAccess
    @PostMapping("/login")
    @Operation(summary = "用户登录")
    @Parameter(name = "user",description = "用户信息json",required = true)
    public UserDTO login(@RequestBody LoginDTO loginDTO) throws MessageException {
        User user1 = getService().login(loginDTO);
        return modelMapper.map(user1,UserDTO.class);
    }

    @Transactional
    @GetMapping("/getUserInfo")
    @Operation(summary = "获取用户信息")
    @Parameter(name = "userId",description = "用户id",required = true,example = "1")
    public UserDTO getBusinessInfo(@RequestParam Long userId){
        User business = getService().GET(userId);
        return modelMapper.map(business,UserDTO.class);
    }


}
