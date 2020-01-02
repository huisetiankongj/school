/**
 * Copyright &copy; 2010-2018 <a href="http://www.it313.cn">big-generator</a> All rights reserved.
 */
package com.it313.elm.school.back.web;

import java.text.ParseException;
import java.util.List;

import com.it313.elm.school.back.dto.ExamTaskStatisticsDto;
import com.it313.elm.school.back.entity.ExamTask;
import com.it313.elm.school.back.service.ExamTaskService;
import com.it313.elm.school.common.OsGlobal;
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
import com.it313.elm.school.back.entity.ExamTaskStatistics;
import com.it313.elm.school.back.service.ExamTaskStatisticsService;

/**
 * 任务统计Controller
 * @author admin
 * @version 2019-09-17
 */
@Controller
@RequestMapping(value = "${adminPath}/back/examTaskStatistics")
public class ExamTaskStatisticsController extends BaseController {

	@Autowired
	private ExamTaskStatisticsService examTaskStatisticsService;
	@Autowired
	private ExamTaskService examTaskService;
	
	/**
	 * 跳转页
	 * @param examTaskStatistics
	 * @param model
	 * @return
	 */
	@RequiresPermissions("back:examTaskStatistics:view")
	@RequestMapping(value = {"examTaskStatisticsList", ""})
	public String indexList(ExamTaskStatistics examTaskStatistics, Model model) {
		ExamTask examTask = new ExamTask();
		List<ExamTask> examTaskList = examTaskService.findList(examTask);
		model.addAttribute("examTaskList", examTaskList);
		return "school/back/examTaskStatisticsList";
	}

	
	/**
	 * 遍历列表examTaskStatistics
	 * @param examTaskStatistics
	 * @return
	 */
	@RequiresPermissions("back:examTaskStatistics:view")
	@RequestMapping(value = {"list", ""})
	@ResponseBody
	public Object list(@RequestBody ExamTaskStatistics examTaskStatistics) throws ParseException {
		if(OsGlobal.sdf.parse(OsGlobal.effectiveDate).getTime()>=System.currentTimeMillis()){
			return setErrorMsg("有效期已过期，请联系管理员");
		}
	    this.setPageParams(examTaskStatistics.getPaginate()); // 核心分页代码 
		List<ExamTaskStatistics> list = examTaskStatisticsService.findList(examTaskStatistics);
		Paginate<ExamTaskStatistics> page = new Paginate<ExamTaskStatistics>(list, examTaskStatistics.getPaginate().getMenuId());
		return page;
	}
    
    /**
     * 修改页面
     * @param examTaskStatistics
     * @param model
     * @return
     */
	@RequiresPermissions("back:examTaskStatistics:view")
	@RequestMapping(value = "form")
	public String form(ExamTaskStatistics examTaskStatistics, Model model) {
	   if(examTaskStatistics!=null && StringUtils.isNotBlank(examTaskStatistics.getId())){
			examTaskStatistics = examTaskStatisticsService.get(examTaskStatistics);
			model.addAttribute("examTaskStatistics", examTaskStatistics);
	   }
	   return "school/back/examTaskStatisticsForm";
	}


    /**
     * 保存数据
     * @param examTaskStatistics
     * @param model
     * @param redirectAttributes
     * @return
     */
	@RequiresPermissions("back:examTaskStatistics:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Object save(@RequestBody ExamTaskStatistics examTaskStatistics, Model model, RedirectAttributes redirectAttributes) {
	try {
			examTaskStatisticsService.save(examTaskStatistics);
		} catch (Exception e) {
			return setErrorMsg("操作失败！");
		}
		return Boolean.TRUE;
	}
	
	/**
	 * 删除页面
	 * @param examTaskStatistics
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("back:examTaskStatistics:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Object delete(@RequestBody List<ExamTaskStatistics> examTaskStatistics, RedirectAttributes redirectAttributes) {
	    examTaskStatisticsService.delete(examTaskStatistics);
		return Boolean.TRUE;
	}


	/**
	 * 跳转页
	 * @param examTaskStatistics
	 * @param model
	 * @return
	 */
	@RequiresPermissions("back:examTaskStatistics:view")
	@RequestMapping(value = {"allSubjectAchievement", ""})
	public String allSubjectAchievement(ExamTaskStatistics examTaskStatistics, Model model) {
		ExamTask examTask = new ExamTask();
		List<ExamTask> examTaskList = examTaskService.findList(examTask);
		model.addAttribute("examTaskList", examTaskList);
		return "school/back/allSubjectAchievement";
	}


	/**
	 * 遍历列表
	 * @param examTaskStatistics
	 * @return
	 */
	@RequiresPermissions("back:examTaskStatistics:view")
	@RequestMapping(value = {"hvList", ""})
	@ResponseBody
	public Object hvList(@RequestBody ExamTaskStatistics examTaskStatistics) throws ParseException {
		if(OsGlobal.sdf.parse(OsGlobal.effectiveDate).getTime()>=System.currentTimeMillis()){
			return setErrorMsg("有效期已过期，请联系管理员");
		}
		this.setPageParams(examTaskStatistics.getPaginate()); // 核心分页代码
		List<ExamTaskStatisticsDto> list = examTaskStatisticsService.findHvList(examTaskStatistics);
		Paginate<ExamTaskStatisticsDto> page = new Paginate<ExamTaskStatisticsDto>(list, examTaskStatistics.getPaginate().getMenuId());
		return page;
	}

}