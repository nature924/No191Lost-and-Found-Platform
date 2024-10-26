package com.ctgu.swzl.dao;

import com.ctgu.swzl.domain.Notice;
import com.ctgu.swzl.domain.Post;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface NoticeDao {

    @Select("Select * from notice")
    List<Notice> findAll();

    @Select("select * from notice where cuser=#{cuser}")
    List<Notice> findByCuser(@Param("cuser") String cuser);

    @Delete("delete from notice where id=#{noticeId}")
    boolean deleteById(@Param("noticeId") long noticeId);

    @Insert("insert into notice (cuser,title,content,ctime) values(#{cuser},#{title},#{content},#{ctime})")
    boolean addNotice(Notice notice);
}
