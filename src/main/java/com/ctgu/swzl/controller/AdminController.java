package com.ctgu.swzl.controller;

import com.ctgu.swzl.dao.FeedbackDao;
import com.ctgu.swzl.dao.NoticeDao;
import com.ctgu.swzl.dao.TypeDao;
import com.ctgu.swzl.domain.Feedback;
import com.ctgu.swzl.domain.Notice;
import com.ctgu.swzl.domain.Post;
import com.ctgu.swzl.domain.User;
import com.ctgu.swzl.service.PostService;
import com.ctgu.swzl.service.UserService;
import com.ctgu.swzl.utils.Jmail;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private Jmail jmail;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private NoticeDao noticeDao;

    @Autowired
    private FeedbackDao feedbackDao;

    @Autowired
    private TypeDao typeDao;

    /*---------------------------------------------------userManage------------------------------------------------*/
    @GetMapping("/searchUser")
    public ModelAndView findUser(@RequestParam("search") String uname){

        User user = userService.findUserByUname(uname).get(0);
        ModelAndView mv = new ModelAndView();

        mv.addObject("users",user);
        mv.setViewName("/admin/userManage");
        return mv;
    }

    @GetMapping("/userManage")
    public ModelAndView showUser(@RequestParam(value = "page",required = false)Integer page){
        int pageSize=4;
        if (page==null) {
            page=1;
        }
        Page<Object> pages = PageHelper.startPage(page, pageSize);

        ModelAndView modelAndView = new ModelAndView();
        List<User> users = userService.findAll();
        modelAndView.addObject("users",users);

        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageNum(page);
        pageInfo.setPages(pageSize);
        int a = (int)pages.getTotal()%pageSize==0?0:1;
        pageInfo.setPages((int)pages.getTotal()/pageSize+a);
        pageInfo.setPrePage(page-1);
        pageInfo.setNextPage(page+1);
        modelAndView.addObject("pageInfo",pageInfo);
        modelAndView.setViewName("/admin/userManage");
        return modelAndView;
    }

    /*---------------------------------------------------postManage---------------------------------------------------*/
    @GetMapping("/searchPost")
    public ModelAndView findPost(@RequestParam("search") String uname){
        List<Post> posts = postService.findByUname(uname);
        System.out.println(uname);
        System.out.println(posts);
        ModelAndView mv = new ModelAndView();
        mv.addObject("posts",posts);
        mv.setViewName("/admin/postManage");
        return mv;
    }

    @GetMapping("/postManage")
    public ModelAndView showPost(@RequestParam(value = "page",required = false)Integer page){
        int pageSize=4;
        if (page==null) {
            page=1;
        }
        Page<Object> pages = PageHelper.startPage(page, pageSize);

        ModelAndView modelAndView = new ModelAndView();
        List<Post> posts = postService.findAll();
        modelAndView.addObject("posts",posts);

        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageNum(page);
        pageInfo.setPages(pageSize);
        int a = (int)pages.getTotal()%pageSize==0?0:1;
        pageInfo.setPages((int)pages.getTotal()/pageSize+a);
        pageInfo.setPrePage(page-1);
        pageInfo.setNextPage(page+1);
        modelAndView.addObject("pageInfo",pageInfo);
        modelAndView.setViewName("/admin/postManage");
        return modelAndView;
    }

    @ResponseBody
    @DeleteMapping("/deletePost/{id}")
    public void deletePost(@PathVariable("id")long postId){
        postService.deleteById(postId);
    }

    @PostMapping("/postStatus")
    public String updateStatus(@RequestParam("postID")long postId,
                               @RequestParam("status")String status){
        int s = "已完成".equals(status)?0:1;
        Post post = postService.findById(postId).get(0);
        post.setStatus(s);
        postService.updateStatus(post);
        return "redirect:postManage";
    }

    /*---------------------------------------------------noticeManage---------------------------------------------------*/
    @GetMapping("/searchNotice")
    public ModelAndView findNotice(@RequestParam("search") String cuser){
        List<Notice> notices = noticeDao.findByCuser(cuser);
        ModelAndView mv = new ModelAndView();
        mv.addObject("notices",notices);
        mv.setViewName("/admin/noticeManage");
        return mv;
    }

    @GetMapping("/noticeManage")
    public ModelAndView showNotice(@RequestParam(value = "page",required = false)Integer page){
        int pageSize=4;
        if (page==null) {
            page=1;
        }
        Page<Object> pages = PageHelper.startPage(page, pageSize);

        ModelAndView modelAndView = new ModelAndView();
        List<Notice> notices = noticeDao.findAll();
        modelAndView.addObject("notices",notices);

        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageNum(page);
        pageInfo.setPages(pageSize);
        int a = (int)pages.getTotal()%pageSize==0?0:1;
        pageInfo.setPages((int)pages.getTotal()/pageSize+a);
        pageInfo.setPrePage(page-1);
        pageInfo.setNextPage(page+1);
        modelAndView.addObject("pageInfo",pageInfo);
        modelAndView.setViewName("/admin/noticeManage");
        return modelAndView;
    }

    @ResponseBody
    @DeleteMapping("/deleteNotice/{id}")
    public void deleteNotice(@PathVariable("id")long noticeId){
        noticeDao.deleteById(noticeId);
    }

    @PostMapping("/addNotice")
    public String addNotice(HttpServletRequest request,Notice notice){
        String cuser = request.getSession().getAttribute("loginUser").toString();
        notice.setCuser(cuser).setCtime(new Date());
        noticeDao.addNotice(notice);
        return "redirect:noticeManage";
    }
    /*---------------------------------------------------feedbackManage---------------------------------------------------*/
    @GetMapping("/searchFeedback")
    public ModelAndView findFeedback(@RequestParam("search") String uname){
        List<Feedback> feedbacks = feedbackDao.findByUname(uname);
        ModelAndView mv = new ModelAndView();
        mv.addObject("feedbacks",feedbacks);
        mv.setViewName("/admin/feedbackManage");
        return mv;
    }

    @GetMapping("/feedbackManage")
    public ModelAndView showFeedback(@RequestParam(value = "page",required = false)Integer page){
        int pageSize=4;
        if (page==null) {
            page=1;
        }
        Page<Object> pages = PageHelper.startPage(page, pageSize);

        ModelAndView modelAndView = new ModelAndView();
        List<Feedback> feedbacks = feedbackDao.findAll();
        modelAndView.addObject("feedbacks",feedbacks);

        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageNum(page);
        pageInfo.setPages(pageSize);
        int a = (int)pages.getTotal()%pageSize==0?0:1;
        pageInfo.setPages((int)pages.getTotal()/pageSize+a);
        pageInfo.setPrePage(page-1);
        pageInfo.setNextPage(page+1);
        modelAndView.addObject("pageInfo",pageInfo);
        modelAndView.setViewName("/admin/feedbackManage");
        return modelAndView;
    }

    @ResponseBody
    @DeleteMapping("/deleteFeedback/{id}")
    public void deleteFeedback(@PathVariable("id")long feedbackId){
        feedbackDao.deleteById(feedbackId);
    }

    @PostMapping("/reply")
    public String reply(@RequestParam("feedbackId")long feedbackId,
                      @RequestParam("content")String content,
                      HttpServletRequest request){
        String admin = request.getSession().getAttribute("loginUser").toString();
        String feedbackName = feedbackDao.fingNameById(feedbackId);
        String tomail = userService.findMailByName(feedbackName);
        jmail.setToMail(tomail);
        jmail.setTopic("失物招领回复");
        jmail.setContent(content);
        System.out.println(tomail+":::"+content);
        try {
            jmail.send();
        } catch (MessagingException e) {
            throw new RuntimeException("邮箱发送失败！");
        }
        return "redirect:feedbackManage";
    }


    @ResponseBody
    @GetMapping("/addType")
    public void addType(@RequestParam("typeName") String typeName){
        typeDao.addType(typeName);
    }

}
