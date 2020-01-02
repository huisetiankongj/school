/**
 * Copyright &copy; 2012-2018 <a href="http://www.it313.cn">big-generator</a> All rights reserved.
 */
package com.it313.elm.school.back.dao;

import java.util.List;

import com.it313.big.common.persistence.CrudDao;
import com.it313.big.common.persistence.annotation.MyBatisDao;
import com.it313.elm.school.back.dto.ExamTaskStatisticsDto;
import com.it313.elm.school.back.entity.ExamTaskStatistics;
import org.apache.ibatis.annotations.Param;

/**
 * 任务统计DAO接口
 * @author admin
 * @version 2019-09-17
 */
@MyBatisDao
public interface ExamTaskStatisticsDao extends CrudDao<ExamTaskStatistics> {

    /**
     * 批量删除
     * @param examTaskStatistics
     */
	void batchDelete( List<ExamTaskStatistics> examTaskStatistics);

    List<ExamTaskStatisticsDto> findHvList(ExamTaskStatistics examTaskStatistics);

    void deleteByTaskId(@Param("taskId") String taskId);

    void saveByTaskId(ExamTaskStatistics statistics);
}