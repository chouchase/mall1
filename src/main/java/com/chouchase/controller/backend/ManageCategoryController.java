package com.chouchase.controller.backend;

import com.chouchase.common.ServerResponse;
import com.chouchase.service.CategoryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/manage/category")
@ResponseBody
public class ManageCategoryController {
    @Autowired
    private CategoryService categoryService;
    @RequestMapping(value = "/add_category",method = RequestMethod.POST)
    public ServerResponse<String> addCategory(@RequestParam(value = "parentId",defaultValue = "0") Integer parentId, String categoryName){
        System.out.println(parentId);
        System.out.println(categoryName);
        if(StringUtils.isBlank(categoryName)){
            return ServerResponse.createIllegalArgsResponse();
        }
        return categoryService.addCategory(parentId,categoryName);
    }
    @RequestMapping(value = "/update_category_name",method = RequestMethod.POST)
    public ServerResponse<String> updateCategoryName(Integer id, String name){
        if(id == null || StringUtils.isBlank(name)){
            return ServerResponse.createIllegalArgsResponse();
        }
        return categoryService.updateCategoryName(id,name);
    }
}
