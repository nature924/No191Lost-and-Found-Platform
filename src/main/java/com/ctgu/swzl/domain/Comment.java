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
public class Comment implements Serializable {

    private long id;
    private long postId;
    private String uname;
    private String content;
    private Date ctime;
    private long parent;

    private User user;
    private List<Comment> comments;

}
