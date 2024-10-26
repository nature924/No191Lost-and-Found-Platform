package com.ctgu.swzl.dao;

import com.ctgu.swzl.domain.Feedback;
import com.ctgu.swzl.domain.Notice;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface FeedbackDao {

    @Select("Select * from feedback")
    List<Feedback> findAll();

    @Insert("insert into feedback (uname,title,content,ctime) values(#{uname},#{title},#{content},#{ctime})")
    boolean addFeedback(Feedback feedback);

    @Select("select * from feedback where uname=#{uname}")
    List<Feedback> findByUname(@Param("uname") String uname);

    @Delete("delete from feedback where id=#{id}")
    boolean deleteById(@Param("id") long feedbackId);

    @Select("select uname from feedback where id=#{feedbackId}")
    String fingNameById(@Param("feedbackId") long feedbackId);
}
