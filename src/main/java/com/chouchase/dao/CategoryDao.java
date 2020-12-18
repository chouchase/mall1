package com.chouchase.dao;

import com.chouchase.domain.Category;
import org.apache.ibatis.annotations.Param;

public interface CategoryDao {
    public int countId(Integer id);
    public int insertCategory(Category category);
    public int countCategoryName(String name);
    public int updateCategorySelectiveById(Category category);

}
