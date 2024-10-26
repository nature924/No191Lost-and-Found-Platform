package com.ctgu.swzl.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Accessors(chain = true)
@ToString
@NoArgsConstructor
public class User implements Serializable {
    private Long id;
    private String username;
    private String password;
    private String email;
    private int age;
    private char sex;
    private String photo;
    private String rewardCode;
    private String personalSay;
    private Date lastTime;
    private String admin;

    private List<Post> allPost;
    private List<Comment> allComment;
}
