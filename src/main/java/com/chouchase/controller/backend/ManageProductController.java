package com.chouchase.controller.backend;

import com.chouchase.common.ServerResponse;
import com.chouchase.domain.Product;
import com.chouchase.service.ProductService;
import com.chouchase.vo.ProductDetail;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("/manage/product")
public class ManageProductController {
    @Autowired
    private ProductService productService;
    @RequestMapping(value = "/save",method = RequestMethod.GET)
    public ServerResponse<String> save(Product product){
        //参数校验
        if(StringUtils.isAnyBlank(product.getName()) || product.getCategoryId() == null){
            System.out.println("我");
            return ServerResponse.createFailResponseByMsg("参数错误");
        }
        return productService.updateOrAddProduct(product);
    }
    @RequestMapping(value = "/set_sale_status",method = RequestMethod.GET)
    public ServerResponse<String> setSaleStatus(Integer productId,Integer status){
        //参数校验
        if(productId == null || status == null){
            return ServerResponse.createFailResponseByMsg("参数错误");
        }
        return productService.setSaleStatus(productId,status);
    }
    @RequestMapping(value = "/detail",method = RequestMethod.GET)
    public ServerResponse<ProductDetail> getDetail(Integer productId){
        //参数校验
        if(productId == null){
            return ServerResponse.createFailResponseByMsg("参数错误");
        }
        return productService.manageProductDetail(productId);
    }
}
