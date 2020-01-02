/**
 * Copyright &copy; 2012-2018 <a href="http://www.it313.cn">big-generator</a> All rights reserved.
 */
package com.it313.elm.school.back.dao;

import com.it313.big.common.persistence.CrudDao;
import com.it313.big.common.persistence.annotation.MyBatisDao;
import com.it313.elm.school.back.entity.ExamTaskDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 任务明细DAO接口
 * @author admin
 * @version 2019-09-17
 */
@MyBatisDao
public interface ExamTaskDetailDao extends CrudDao<ExamTaskDetail> {

    /**
     * 批量删除
     * @param examTaskDetail
     */
	void batchDelete( List<ExamTaskDetail> examTaskDetail);

    void deleteByTaskId(@Param("taskId") String taskId);

    void batchSave(List<ExamTaskDetail> list);
}