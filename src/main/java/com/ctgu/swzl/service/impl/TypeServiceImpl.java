package com.ctgu.swzl.service.impl;

import com.ctgu.swzl.dao.PostDao;
import com.ctgu.swzl.dao.TypeDao;
import com.ctgu.swzl.domain.Type;
import com.ctgu.swzl.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeDao typeDao;

    @Autowired
    private PostDao postDao;

    @Override
    public List<Type> findAll() {
        return typeDao.findAll();
    }

    @Override
    public int getCountByName(String typeName) {
        int size = postDao.findByTypeName(typeName).size();
        return size;
    }

}
