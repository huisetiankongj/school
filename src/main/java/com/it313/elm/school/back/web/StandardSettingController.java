/**
 * Copyright &copy; 2010-2018 <a href="http://www.it313.cn">big-generator</a> All rights reserved.
 */
package com.it313.elm.school.back.web;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.it313.big.common.persistence.paginate.Paginate;
import com.it313.big.common.web.BaseController;
import com.it313.big.common.utils.StringUtils;
import com.it313.elm.school.back.entity.StandardSetting;
import com.it313.elm.school.back.service.StandardSettingService;

/**
 * 任务统计Controller
 * @author admin
 * @version 2019-09-27
 */
@Controller
@RequestMapping(value = "${adminPath}/back/standardSetting")
public class StandardSettingController extends BaseController {

	@Autowired
	private StandardSettingService standardSettingService;
	
	/**
	 * 跳转页
	 * @param standardSetting
	 * @param model
	 * @return
	 */
	@RequiresPermissions("back:standardSetting:view")
	@RequestMapping(value = {"index", ""})
	public String index(StandardSetting standardSetting, Model model) {
		return "school/back/standardSettingList";
	}
	
	/**
	 * 遍历列表
	 * @param standardSetting
	 * @return
	 */
	@RequiresPermissions("back:standardSetting:view")
	@RequestMapping(value = {"list", ""})
	@ResponseBody
	public Object list(@RequestBody StandardSetting standardSetting) {
	    this.setPageParams(standardSetting.getPaginate()); // 核心分页代码 
		List<StandardSetting> list = standardSettingService.findList(standardSetting);
		Paginate<StandardSetting> page = new Paginate<StandardSetting>(list, standardSetting.getPaginate().getMenuId());
		return page;
	}
    
    /**
     * 修改页面
     * @param standardSetting
     * @param model
     * @return
     */
	@RequiresPermissions("back:standardSetting:view")
	@RequestMapping(value = "form")
	public String form(StandardSetting standardSetting, Model model) {
	   if(standardSetting!=null && StringUtils.isNotBlank(standardSetting.getId())){
			standardSetting = standardSettingService.get(standardSetting);
			model.addAttribute("standardSetting", standardSetting);
		}
		return "school/back/standardSettingForm";
	}
	
    /**
     * 保存数据
     * @param standardSetting
     * @param model
     * @param redirectAttributes
     * @return
     */
	@RequiresPermissions("back:standardSetting:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Object save(@RequestBody StandardSetting standardSetting, Model model, RedirectAttributes redirectAttributes) {
	try {
			standardSettingService.save(standardSetting);
		} catch (Exception e) {
			return setErrorMsg("操作失败！");
		}
		return Boolean.TRUE;
	}
	
	/**
	 * 删除页面
	 * @param standardSetting
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("back:standardSetting:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Object delete(@RequestBody List<StandardSetting> standardSetting, RedirectAttributes redirectAttributes) {
	    standardSettingService.delete(standardSetting);
		return Boolean.TRUE;
	}
}