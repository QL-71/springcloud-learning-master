package com.example.architecturemaster.service;

import com.example.architecturemaster.common.utils.TokenUtils;
import com.example.architecturemaster.dao.UserDao;
import com.example.architecturemaster.VO.Exception.MessageException;
import com.example.architecturemaster.common.service.LogicService;
import com.example.architecturemaster.dto.LoginDTO;
import com.example.architecturemaster.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class UserService extends LogicService<UserDao, User,Long> {
    public UserService(@Autowired UserDao dao) {
        super(dao);
    }

    public User login(LoginDTO loginDTO) throws MessageException {
        String userTelephone = loginDTO.getUserTelephone();
        String userPassword = DigestUtils.md5DigestAsHex(loginDTO.getUserPassword().getBytes());
        User user1 = getDAO().findUserByUserTelephoneAndUserPassword(userTelephone, userPassword);
        if (user1 == null) throw new MessageException();
        String token = TokenUtils.createToken(user1.getId().toString(),user1.getUserPassword());
        String refreshToken = TokenUtils.createRefreshToken(user1.getId().toString(),user1.getUserTelephone());
        user1.setToken(token);
        user1.setRefreshToken(refreshToken);
        getDAO().save(user1);
        return user1;
    }


}
