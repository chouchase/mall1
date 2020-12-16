package com.chouchase.service.impl;

import com.chouchase.common.Const;
import com.chouchase.common.ServerResponse;
import com.chouchase.common.TokenCache;
import com.chouchase.dao.UserDao;
import com.chouchase.domain.User;
import com.chouchase.service.UserService;
import com.chouchase.util.MD5Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service

public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    public ServerResponse<String> checkUsername(String username) {
        int count = userDao.countUsername(username);
        if (count > 0) {
            return ServerResponse.createFailResponseByMsg("用户名已存在");
        } else {
            return ServerResponse.createSuccessResponseByMsg("校验成功");
        }
    }

    public ServerResponse<String> checkEmail(String email) {
        int count = userDao.countEmail(email);
        if (count > 0) {
            return ServerResponse.createFailResponseByMsg("邮箱已存在");
        } else {
            return ServerResponse.createSuccessResponseByMsg("校验成功");
        }
    }

    public ServerResponse<String> checkPhone(String phone) {
        int count = userDao.countPhone(phone);
        if (count > 0) {
            return ServerResponse.createFailResponseByMsg("电话号码已存在");
        } else {
            return ServerResponse.createSuccessResponseByMsg("校验成功");
        }
    }

    @Override
    public ServerResponse<String> register(User user) {
        if (userDao.countUsername(user.getUsername()) > 0) {
            return ServerResponse.createFailResponseByMsg("用户名已存在");
        }
        if (userDao.countEmail(user.getEmail()) > 0) {
            return ServerResponse.createFailResponseByMsg("邮箱已存在");
        }
        if (userDao.countPhone(user.getPhone()) > 0) {
            return ServerResponse.createFailResponseByMsg("电话号码已存在");
        }

        user.setPassword(MD5Utils.encrypt(user.getPassword()));
        int cnt = userDao.insertUser(user);
        if (cnt > 0) {
            return ServerResponse.createSuccessResponseByMsg("注册成功");
        }
        return ServerResponse.createFailResponseByMsg("注册失败");
    }

    @Override
    public ServerResponse<User> login(String username, String password) {
        if (userDao.countUsername(username) == 0) {
            return ServerResponse.createFailResponseByMsg("用户名不存在");
        }
        User user = userDao.selectUserByUsernameAndPassword(username, MD5Utils.encrypt(password));
        if (user != null) {
            return ServerResponse.createSuccessResponseByMsgAndData("登陆成功", user);
        }
        return ServerResponse.createFailResponseByMsg("密码错误");
    }

    @Override
    public ServerResponse<String> getQuestion(String username) {
        if (userDao.countUsername(username) == 0) {
            return ServerResponse.createFailResponseByMsg("用户不存在");
        }
        String question = userDao.selectQuestionByUsername(username);
        if (StringUtils.isBlank(question)) {
            return ServerResponse.createFailResponseByMsg("用户没有设置问题");
        }
        return ServerResponse.createSuccessResponseByMsgAndData("成功", question);
    }

    @Override
    public ServerResponse<String> checkAnswer(String username, String question, String answer) {
        int cnt = userDao.countUsername(username);
        if (cnt == 0) {
            return ServerResponse.createFailResponseByMsg("用户名不存在");
        }
        cnt = userDao.countUserQuestionAnswer(username, question, answer);
        if (cnt == 0) {
            return ServerResponse.createFailResponseByMsg("问题或答案错误");
        }
        String uuid = UUID.randomUUID().toString();
        TokenCache.put(username, uuid);
        return ServerResponse.createSuccessResponseByMsgAndData("校验成功", uuid);
    }

    @Override
    public ServerResponse<String> resetPassword(String username, String newPassword, String resetToken) {
        String token = TokenCache.get(username);
        if (!StringUtils.equals(token, resetToken)) {
            return ServerResponse.createFailResponseByMsg("token无效");
        }
        int cnt = userDao.updatePasswordByUsername(username, MD5Utils.encrypt(newPassword));
        if (cnt > 0) {
            return ServerResponse.createSuccessResponseByMsg("密码重置成功");
        }
        return ServerResponse.createFailResponseByMsg("密码重置失败");
    }

    @Override
    public ServerResponse<String> updatePassword(Integer userId, String oldPassword, String newPassword) {
        int cnt = userDao.countPasswordByUserId(userId, MD5Utils.encrypt(oldPassword));
        if (cnt == 0) {
            return ServerResponse.createFailResponseByMsg("原密码错误");
        }
        cnt = userDao.updatePasswordByUserId(userId, MD5Utils.encrypt(newPassword));
        if (cnt == 0) {
            return ServerResponse.createFailResponseByMsg("更新密码失败");
        }
        return ServerResponse.createSuccessResponseByMsg("更新密码成功");
    }

    @Override
    public ServerResponse<User> updateUserInfo(User user) {
        if (StringUtils.isNotBlank(user.getEmail())) {
            int cnt = userDao.countEmailByOtherId(user.getEmail(), user.getId());
            if (cnt > 0) {
                return ServerResponse.createFailResponseByMsg("邮箱已存在");
            }
        }
        if (StringUtils.isNotBlank(user.getPhone())) {
            int cnt = userDao.countPhoneByOtherId(user.getPhone(), user.getId());
            if (cnt > 0) {
                return ServerResponse.createFailResponseByMsg("电话号码已存在");
            }
        }

        int cnt = userDao.updateUserBySelective(user);
        if (cnt > 0) {
            return ServerResponse.createSuccessResponseByMsgAndData("更新成功", userDao.selectUserByPrimaryKey(user.getId()));
        }
        return ServerResponse.createSuccessResponseByMsg("更新失败");
    }
}
