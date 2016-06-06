package com.zzu.xblog.service;

import com.zzu.xblog.dao.CategoryDao;
import com.zzu.xblog.model.Category;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
		return categoryDao.listCategory();
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
