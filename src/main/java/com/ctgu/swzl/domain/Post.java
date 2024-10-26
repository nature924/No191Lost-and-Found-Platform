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
public class Post implements Serializable {

    private long id;
    private String uname;
    private String typeName;
    private String title;
    private String content;
    private String picture;
    private String address;
    private Date ctime;
    private int status;
    private int flag;
    private int viewCount;

    private List<Comment> allComment;
    private User user;
    private Type type;
}
