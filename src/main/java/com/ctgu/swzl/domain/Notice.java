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
public class Notice implements Serializable {

    private long id;
    private String cuser;
    private String content;
    private Date ctime;
    private String title;
}
