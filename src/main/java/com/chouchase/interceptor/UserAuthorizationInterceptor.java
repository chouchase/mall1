package com.chouchase.interceptor;

import com.chouchase.common.Const;
import com.chouchase.common.ServerResponse;
import com.chouchase.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserAuthorizationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getSession(false) == null || request.getSession().getAttribute(Const.CURRENT_USER) == null){
            response.setContentType("application/json;charset=utf-8");
            ObjectMapper objectMapper = new ObjectMapper();
            String err = objectMapper.writeValueAsString(ServerResponse.createNeedLoginResponse());
            response.getWriter().write(err);
            return false;
        }else if(((User)request.getSession().getAttribute(Const.CURRENT_USER)).getRole() != Const.Role.CUSTOMER){
            response.setContentType("application/json;charset=utf-8");
            ObjectMapper objectMapper = new ObjectMapper();
            String err = objectMapper.writeValueAsString(ServerResponse.createInsufficientAuthorityResponse());
            response.getWriter().write(err);
        }
        return true;
    }
}
