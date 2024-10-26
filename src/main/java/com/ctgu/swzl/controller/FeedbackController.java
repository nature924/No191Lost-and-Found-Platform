package com.ctgu.swzl.controller;

import com.ctgu.swzl.dao.FeedbackDao;
import com.ctgu.swzl.domain.Feedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
public class FeedbackController {

    @Autowired
    private FeedbackDao feedbackDao;

    @GetMapping("/feedback")
    public String toFeedback(){
        return "feedback";
    }

    @ResponseBody
    @PostMapping("/feedback")
    public void addFeedback(Feedback feedback, HttpServletRequest request){
        String uname = (String) request.getSession().getAttribute("loginUser");
        feedback.setUname(uname).setCtime(new Date());
        feedbackDao.addFeedback(feedback);
    }
}
