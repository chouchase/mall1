package com.chouchase.interceptor;

import com.chouchase.common.Const;
import com.chouchase.common.ServerResponse;
import com.chouchase.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class ManagerAuthorizationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = (User) request.getSession().getAttribute(Const.CURRENT_USER);
        if(user.getRole() != Const.Role.ADMIN){
            response.setContentType("application/json;charset=UTF-8");
            ServerResponse<String> resp = ServerResponse.createFailResponseByMsg("权限不足");
            response.getWriter().write(new ObjectMapper().writeValueAsString(resp));
            return false;
        }
        return true;
    }
}
