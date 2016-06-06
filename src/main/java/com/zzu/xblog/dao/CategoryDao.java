package com.zzu.xblog.dao;

import com.zzu.xblog.model.Category;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * 分类dao
 */
@Component
public interface CategoryDao {
	/**
	 * 获取所有分类
	 * @return
	 */
	List<Category> listCategory();

	/**
	 * 获取制定分类下的所有子分类
	 * @param id
	 * @return
	 */
	List<Category> getChildrenCategory(int id);

	/**
	 * 根据id查询分类
	 * @param id
	 * @return
	 */
	Category detail(int id);
}
