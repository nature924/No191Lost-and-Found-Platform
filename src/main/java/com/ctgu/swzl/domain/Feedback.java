package com.ctgu.swzl.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
@ToString
@NoArgsConstructor
public class Feedback implements Serializable {

    private long id;
    private String uname;
    private String title;
    private String content;
    private Date ctime;

}
