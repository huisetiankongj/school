/**
 * Copyright &copy; 2012-2018 <a href="http://www.it313.cn">big-generator</a> All rights reserved.
 */
package com.it313.elm.school.back.service;

import java.util.List;

import com.it313.big.modules.sys.entity.Org;
import com.it313.big.modules.sys.utils.OrgUtils;
import com.it313.elm.school.back.dto.ExamTaskStatisticsDto;
import com.it313.elm.school.back.entity.ExamTask;
import com.it313.elm.school.back.entity.StandardSetting;
import com.it313.elm.school.back.utils.StandardSettingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.it313.big.common.persistence.Page;
import com.it313.big.common.utils.StringUtils;
import com.it313.big.common.service.CrudService;
import com.it313.elm.school.back.entity.ExamTaskStatistics;
import com.it313.elm.school.back.dao.ExamTaskStatisticsDao;

/**
 * 任务统计Service
 * @author admin
 * @version 2019-09-17
 */
@Service
@Transactional(readOnly = true)
public class ExamTaskStatisticsService extends CrudService<ExamTaskStatisticsDao, ExamTaskStatistics> {

	@Autowired
	private ExamTaskService examTaskService;
     /**
      * 查询单个
      * @param id
      */
	public ExamTaskStatistics get(String id) {
		return super.get(id);
	}
	
	/**
	 * 遍历列表
	 * @param examTaskStatistics
	 */
	public List<ExamTaskStatistics> findList(ExamTaskStatistics examTaskStatistics) {
		if(StringUtils.isNotBlank(examTaskStatistics.getSubjectId())){
			String subjectId = examTaskStatistics.getSubjectId();
			subjectId = subjectId.replace(",", "','");
			examTaskStatistics.setSubjectId(subjectId);
		}
		return super.findList(examTaskStatistics);
	}
	
	public Page<ExamTaskStatistics> findPage(Page<ExamTaskStatistics> page, ExamTaskStatistics examTaskStatistics) {
		return super.findPage(page, examTaskStatistics);
	}
	
	/**
	 * 保存数据
	 * @param examTaskStatistics
	 */
	@Transactional(readOnly = false)
	public void save(ExamTaskStatistics examTaskStatistics) {
		if(examTaskStatistics != null && StringUtils.isNotBlank(examTaskStatistics.getId())){
			dao.update(examTaskStatistics);
		} else {
			examTaskStatistics.preInsert();
			dao.insert(examTaskStatistics);
		}
	}

	/**
	 * 批量删除数据
	 * @param examTaskStatistics
	 */
	@Transactional(readOnly = false)
	public void delete( List<ExamTaskStatistics> examTaskStatistics) {
		dao.batchDelete(examTaskStatistics);
	}

    public List<ExamTaskStatisticsDto> findHvList(ExamTaskStatistics examTaskStatistics) {
		if(StringUtils.isNotBlank(examTaskStatistics.getSubjectId())){
			String subjectId = examTaskStatistics.getSubjectId();
			subjectId = subjectId.replace(",", "','");
			examTaskStatistics.setSubjectId(subjectId);
		}
		return dao.findHvList(examTaskStatistics);
    }

	@Transactional(readOnly = false)
	public void genReport(ExamTask task) {
		dao.deleteByTaskId(task.getId());
		List<StandardSetting> settings = StandardSettingUtils.getStandardSettings(task.getSchoolOrgId());
		ExamTaskStatistics statistics = new ExamTaskStatistics();
		statistics.setTaskId(task.getId());
		for(int i=0,len=settings.size();i<len;i++){
			StandardSetting setting = settings.get(i);
			statistics.setPasscore(Double.parseDouble(setting.getPassScore().toString()));
			statistics.setExcellentScore(Double.parseDouble(setting.getExcellentScore().toString()));
			statistics.setSubjectId(setting.getSubjectId());
			statistics.setSchoolOrgId(setting.getOrgId());
			dao.saveByTaskId(statistics);
		}

	}
}