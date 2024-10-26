package com.ctgu.swzl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ctgu.swzl.dao")
public class SwzlApplication {

    public static void main(String[] args) {
        SpringApplication.run(SwzlApplication.class, args);
    }

}
