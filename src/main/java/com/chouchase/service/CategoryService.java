package com.chouchase.service;

import com.chouchase.common.ServerResponse;

public interface CategoryService {
    public ServerResponse<String> addCategory(Integer parentId,String categoryName);
    public ServerResponse<String> updateCategoryName(Integer id,String categoryName);
}
