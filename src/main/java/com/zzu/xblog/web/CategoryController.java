package com.zzu.xblog.web;

import com.zzu.xblog.model.Category;
import com.zzu.xblog.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 分类相关controller
 */
@Controller
@RequestMapping("category")
public class CategoryController {
	@Resource
	private CategoryService categoryService;

	/* 获取所有分类 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public List<Category> list() {
		return categoryService.listCategory();
	}

	/* 获取所有分类 */
	@RequestMapping(value = "/{id}/child", method = RequestMethod.GET)
	@ResponseBody
	public List<Category> listChildren(@PathVariable("id") Integer id) {
		return categoryService.getChildrenCategory(id);
	}

	/* 根据id查询分类 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Category detail(@PathVariable("id") Integer id) {
		return categoryService.detail(id);
	}
}
