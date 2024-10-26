package com.ctgu.swzl.service.impl;

import com.ctgu.swzl.dao.UserDao;
import com.ctgu.swzl.domain.User;
import com.ctgu.swzl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User login(String usernmae, String password) {
        return userDao.login(usernmae,password);
    }

    @Override
    public boolean addUser(User user) {
        return userDao.addUser(user);
    }

    @Override
    public List<User> findUserByUname(String uname) {
        return userDao.findUserByUname(uname);
    }

    @Override
    public boolean updateUser(User loginUser) {
        return userDao.updateUser(loginUser);
    }

    @Override
    public boolean deleteByUid(long userId) {
        return userDao.deleteByUid(userId);
    }

    @Override
    public String findMailByName(String feedbackName) {
        return userDao.findMailByName(feedbackName);
    }

    @Override
    public User findUnameByMail(String mail) {
        return userDao.findUserByMail(mail);
    }
}
