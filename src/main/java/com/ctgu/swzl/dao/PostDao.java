package com.ctgu.swzl.dao;

import com.ctgu.swzl.domain.Post;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface PostDao {

    @Select("select * from post")
    @Results(id = "postMap",value = {
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "uname",column = "uname"),
            @Result(property = "allComment",column = "id",many = @Many(select = "com.ctgu.swzl.dao.CommentDao.findByPostId",fetchType = FetchType.LAZY)),
            @Result(property = "user",column="uname",one = @One(select = "com.ctgu.swzl.dao.UserDao.findUserByUname",fetchType = FetchType.EAGER))
    })
    List<Post> findAll();

    @Select("select * from post where type_name=#{typeName}")
    @ResultMap("postMap")
    List<Post> findByTypeName(@Param("typeName") String typeName);

    @Select("select * from post where id=#{id}")
    @ResultMap("postMap")
    List<Post> findById(@Param("id")long id);

    @Select("select * from post where status=#{S} and flag=#{L}")
    @ResultMap("postMap")
    List<Post> findBySL(@Param("S")int S,@Param("L")int L);

    @Select("select * from post where uname=#{username}")
    @ResultMap("postMap")
    List<Post> findByUname(@Param("username")String username);

    @Insert("insert into post(uname,type_name,title,content,picture,address,ctime,status,flag) values" +
            "(#{uname},#{typeName},#{title},#{content},#{picture},#{address},#{ctime},#{status},#{flag})")
    boolean addPost(Post post);

    @Delete("delete from post where id=#{id}")
    boolean deleteById(@Param("id") long id);

    @Update("update post set status=#{status} where id=#{id}")
    boolean updateStatus(Post post);

    @Update("update post set view_count=#{viewCount} where id=#{id}")
    boolean updateViewCount(Post post);

}
