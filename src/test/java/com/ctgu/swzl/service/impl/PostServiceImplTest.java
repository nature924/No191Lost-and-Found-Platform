package com.ctgu.swzl.service.impl;

import com.ctgu.swzl.domain.Post;
import com.ctgu.swzl.service.PostService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PostServiceImplTest {

    @Autowired
    private PostService postService;

    @Test
    public void findAll() {
        List<Post> all = postService.findAll();
        all.forEach(System.out::println);
    }
}