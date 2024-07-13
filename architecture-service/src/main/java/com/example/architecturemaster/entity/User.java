package com.example.architecturemaster.entity;

import com.example.architecturemaster.common.entity.LogicEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.springframework.util.DigestUtils;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Inheritance
@Where(clause = "is_deleted = 0")
@DiscriminatorColumn(name = "userType",discriminatorType = DiscriminatorType.INTEGER)
public abstract class User extends LogicEntity {
    @Column
    protected String userName;
    @Column
    protected String userPassword;
    public void setUserPassword(String userPassword){
        this.userPassword = DigestUtils.md5DigestAsHex(userPassword.getBytes());
    }
    @Column
    protected Integer userSex;
    @Column
    protected String userTelephone;
    @Column
    protected String userImg;
    @Column(updatable = false,insertable = false)
    protected Integer userType;
    @Column
    protected String token;
    @Column
    protected String refreshToken;
}
