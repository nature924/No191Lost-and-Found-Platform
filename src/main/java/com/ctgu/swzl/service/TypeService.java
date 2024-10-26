package com.ctgu.swzl.service;

import com.ctgu.swzl.domain.Type;

import java.util.List;

public interface TypeService {

    List<Type> findAll();

    int getCountByName(String typeName);

}
