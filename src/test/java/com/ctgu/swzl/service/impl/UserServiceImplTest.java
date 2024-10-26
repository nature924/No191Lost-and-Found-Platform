package com.ctgu.swzl.service.impl;

import com.ctgu.swzl.dao.*;
import com.ctgu.swzl.domain.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private NoticeDao noticeDao;

    @Autowired
    private PostDao postDao;

    @Autowired
    private FeedbackDao feedbackDao;

    @Autowired
    private TypeDao typeDao;

    @Test
    public void findAll() {
        List<User> findall = userDao.findAll();
        findall.forEach(System.out::println);
    }
    @Test
    public void findAll2() {
        List<Comment> findall = commentDao.findAll();
        findall.forEach(System.out::println);
    }
    @Test
    public void findAll3() {
        List<Notice> findall = noticeDao.findAll();
        findall.forEach(System.out::println);
    }
    @Test
    public void findAll4() {
        List<Post> findall = postDao.findAll();
        findall.forEach(System.out::println);
    }
    @Test
    public void findAll5() {
        List<Feedback> findall = feedbackDao.findAll();
        findall.forEach(System.out::println);
    }
    @Test
    public void findAll6() {
        List<Type> findall = typeDao.findAll();
        findall.forEach(System.out::println);
    }

    @Test
    public void findByTypeName(){
        List<Post> posts = postDao.findByTypeName("校园卡");
        posts.forEach(System.out::println);
    }

    @Test
    public void test(){
        Page<Object> page = PageHelper.startPage(1,2);
        List<User> all = userDao.findAll();
        all.forEach(System.out::println);
        PageInfo<User> pageInfo = new PageInfo<>(all);
        System.out.println(pageInfo);
    }

    @Test
    public void testComment(){
        List<Comment> posts = commentDao.findByPostId(2);
        posts.forEach(System.out::println);
    }

}