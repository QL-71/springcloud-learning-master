package com.example.architecturemaster.dao;

import com.example.architecturemaster.common.dao.LogicDAO;
import com.example.architecturemaster.entity.User;

public interface UserDao extends LogicDAO<User,Long> {
    User findUserByUserTelephoneAndUserType(String telephone,Integer userType);
    User findUserByUserTelephoneAndUserPassword(String telephone,String pwd);
    User findUserById(Long userId);
    User findUserByUserTelephone(String telephone);
}
