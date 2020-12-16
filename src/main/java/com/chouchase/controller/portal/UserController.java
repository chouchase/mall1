package com.chouchase.controller.portal;

import com.chouchase.common.Const;
import com.chouchase.common.ServerResponse;
import com.chouchase.domain.User;
import com.chouchase.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/user")
@ResponseBody
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ServerResponse<String> register(User user) {
        if(StringUtils.isAnyBlank(user.getUsername(),user.getPassword())){
            return ServerResponse.createIllegalArgsResponse();
        }
        return userService.register(user);
    }
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ServerResponse<User> login(String username, String password, HttpSession session) {
        if(StringUtils.isAnyBlank(username,password)){
            return ServerResponse.createIllegalArgsResponse();
        }
        ServerResponse<User> response = userService.login(username, password);
        if (response.isSuccess()) {
            session.setAttribute(Const.CURRENT_USER, response.getData());
        }
        return response;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ServerResponse<String> logout(HttpSession session) {
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createSuccessResponseByMsg("退出成功");
    }

    @RequestMapping(value = "/check/username", method = RequestMethod.GET)
    public ServerResponse<String> checkUsername(String username) {
        if(StringUtils.isBlank(username)){
            return ServerResponse.createIllegalArgsResponse();
        }
        return userService.checkUsername(username);
    }

    @RequestMapping(value = "/check/phone", method = RequestMethod.GET)
    public ServerResponse<String> checkPhone(String phone) {
        if(StringUtils.isBlank(phone)){
            return ServerResponse.createIllegalArgsResponse();
        }
        return userService.checkPhone(phone);
    }

    @RequestMapping(value = "/check/email", method = RequestMethod.GET)
    public ServerResponse<String> checkEmail(String email) {
        if(StringUtils.isBlank(email)){
            return ServerResponse.createIllegalArgsResponse();
        }
        return userService.checkEmail(email);
    }


    @RequestMapping(value = "/forget/get_question", method = RequestMethod.GET)
    public ServerResponse<String> getQuestion(String username) {
        if(StringUtils.isBlank(username)){
            return ServerResponse.createIllegalArgsResponse();
        }
        return userService.getQuestion(username);
    }

    @RequestMapping(value = "/forget/check_answer", method = RequestMethod.POST)
    public ServerResponse<String> checkAnswer(String username, String question, String answer) {
        if(StringUtils.isAnyBlank(username,question,answer)){
            return ServerResponse.createIllegalArgsResponse();
        }
        return userService.checkAnswer(username, question, answer);
    }

    @RequestMapping(value = "/forget/reset_password", method = RequestMethod.POST)
    public ServerResponse<String> forgetResetPassword(String username, String newPassword, String resetToken) {
        if(StringUtils.isAnyBlank(username,newPassword,resetToken)){
            return ServerResponse.createIllegalArgsResponse();
        }
        return userService.resetPassword(username, newPassword, resetToken);
    }

    @RequestMapping(value = "/reset_password", method = RequestMethod.POST)
    public ServerResponse<String> updatePassword(HttpSession session, String oldPassword, String newPassowrd) {
        if(StringUtils.isAnyBlank(oldPassword,newPassowrd)){
            return ServerResponse.createIllegalArgsResponse();
        }
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        return userService.updatePassword(user.getId(), oldPassword, newPassowrd);

    }

    @RequestMapping(value = "/get_user_info", method = RequestMethod.GET)
    public ServerResponse<User> getUserInfo(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        return ServerResponse.createSuccessResponseByMsgAndData("获取成功", user);
    }

    @RequestMapping(value = "/update_user_info", method = RequestMethod.POST)
    public ServerResponse<User> updateUserInfo(User user, HttpSession session) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        user.setId(currentUser.getId());
        user.setUsername(currentUser.getUsername());
        user.setPassword(null);
        user.setAnswer(null);
        user.setQuestion(null);
        user.setRole(null);
        ServerResponse<User> response = userService.updateUserInfo(user);
        if (response.isSuccess()) {
            session.setAttribute(Const.CURRENT_USER, response.getData());
        }
        return response;
    }

}
