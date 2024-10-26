package com.ctgu.swzl.dao;

import com.ctgu.swzl.domain.Type;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TypeDao {

    @Select("select * from type")
    List<Type> findAll();

    @Insert("insert into type (type_name) values(#{typeName})")
    boolean addType(@Param("typeName") String typeName);
}
