package com.ctgu.swzl.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice    /*异常处理器*/
public class WebExceptionHandle {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    public ModelAndView exceptiongHandle(HttpServletRequest request,Exception e){
        logger.error("Request URL:{},Method:{}---Exception : {}",request.getRequestURL(),request.getMethod(),e);

        ModelAndView mv = new ModelAndView();
        mv.addObject("url",request.getRequestURL());
        mv.addObject("Exception",e);
        mv.setViewName("/error/error");
        return mv;
    }
}
