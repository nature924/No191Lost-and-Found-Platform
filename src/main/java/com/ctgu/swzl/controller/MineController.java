package com.ctgu.swzl.controller;

import com.ctgu.swzl.domain.Post;
import com.ctgu.swzl.domain.User;
import com.ctgu.swzl.service.PostService;
import com.ctgu.swzl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
public class MineController {

    @Value("${images.path}")
    private String path;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @GetMapping("/mine")
    public ModelAndView toMine(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        String uname = request.getSession().getAttribute("loginUser").toString();
        List<User> users = userService.findUserByUname(uname);
        List<Post> posts = postService.findByUname(uname);
        modelAndView.addObject("mine",users.get(0));
        modelAndView.addObject("posts",posts);
        modelAndView.setViewName("mine");
        return modelAndView;
    }

    @PostMapping("/mine")
    public String updateMine(@RequestParam(value = "frequency",required = false)String sexx,
                             @RequestParam(value = "age",required = false)Integer age,
                             @RequestParam(value = "personalSay",required = false)String personalSay,
                             @RequestParam(value = "upload",required = false) MultipartFile upload,
                             @RequestParam(value = "upload5",required = false) MultipartFile upload5,
                             HttpServletRequest request) throws IOException {
        User loginUser = (User) request.getSession().getAttribute("localUser");

        String filename = upload.getOriginalFilename();
        if (filename.length()!=0){
            filename = UUID.randomUUID().toString().replaceAll("-", "") + "_" + filename;
            upload.transferTo(new File(path, filename));
            loginUser.setPhoto("/images/"+filename);
        }
        String filename5 = upload5.getOriginalFilename();
        if (filename5.length()!=0){
            filename5 = UUID.randomUUID().toString().replaceAll("-", "") + "_" + filename5;
            upload5.transferTo(new File(path, filename5));
            loginUser.setRewardCode("/images/"+filename5);
        }

        if (sexx.length()!=0) {
            char sex = "man".equals(sexx) ? '男' : '女';
            loginUser.setSex(sex);
        }
        if (age!=null) {
            loginUser.setAge(age);
        }
        if (personalSay.length()!=0){
            loginUser.setPersonalSay(personalSay);
        }
        userService.updateUser(loginUser);
        return "redirect:/mine";
    }

    @ResponseBody
    @DeleteMapping("/mine/{id}")
    public String deletePostById(@PathVariable("id") long postId) {
        try {
            postService.deleteById(postId);
        } catch (Exception e) {
            e.printStackTrace();
//            throw new CustomException("删除失败");
        }
        return "success";
    }

    @PostMapping("/postStatus")
    public String updateStatus(@RequestParam("postID")long postId,
                               @RequestParam("status")String status){
        int s = "已完成".equals(status)?0:1;
        Post post = postService.findById(postId).get(0);
        post.setStatus(s);
        postService.updateStatus(post);
        return "redirect:mine";
    }
}
