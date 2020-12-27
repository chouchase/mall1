package com.chouchase.controller.portal;

import com.chouchase.common.Const;
import com.chouchase.common.ServerResponse;
import com.chouchase.domain.User;
import com.chouchase.service.CartService;
import com.chouchase.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/cart")
@ResponseBody
public class CartController {
    @Autowired
    private CartService cartService;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ServerResponse<CartVo> add(HttpSession session, Integer productId, Integer count) {
        System.out.println(productId);
        System.out.println(count);
        //参数校验
        if (productId == null || count == null) {
            return ServerResponse.createFailResponseByMsg("参数错误");
        }
        //获取登录用户的id
        Integer userId = ((User) session.getAttribute(Const.CURRENT_USER)).getId();
        return cartService.add(userId, productId, count);
    }
}
