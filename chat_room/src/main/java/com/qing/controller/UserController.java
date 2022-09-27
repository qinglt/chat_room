package com.qing.controller;

import com.qing.pojo.Result;
import com.qing.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author lianggq
 * @date 2022/9/22 15:39
 */
@Controller
@Slf4j
public class UserController {

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Result login(User user, HttpSession session){
        Result result = new Result();
        if (user != null && user.getPassword().equals("123456")) {
            result.setFlag(true);
            result.setMessage("登陆成功");
            session.setAttribute("user", user.getUsername());
            log.info("============User: " + user.getUsername() + " 登陆成功============");
        } else {
            result.setFlag(false);
            result.setMessage("登陆失败");
            log.info("============User: " + user.getUsername() + " 登陆失败============");
        }
        return result;
    }

    @RequestMapping(value = "/getUsername", method = RequestMethod.GET)
    @ResponseBody
    public String getUsername(HttpSession session) {
        return (String) session.getAttribute("user");
    }

}
