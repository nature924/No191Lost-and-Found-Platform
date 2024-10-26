package com.ctgu.swzl.service.impl;

import com.ctgu.swzl.dao.PostDao;
import com.ctgu.swzl.domain.Post;
import com.ctgu.swzl.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostDao postDao;

    @Override
    public List<Post> findAll() {
        return postDao.findAll();
    }

    @Override
    public List<Post> findBySL(int S, int L) {
        return postDao.findBySL(S,L);
    }

    @Override
    public boolean addPost(Post post) {
        return postDao.addPost(post);
    }

    @Override
    public List<Post> findById(long id) {
        return postDao.findById(id);
    }

    @Override
    public List<Post> findByUname(String username) {
        return postDao.findByUname(username);
    }

    @Override
    public boolean deleteById(long id) {
        return postDao.deleteById(id);
    }

    @Override
    public List<Post> findByTypeName(String typeName) {
        return postDao.findByTypeName(typeName);
    }

    @Override
    public boolean updateStatus(Post post) {
        return postDao.updateStatus(post);
    }

    @Override
    public boolean updateViewCount(Post post) {
        return postDao.updateViewCount(post);
    }
}
