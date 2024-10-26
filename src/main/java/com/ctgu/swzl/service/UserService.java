package com.ctgu.swzl.service;

import com.ctgu.swzl.domain.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User login(String usernmae,String password);

    boolean addUser(User user);

    List<User> findUserByUname(String uname);

    boolean updateUser(User loginUser);

    boolean deleteByUid(long userId);

    String findMailByName(String feedbackName);

    User findUnameByMail(String mail);

}
