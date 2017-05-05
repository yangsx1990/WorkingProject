package com.hiersun.oohdear.user.controller;/**
 * Created by liubaocheng on 2017/2/28.
 */

import java.util.List;

import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hiersun.oohdear.config.redis.RedisUtil;
import com.hiersun.oohdear.els.UserEls;
import com.hiersun.oohdear.user.entity.User;
import com.hiersun.oohdear.user.mapper.UserMapper;

/**
 * Description:
 * Author: liubaocheng
 * Create: 2017-02-28 9:59
 **/
@Controller
public class IndexController {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UserMapper userMapper;

   /* @Autowired
    UserRepository userRepository;
*/

  /*  @Autowired
    private UserEls userEls;*/


    @RequestMapping("test1")
    @ResponseBody
    public List<User> index(){

        return userMapper.selectAll();
    }

    @RequestMapping("test")
    public String demo(Model model){
        model.addAttribute("value","Welcome to Ooh_dear!");
        return "test";
    }


    @RequestMapping("redis")
    @ResponseBody
    public Boolean setredis(){
        return  redisUtil.set("liuyang1991","xiaoxianrou");
    }


    @RequestMapping("getRedis")
    @ResponseBody
    public Object getredis(){
        return  redisUtil.get("liuyang1991");
    }


    /*@RequestMapping("mongo")
    @ResponseBody
    public Object mongo(User user){
        user.setId(13);
        user.setName("liuyang");
        user.setPhone("animal apple shanghai");
        return  userRepository.save(user);
    }*/

/*
    @RequestMapping("getMongo")
    @ResponseBody
    public List<User> getMongo(){
        return  userRepository.findByName("liuyang");
    }
*/
   /* @RequestMapping("els")
    @ResponseBody
    public Object els(User user){
        user.setId(20);
        user.setName("之所以说是最简单，是因为这样能搭建出一个能跑出来结果的框架，而更高级的功能往往就是在最简单的框架的基础上建设的，对吧。");
        user.setPhone("animal apple shanghai");
        return  userEls.save(user);
    }*/


    /**
     *  page 分页
     * @return
     */
   /* @RequestMapping("getEls")
    @ResponseBody
    public Object getEls(){
        Page<User> page = userEls.search(QueryBuilders.termQuery("name","框架"),new PageRequest(1,1));
     return page.getContent();

    }*/


}
