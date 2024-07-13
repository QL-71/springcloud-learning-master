package com.example.architecturemaster.service;

import com.example.architecturemaster.service.impl.UserFallbackService;
import com.example.architecturemaster.pojo.Result;
import com.example.architecturemaster.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Description:添加UserService接口完成对user-service服务的接口绑定
 *             修改UserService接口，设置服务降级处理类为UserFallbackService
 *
 */
@FeignClient(value = "user-service", fallback = UserFallbackService.class)
public interface UserService {

    @PostMapping("/user/insert")
    Result insert(@RequestBody User user);

    @GetMapping("/user/{id}")
    Result<User> getUser(@PathVariable Long id);

    @GetMapping("/user/listUsersByIds")
    Result<List<User>> listUsersByIds(@RequestParam List<Long> ids);

    @GetMapping("/user/getByUsername")
    Result<User> getByUsername(@RequestParam String username);

    @PostMapping("/user/update")
    Result update(@RequestBody User user);

    @PostMapping("/user/delete/{id}")
    Result delete(@PathVariable Long id);

}
