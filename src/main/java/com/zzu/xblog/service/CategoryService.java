package com.zzu.xblog.service;

import com.zzu.xblog.dao.CategoryDao;
import com.zzu.xblog.model.Category;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 分类相关业务逻辑
 */
@Service
public class CategoryService {
    @Resource
    private CategoryDao categoryDao;

    /**
     * 获取所有分类
     *
     * @return
     */
    public List<Category> listCategory() {
        List<Category> list = categoryDao.listCategory();
        Map<Integer, Category> map = new HashMap<Integer, Category>();

        for (Category category : list) {
            if (category.getParent() != null &&
                    !map.containsKey(category.getParent().getCateId())) {
                map.put(category.getParent().getCateId(), category.getParent());
            }
        }

        for (Category category : list) {
            map.get(category.getParent().getCateId()).getChildren().add(category);
            category.setParent(null);
        }

        list = new ArrayList<Category>();
        for(Category category : map.values()) {
            list.add(category);
        }

        return list;
    }

    /**
     * 获取制定分类下的所有子分类
     *
     * @param id
     * @return
     */
    public List<Category> getChildrenCategory(int id) {
        if (id < 0) {
            return null;
        }
        return categoryDao.getChildrenCategory(id);
    }

    /**
     * 根据id查询分类
     *
     * @param id
     * @return
     */
    public Category detail(int id) {
        if (id < 0) {
            return null;
        }
        return categoryDao.detail(id);
    }
}
