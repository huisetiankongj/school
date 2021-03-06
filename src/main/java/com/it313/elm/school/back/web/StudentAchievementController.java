/**
 * Copyright &copy; 2010-2018 <a href="http://www.it313.cn">big-generator</a> All rights reserved.
 */
package com.it313.elm.school.back.web;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.it313.big.common.utils.excel.ExportExcel;
import com.it313.big.common.utils.excel.ExportExcel2;
import com.it313.big.modules.sys.entity.Dict;
import com.it313.big.modules.sys.entity.Org;
import com.it313.big.modules.sys.utils.OrgUtils;
import com.it313.elm.school.back.entity.ExamTask;
import com.it313.elm.school.back.entity.StandardSetting;
import com.it313.elm.school.back.service.ExamTaskService;
import com.it313.elm.school.back.utils.StandardSettingUtils;
import com.it313.elm.school.common.OsGlobal;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.it313.big.common.persistence.paginate.Paginate;
import com.it313.big.common.web.BaseController;
import com.it313.big.common.utils.StringUtils;
import com.it313.elm.school.back.entity.StudentAchievement;
import com.it313.elm.school.back.service.StudentAchievementService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.it313.elm.school.back.utils.StandardSettingUtils.getStandardSettings;

/**
 * 学生成绩Controller
 * @author admin
 * @version 2019-08-27
 */
@Controller
@RequestMapping(value = "${adminPath}/back/studentAchievement")
public class StudentAchievementController extends BaseController {

    @Autowired
    private StudentAchievementService studentAchievementService;
    @Autowired
    private ExamTaskService examTaskService;

    /**
     * 跳转页
     * @param studentAchievement
     * @param model
     * @return
     */
    @RequiresPermissions("back:studentAchievement:view")
    @RequestMapping(value = {"studentAchievementList", ""})
    public String indexList(StudentAchievement studentAchievement, Model model) {
        ExamTask examTask = new ExamTask();
        List<ExamTask> examTaskList = examTaskService.findList(examTask);
        model.addAttribute("examTaskList", examTaskList);
        return "school/back/studentAchievementList";
    }

    /**
     * 遍历列表
     * @param studentAchievement
     * @return
     */
    @RequiresPermissions("back:studentAchievement:view")
    @RequestMapping(value = {"list", ""})
    @ResponseBody
    public Object list(@RequestBody StudentAchievement studentAchievement) {
        this.setPageParams(studentAchievement.getPaginate()); // 核心分页代码
        List<StudentAchievement> list = studentAchievementService.findList(studentAchievement);
        Paginate<StudentAchievement> page = new Paginate<StudentAchievement>(list, studentAchievement.getPaginate().getMenuId());
        return page;
    }

    /**
     * 修改页面
     * @param studentAchievement
     * @param model
     * @return
     */
    @RequiresPermissions("back:studentAchievement:view")
    @RequestMapping(value = "form")
    public String form(StudentAchievement studentAchievement, Model model) {
        if(studentAchievement!=null && StringUtils.isNotBlank(studentAchievement.getId())){
            studentAchievement = studentAchievementService.get(studentAchievement);
            model.addAttribute("studentAchievement", studentAchievement);
        }
        return "school/back/studentAchievementForm";
    }

    /**
     * 保存数据
     * @param studentAchievement
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("back:studentAchievement:edit")
    @RequestMapping(value = "save")
    @ResponseBody
    public Object save(@RequestBody StudentAchievement studentAchievement, Model model, RedirectAttributes redirectAttributes) {
        try {
            studentAchievementService.save(studentAchievement);
        } catch (Exception e) {
            return setErrorMsg("操作失败！");
        }
        return Boolean.TRUE;
    }

    /**
     * 删除页面
     * @param studentAchievement
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("back:studentAchievement:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public Object delete(@RequestBody List<StudentAchievement> studentAchievement, RedirectAttributes redirectAttributes) {
        studentAchievementService.delete(studentAchievement);
        return Boolean.TRUE;
    }

    @RequiresPermissions("back:studentAchievement:import")
    @RequestMapping(value = {"importPage"})
    public String importPage(StudentAchievement studentAch, Model model) {
        ExamTask examTask = new ExamTask();
        List<ExamTask> examTaskList = examTaskService.findList(examTask);
        model.addAttribute("studentAch", studentAch);
        model.addAttribute("examTaskList", examTaskList);
        return "school/back/studentAchievementImport";
    }

    /**
     * 批量导入
     * 注：批量导入的时候会把附件路径保存起来，后面修改不会对图片路径进行修改
     * @param file
     * @param taskId
     * @param orgId
     * @return
     */
    @RequiresPermissions("back:studentAchievement:import")
    @RequestMapping(value = "batchSaveByExcel")
    @ResponseBody
    public Object batchSaveByExcel(@RequestParam("ajaxFile") MultipartFile file, @RequestParam("taskId")String taskId,
                                   @RequestParam("subjectId")String subjectId,@RequestParam("orgId")String orgId,
                                   @RequestParam("taskName")String taskName,
                                   @RequestParam("subjectName")String subjectName,@RequestParam("orgName")String orgName) throws ParseException {

        if(OsGlobal.sdf.parse(OsGlobal.effectiveDate).getTime()>=System.currentTimeMillis()){
            return setErrorMsg("有效期已过期，请联系管理员");
        }
        try {
            StudentAchievement studentAch = new StudentAchievement();
            studentAch.setOrgId(orgId);
            studentAch.setOrgName(orgName);
            studentAch.setSubjectId(subjectId);
            studentAch.setSubjectName(subjectName);
            studentAch.setTaskId(taskId);
            studentAch.setTaskName(taskName);
            String msg = studentAchievementService.batchSaveByExcel(file,studentAch);
            if(!"ok".equals(msg)){
                Map<String,Object> errorMap = new HashMap<String,Object>();
                errorMap.put("msg", msg);
                return errorMap;
            }
        } catch (Exception e) {
            return setErrorMsg("导入学生成绩失败！失败信息："+e.getMessage());
        }
        return Boolean.TRUE;
    }

