package com.ctgu.swzl.controller;

import com.ctgu.swzl.domain.User;
import com.ctgu.swzl.service.UserService;
import com.ctgu.swzl.utils.Jmail;
import com.ctgu.swzl.utils.SimpleJson;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Map;
import java.util.Random;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private Jmail jmail;

    @Autowired
    private SimpleJson simpleJson;

    /*---------------------------------账号注册-------------------------------*/
    @GetMapping("/register")
    public String toRegister(){
        return "register";
    }

    @ResponseBody
    @PostMapping("/register")
    public String register(@RequestParam("username")String username,
                           @RequestParam("password")String password,
                           @RequestParam("mail")String mail,
                           @RequestParam("code")String coding,
                           HttpServletRequest request) throws JsonProcessingException {
        User user = new User();
        user.setUsername(username)
                .setPassword(DigestUtils.md5DigestAsHex(password.getBytes()))
                .setEmail(mail)
                .setPhoto("/images/default.jpg")
                .setAdmin("false");
        String code = (String) request.getSession().getAttribute("code");
        if (code==null||!code.equals(coding)){
            return "2";             // 验证码错误，返回2
        }
        boolean user1 = false;
        try {
            user1 = userService.addUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            return "0";             // 添加失败，返回0，一般是用户名重复问题
        }
        return "1";                 // 注册成功，返回1
    }

    @ResponseBody
    @PostMapping("/getcode")    //邮箱验证码
    public void getCode(HttpServletRequest request,
                        @RequestBody String body) throws Exception {
        Map<String,String> data = simpleJson.JsonToMap(body);
        Random random = new Random();
        String code = "";
        for (int i=0;i<6;i++) {
            code += random.nextInt(9);
        }
        request.getSession().setAttribute("code",code);
        jmail.setToMail(data.get("mail"));
        jmail.setTopic("失物招验证码");
        jmail.setContent("您好，本次验证码为："+code);
        try {
            jmail.send();
        } catch (MessagingException e) {
        	e.printStackTrace();
            throw new RuntimeException("邮箱发送失败！");
        }
    }


    /*---------------------------------账号登录-------------------------------*/
    @GetMapping("/login")
    public String toLogin(){
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestParam("username")String username,
                        @RequestParam("password")String password,
                        @RequestParam("remember")String remember,
                        HttpServletRequest request,
                        HttpServletResponse response) throws JsonProcessingException {
        String password_back = password;
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        User login = userService.login(username, password);
        if (login!=null){
            login.setLastTime(new Date());
            userService.updateUser(login);
            HttpSession session = request.getSession();
            session.setAttribute("loginUser",username);
            session.setAttribute("isAdmin",login.getAdmin());
            User user = userService.findUserByUname(username).get(0);
            session.setAttribute("localUser",user);
            if (remember.equals("true")){
                Cookie ucookie = new Cookie("unameCookie",username);
                Cookie pcookie = new Cookie("passwdCookie",password_back);
                ucookie.setMaxAge(24*3600);  //cookies 保存一天
                pcookie.setMaxAge(24*3600);
                response.addCookie(ucookie);
                response.addCookie(pcookie);
            }
            if ("true".equals(login.getAdmin())){
                return "2";       //管理员登录返回2
            }
            return "1";           // 普通登陆，返回1
        }

        return "0";                 // 失败，返回0.用户名或密码错误
    }


    /*---------------------------------密码重置-------------------------------*/
    @GetMapping("/repassword")
    public String toRepass(){
        return "repassword";
    }

    @PostMapping("/repassword")
    @ResponseBody
    public String repassword(@RequestParam("newpass")String pass,
                             HttpServletRequest request,
                             @RequestParam("mail")String mail,
                             @RequestParam("code")String code,
                             @RequestParam("username")String username) throws MessagingException {
        String localcode = (String) request.getSession().getAttribute("code");
        if (!localcode.equals(code)){
            return "0";
        }
        pass = DigestUtils.md5DigestAsHex(pass.getBytes());
        User user = userService.findUserByUname(username).get(0);
        if (user==null){
            return "2";
        }
        user.setPassword(pass);
        userService.updateUser(user);
        jmail.setToMail(mail);
        jmail.setTopic("swzl密码重置");
        jmail.setContent("您的密码已经重置成功，赶紧使用新密码登录吧！");
        jmail.send();
        return "1";
    }

    @GetMapping("/logout")   //  账号退出
    public String logout(HttpServletRequest request){
        request.getSession().invalidate();
        return "redirect:/index";
    }

}
