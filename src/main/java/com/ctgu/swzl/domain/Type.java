package com.ctgu.swzl.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@ToString
@NoArgsConstructor
public class Type implements Serializable {
    private int id;
    private String typeName;
}