    @RequestMapping("downExcel")
    public String downExcel(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes){
        try {
            String orgId = request.getParameter("orgId");
            String etSubjectId = request.getParameter("etSubjectId");
            String taskId = request.getParameter("taskId");
            String titleName ="初二1班语文成绩导入模板";
            String fileName = titleName+".xlsx";
            StudentAchievement studentAch = new StudentAchievement();
            studentAch.setOrgId(orgId);
            studentAch.setSubjectId(etSubjectId);
            studentAch.setTaskId(taskId);
            List<StudentAchievement> list = studentAchievementService.importModuleData(studentAch);
            new ExportExcel(titleName, StudentAchievement.class).setDataList(list).write(request,response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导出失败！失败信息："+e.getMessage());
        }

        return "school/back/studentAchievementList";
    }

    /**
     * 跳转页
     * @param model
     * @return
     */
    @RequiresPermissions("back:examTaskStatistics:view")
    @RequestMapping(value = {"scoreDetail", ""})
    public String scoreDetail( Model model) {
        return "school/back/scoreDetail";
    }

    /**
     * 遍历列表
     * @param studentAchievement
     * @return
     */
    @RequiresPermissions("back:studentAchievement:view")
    @RequestMapping(value = {"scoreDetailList", ""})
    @ResponseBody
    public Object scoreDetailList(@RequestBody StudentAchievement studentAchievement) {
        Org parentOrg = OrgUtils.getSchoolOrg(studentAchievement.getOrgId());
        if(parentOrg==null||StringUtils.isBlank(parentOrg.getId())){
            return setErrorMsg("找不到该学校");
        }
        StandardSetting setting = StandardSettingUtils.getStandardSetting(parentOrg.getId(),studentAchievement.getSubjectId());
        //类型（bad:不及格,pass:及格,excellent:优秀,all:所有）
        if("bad".equals(studentAchievement.getType())){
            studentAchievement.setBeginScore(0);
            studentAchievement.setEndScore(setting.getPassScore()-1);
        }else if("pass".equals(studentAchievement.getType())){
            studentAchievement.setBeginScore(setting.getPassScore());
        }else if("excellent".equals(studentAchievement.getType())){
            studentAchievement.setBeginScore(setting.getExcellentScore());
        }

        this.setPageParams(studentAchievement.getPaginate()); // 核心分页代码
        List<StudentAchievement> list = studentAchievementService.findList(studentAchievement);
        Paginate<StudentAchievement> page = new Paginate<StudentAchievement>(list, studentAchievement.getPaginate().getMenuId());
        return page;
    }

    @RequiresPermissions("back:studentAchievement:view")
    @RequestMapping(value = {"allSubjectAchievement", ""})
    @ResponseBody
    public Object allSubjectAchievement(@RequestBody StudentAchievement studentAchievement) {

        Integer count = studentAchievementService.allSubjectAchievementCount(studentAchievement);
        if(count==null){
            count = 0;
        }
        List<Map<String,Object>> list = new ArrayList<>();
        if(count>0){
            list = studentAchievementService.allSubjectAchievement(studentAchievement);
        }
        Paginate paginate = studentAchievement.getPaginate();
        Paginate<Map<String,Object>> page = new Paginate(list, paginate.getCurrentPage(), paginate.getRowsOfPage(), count, paginate.getMenuId());
        return page;
    }

    @RequiresPermissions("back:studentAchievement:view")
    @RequestMapping(value = {"exportAllSubject", ""})
    public String exportAllSubject(StudentAchievement studentAchievement,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes){
        try {

            ExamTask task = new ExamTask();
            task.setId(studentAchievement.getTaskId());
            List<Dict> subjectDicts = examTaskService.getTaskSubject(task);

            String[] heads= new String[subjectDicts.size()+3];
            String[] fields = new String[subjectDicts.size()+3];
            heads[0]="班级";
            heads[1]="学号";
            heads[2]="姓名";
            fields[0]="stuOrgName";
            fields[1]="stuNo";
            fields[2]="stuName";
            for(int k=0,kLen=subjectDicts.size();k<kLen;k++) {
                Dict dic = subjectDicts.get(k);
                heads[k+3] = dic.getLabel();
                fields[k+3] = "subject"+dic.getValue();
            }

            List<Map<String,Object>> list =studentAchievementService.allSubjectAchievement(studentAchievement);
            String fileName =studentAchievement.getTaskName()+".xlsx";
            new ExportExcel("", heads,fields).setDataList(list).write(request,response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导出失败！失败信息："+e.getMessage());
        }

        return "school/back/allSubjectAchievement";
    }

}