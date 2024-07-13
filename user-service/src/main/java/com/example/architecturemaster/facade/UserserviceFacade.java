package com.example.architecturemaster.facade;

import com.example.architecturemaster.entity.User;
import com.example.architecturemaster.dto.LoginDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author siyue
 * @ClassName UserserviceFacade
 * @description: TODO
 * @date 2024年07月06日
 * @version: 1.0
 */
public interface UserserviceFacade {

    @GetMapping("userservice/getUserInfo/{id}")
    User queryOutboundApprovePages(@PathVariable("id") Long id);

    @PostMapping("userservice/login")
    User login(@RequestBody LoginDTO loginDTO) throws Exception;
}
