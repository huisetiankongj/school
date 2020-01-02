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
import com.it313.elm.school.back.entity.ExamTaskDetail;
import com.it313.elm.school.back.service.ExamTaskDetailService;

/**
 * 任务明细Controller
 * @author admin
 * @version 2019-09-17
 */
@Controller
@RequestMapping(value = "${adminPath}/back/examTaskDetail")
public class ExamTaskDetailController extends BaseController {

	@Autowired
	private ExamTaskDetailService examTaskDetailService;
	
	/**
	 * 跳转页
	 * @param examTaskDetail
	 * @param model
	 * @return
	 */
	@RequiresPermissions("back:examTaskDetail:view")
	@RequestMapping(value = {"examTaskDetailList", ""})
	public String indexList(ExamTaskDetail examTaskDetail, Model model) {
		return "elm/back/examTaskDetailList";
	}
	
	/**
	 * 遍历列表
	 * @param examTaskDetail
	 * @return
	 */
	@RequiresPermissions("back:examTaskDetail:view")
	@RequestMapping(value = {"list", ""})
	@ResponseBody
	public Object list(@RequestBody ExamTaskDetail examTaskDetail) {
	    this.setPageParams(examTaskDetail.getPaginate()); // 核心分页代码 
		List<ExamTaskDetail> list = examTaskDetailService.findList(examTaskDetail);
		Paginate<ExamTaskDetail> page = new Paginate<ExamTaskDetail>(list, examTaskDetail.getPaginate().getMenuId());
		return page;
	}
    
    /**
     * 修改页面
     * @param examTaskDetail
     * @param model
     * @return
     */
	@RequiresPermissions("back:examTaskDetail:view")
	@RequestMapping(value = "form")
	public String form(ExamTaskDetail examTaskDetail, Model model) {
	   if(examTaskDetail!=null && StringUtils.isNotBlank(examTaskDetail.getId())){
			examTaskDetail = examTaskDetailService.get(examTaskDetail);
			model.addAttribute("examTaskDetail", examTaskDetail);
		}
		return "elm/back/examTaskDetailForm";
	}
	
    /**
     * 保存数据
     * @param examTaskDetail
     * @param model
     * @param redirectAttributes
     * @return
     */
	@RequiresPermissions("back:examTaskDetail:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Object save(@RequestBody ExamTaskDetail examTaskDetail, Model model, RedirectAttributes redirectAttributes) {
	try {
			examTaskDetailService.save(examTaskDetail);
		} catch (Exception e) {
			return setErrorMsg("操作失败！");
		}
		return Boolean.TRUE;
	}
	
	/**
	 * 删除页面
	 * @param examTaskDetail
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("back:examTaskDetail:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Object delete(@RequestBody List<ExamTaskDetail> examTaskDetail, RedirectAttributes redirectAttributes) {
	    examTaskDetailService.delete(examTaskDetail);
		return Boolean.TRUE;
	}
}