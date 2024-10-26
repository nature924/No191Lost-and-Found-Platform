package com.ctgu.swzl.dao;

import com.ctgu.swzl.domain.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface UserDao {

    @Select("Select * from user")
    @Results(id = "userMap",value = {
            @Result(property = "username",column = "username"),
            @Result(property = "allPost",column = "username",many = @Many(select = "com.ctgu.swzl.dao.PostDao.findByUname",fetchType = FetchType.LAZY)),
            @Result(property = "allComment",column = "username",many = @Many(select = "com.ctgu.swzl.dao.CommentDao.findByUname",fetchType = FetchType.LAZY))
    })
    List<User> findAll();

    @Select("Select * from user where username=#{username} and password=#{password}")
    User login(@Param("username") String usernmae, @Param("password") String password);

    @Insert("insert into user (username,password,email,photo,admin) values(#{username},#{password},#{email},#{photo},#{admin})")
    boolean addUser(User user);

    @Select("select * from user where username=#{uname}")
    List<User> findUserByUname(@Param("uname") String uname);

    @Update("update user set age=#{age},sex=#{sex},personal_say=#{personalSay},photo=#{photo},last_time=#{lastTime},password=#{password}," +
            "reward_code=#{rewardCode} where id=#{id}")
    boolean updateUser(User loginUser);

    @Delete("delete from user where id=#{userId}")
    boolean deleteByUid(@Param("userId") long userId);

    @Select("select email from user where username=#{feedbackName}")
    String findMailByName(@Param("feedbackName")String feedbackName);

    @Select("select * from user where email=#{mail}")
    User findUserByMail(@Param("mail") String mail);
}
