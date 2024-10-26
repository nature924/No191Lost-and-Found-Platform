package com.ctgu.swzl.controller;

import com.ctgu.swzl.dao.CommentDao;
import com.ctgu.swzl.dao.NoticeDao;
import com.ctgu.swzl.domain.Comment;
import com.ctgu.swzl.domain.Notice;
import com.ctgu.swzl.domain.Post;
import com.ctgu.swzl.domain.Type;
import com.ctgu.swzl.service.PostService;
import com.ctgu.swzl.service.TypeService;
import com.ctgu.swzl.utils.SimpleJson;
import com.ctgu.swzl.vo.TypeVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class PostController {

    @Value("${images.path}")
    private String path;

    @Autowired
    private PostService postService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private SimpleJson simpleJson;

    @Autowired
    private NoticeDao noticeDao;

    @GetMapping("/index")
    public String getPost(@RequestParam(value = "status",required = false)Integer status,
                          @RequestParam(value = "flag",required = false) Integer flag,
                          @RequestParam(value = "type",required = false) String typeNmae){
        if (status==null) {
            if (typeNmae==null) {
                return "forward:/index/2/2/false";
            }else {
                return "forward:/index/2/2/"+typeNmae;
            }
        }
        return "forward:/index/"+status+"/"+flag+"/false";
    }

    @GetMapping("/index/{status}/{flag}/{typeName}")
    public ModelAndView getAppoint(@PathVariable("status")int status,
                                   @PathVariable("flag")int flag,
                                   @PathVariable("typeName") String tname,
                                   @RequestParam(value = "search",required = false)String search,
                                   @RequestParam(value = "page",required = false)Integer page){
        List<Post> posts = new ArrayList<>();
        ModelAndView modelAndView = new ModelAndView();

        int pageSize=4;
        if (page==null) {
            page=1;
        }
        Page<Object> pages = PageHelper.startPage(page, pageSize,"ctime desc");

        if (status==0&&flag==0){
            posts = postService.findBySL(0,0);
            modelAndView.addObject("message","已经找到");
        }else if (status==0&&flag==1){
            posts = postService.findBySL(0,1);
            modelAndView.addObject("message","已被认领");
        } else if (status==1&&flag==0){
            posts = postService.findBySL(1,0);
            modelAndView.addObject("message","正在寻找");
        } else if (status==1&&flag==1){
            posts = postService.findBySL(1,1);
            modelAndView.addObject("message","等待认领");
        }else {
            if ("false".equals(tname)) {
                posts = postService.findAll();
                modelAndView.addObject("message","启事");
                if (search!=null){
                    posts = posts.stream().filter(a -> a.getContent().contains(search)).collect(Collectors.toList());
                    modelAndView.addObject("message","根据关键字<"+search+">找到如下记录");
                    pages.setTotal(posts.size());
                }
            }else {
                posts = postService.findByTypeName(tname);
                modelAndView.addObject("message",tname);
            }

        }
//        posts.sort((a, b) -> b.getCtime().compareTo(a.getCtime()));

        PageInfo<Post> pageInfo = new PageInfo<Post>(posts);
        pageInfo.setPageNum(page);
        pageInfo.setPages(pageSize);
        int a = (int)pages.getTotal()%pageSize==0?0:1;
        pageInfo.setPages((int)pages.getTotal()/pageSize+a);
        pageInfo.setPrePage(page-1);
        pageInfo.setNextPage(page+1);

        List<Type> types = typeService.findAll();
        List<TypeVO> list = new ArrayList<>();
        for (Type type : types) {
            TypeVO typeVO = new TypeVO();
            String typeName = type.getTypeName();
            typeVO.setName(typeName);
            typeVO.setCount(typeService.getCountByName(typeName));
            list.add(typeVO);
        }
        List<Integer> SL = new ArrayList<>();
        List<Notice> notices = noticeDao.findAll();
        SL.add(postService.findBySL(1,0).size()); //正在寻找
        SL.add(postService.findBySL(0,0).size()); //已经找到
        SL.add(postService.findBySL(1,1).size()); //等待认领
        SL.add(postService.findBySL(0,1).size()); //已被认领
        modelAndView.addObject("postList",posts);
        modelAndView.addObject("typeList",list);
        modelAndView.addObject("notices",notices);
        modelAndView.addObject("SL",SL);
        modelAndView.addObject("pageInfo",pageInfo);
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping("/doSearch")
    public String doSearch(@RequestParam("content")String content){
        return "forward:/index/2/2/false?search="+content;
    }

    @GetMapping("/announce")
    public ModelAndView toAnnounce(){
        List<Type> types = typeService.findAll();
        ModelAndView mv = new ModelAndView();
        mv.addObject("types",types);
        mv.setViewName("announce.html");
        return mv;
    }

    @PostMapping("/announce")
    public String addPost(HttpServletRequest request,
                          Post post,
                          @RequestParam("fruit") String xr,
                          MultipartFile upload) throws IOException {
        String uname = (String) request.getSession().getAttribute("loginUser");
        int flag = xr.equals("x")?0:1;
        post.setUname(uname).setFlag(flag).setStatus(1).setCtime(new Date());
        File file = new File(path);
        if (!file.exists()){
            file.mkdirs();
        }
        String filename = upload.getOriginalFilename();
        filename = UUID.randomUUID().toString().replaceAll("-","")+"_"+filename;
        upload.transferTo(new File(path,filename));
        post.setPicture("/images/"+filename);
        postService.addPost(post);
        return "redirect:/index";
    }


    @GetMapping("/detail")
    public ModelAndView toDetail(@RequestParam("postId")Long postId){
        ModelAndView modelAndView = new ModelAndView();
        Post post = postService.findById(postId).get(0);
        modelAndView.addObject("post",post);
        modelAndView.setViewName("detail");
        post.setViewCount(post.getViewCount()+1);
        postService.updateViewCount(post);
        return modelAndView;
    }

    @ResponseBody
    @PostMapping("/addcomment")
    public String addComment(@RequestParam("comment") String comment,
                            @RequestParam("postId") long postId,
                            @RequestParam("parentId")long parentId,
                            HttpServletRequest request) {
        Object loginUser = request.getSession().getAttribute("loginUser");
            if (loginUser==null){
                return "0";
        }
        Comment newComment = new Comment();
        String uname = (String) request.getSession().getAttribute("loginUser");
        newComment.setUname(uname)
                .setContent(comment)
                .setPostId(postId)
                .setCtime(new Date())
                .setParent(parentId);
        if (parentId==0){
            commentDao.addComment0(newComment);
        }else {
            commentDao.addComment(newComment);
        }
        return "1";
    }
}
