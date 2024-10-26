package com.ctgu.swzl.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SimpleJson {

    @Autowired
    private ObjectMapper mapper;

    public Map<String,String> JsonToMap(String json) throws JsonProcessingException {

        Map<String,String> map = mapper.readValue(json, Map.class);
        return map;
    }

}
