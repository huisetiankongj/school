/**
 * Copyright &copy; 2012-2018 <a href="http://www.it313.cn">big-generator</a> All rights reserved.
 */
package com.it313.elm.school.back.dao;

import java.util.List;

import com.it313.big.common.persistence.CrudDao;
import com.it313.big.common.persistence.annotation.MyBatisDao;
import com.it313.elm.school.back.entity.ExamTask;

/**
 * 考试任务DAO接口
 * @author admin
 * @version 2019-08-27
 */
@MyBatisDao
public interface ExamTaskDao extends CrudDao<ExamTask> {

    /**
     * 批量删除
     * @param examTask
     */
    void batchDelete( List<ExamTask> examTask);

}