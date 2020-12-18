package com.chouchase.service.impl;

import com.chouchase.common.ServerResponse;
import com.chouchase.dao.CategoryDao;
import com.chouchase.domain.Category;
import com.chouchase.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryDao categoryDao;
    @Override
    public ServerResponse<String> addCategory(Integer parentId, String categoryName) {
        int cnt = 0;
        if(parentId != 0){
            cnt = categoryDao.countId(parentId);
            if(cnt == 0){
                return ServerResponse.createIllegalArgsResponse();
            }
        }
        cnt = categoryDao.countCategoryName(categoryName);
        if(cnt != 0){
            return ServerResponse.createFailResponseByMsg("分类名已存在");
        }
        Category category = new Category();
        category.setParentId(parentId);
        category.setStatus(true);
        category.setName(categoryName);

        cnt = categoryDao.insertCategory(category);
        if(cnt > 0){
            return ServerResponse.createSuccessResponseByMsg("添加成功");
        }
        return ServerResponse.createFailResponseByMsg("添加失败");
    }

    @Override
    public ServerResponse<String> updateCategoryName(Integer id, String categoryName) {
        Category category = new Category();
        category.setId(id);
        category.setName(categoryName);
        category.setStatus(true);
        int cnt = categoryDao.updateCategorySelectiveById(category);
        if(cnt > 0){
            return ServerResponse.createSuccessResponseByMsg("更新成功");
        }
        return ServerResponse.createFailResponseByMsg("updature");

    }
}
