/**
 * Copyright &copy; 2012-2018 <a href="http://www.it313.cn">big-generator</a> All rights reserved.
 */
package com.it313.elm.school.back.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.it313.big.common.persistence.Page;
import com.it313.big.common.utils.StringUtils;
import com.it313.big.common.service.CrudService;
import com.it313.elm.school.back.entity.ExamTaskDetail;
import com.it313.elm.school.back.dao.ExamTaskDetailDao;

/**
 * 任务明细Service
 * @author admin
 * @version 2019-09-17
 */
@Service
@Transactional(readOnly = true)
public class ExamTaskDetailService extends CrudService<ExamTaskDetailDao, ExamTaskDetail> {

     /**
      * 查询单个
      * @param id
      */
	public ExamTaskDetail get(String id) {
		return super.get(id);
	}
	
	/**
	 * 遍历列表
	 * @param examTaskDetail
	 */
	public List<ExamTaskDetail> findList(ExamTaskDetail examTaskDetail) {
		return super.findList(examTaskDetail);
	}
	
	public Page<ExamTaskDetail> findPage(Page<ExamTaskDetail> page, ExamTaskDetail examTaskDetail) {
		return super.findPage(page, examTaskDetail);
	}
	
	/**
	 * 保存数据
	 * @param examTaskDetail
	 */
	@Transactional(readOnly = false)
	public void save(ExamTaskDetail examTaskDetail) {
		if(examTaskDetail != null && StringUtils.isNotBlank(examTaskDetail.getId())){
			dao.update(examTaskDetail);
		} else {
			examTaskDetail.preInsert();
			dao.insert(examTaskDetail);
		}
	}

	/**
	 * 批量删除数据
	 * @param examTaskDetail
	 */
	@Transactional(readOnly = false)
	public void delete( List<ExamTaskDetail> examTaskDetail) {
		dao.batchDelete(examTaskDetail);
	}

	public void deleteByTaskId(String taskId) {
		dao.deleteByTaskId(taskId);
	}

	public void batchSave(List<ExamTaskDetail> details) {
		dao.batchSave(details);
	}
}