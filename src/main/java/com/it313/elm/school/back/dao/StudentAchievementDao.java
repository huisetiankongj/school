/**
 * Copyright &copy; 2012-2018 <a href="http://www.it313.cn">big-generator</a> All rights reserved.
 */
package com.it313.elm.school.back.dao;

import java.util.List;
import java.util.Map;

import com.it313.big.common.persistence.CrudDao;
import com.it313.big.common.persistence.annotation.MyBatisDao;
import com.it313.elm.school.back.entity.StudentAchievement;

/**
 * 学生成绩DAO接口
 * @author admin
 * @version 2019-08-27
 */
@MyBatisDao
public interface StudentAchievementDao extends CrudDao<StudentAchievement> {

    /**
     * 批量删除
     * @param studentAchievement
     */
    void batchDelete( List<StudentAchievement> studentAchievement);

    void batchSave(List<StudentAchievement> addStuList);

    List<StudentAchievement> importModuleData(StudentAchievement studentAch);

    void updateScore(StudentAchievement studentAchievement);

    List<Map<String,Object>> allSubjectAchievement(StudentAchievement studentAchievement);

    Integer allSubjectAchievementCount(StudentAchievement studentAchievement);
}