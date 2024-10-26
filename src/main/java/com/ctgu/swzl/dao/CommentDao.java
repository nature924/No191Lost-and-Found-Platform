package com.ctgu.swzl.dao;

import com.ctgu.swzl.domain.Comment;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface CommentDao {

    @Select("Select * from comment")
    @Results(id = "commentMap",value = {
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "uname",column = "uname"),
            @Result(property = "user",column="uname",one = @One(select = "com.ctgu.swzl.dao.UserDao.findUserByUname",fetchType = FetchType.EAGER)),
            @Result(property = "comments",column = "id",one = @One(select = "com.ctgu.swzl.dao.CommentDao.findCommentByParentId",fetchType = FetchType.EAGER))
    })
    List<Comment> findAll();

    @Select("select * from comment where post_id=#{id}")
    @ResultMap("commentMap")
    List<Comment> findByPostId(@Param("id") long id);

    @Select("select * from comment where uname=#{uname}")
    @ResultMap("commentMap")
    List<Comment> findByUname(@Param("uname")String uname);

    @Insert("insert into comment(post_id,uname,content,ctime,parent) values(#{postId},#{uname},#{content},#{ctime},#{parent})")
    boolean addComment(Comment newComment);

    @Insert("insert into comment(post_id,uname,content,ctime) values(#{postId},#{uname},#{content},#{ctime})")
    boolean addComment0(Comment newComment);

    @Select("select * from comment where parent = #{parentId}")
    @ResultMap("commentMap")
    List<Comment> findCommentByParentId(@Param("parentId")long parentId);
}
