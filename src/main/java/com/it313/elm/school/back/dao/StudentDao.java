/**
 * Copyright &copy; 2012-2018 <a href="http://www.it313.cn">big-generator</a> All rights reserved.
 */
package com.it313.elm.school.back.dao;

import java.util.List;

import com.it313.big.common.persistence.CrudDao;
import com.it313.big.common.persistence.annotation.MyBatisDao;
import com.it313.elm.school.back.entity.Student;
import org.apache.ibatis.annotations.Param;

/**
 * 学生DAO接口
 * @author admin
 * @version 2019-08-27
 */
@MyBatisDao
public interface StudentDao extends CrudDao<Student> {

    /**
     * 批量删除
     * @param student
     */
    void batchDelete( List<Student> student);

    void createTable(@Param("orgId")String orgId);

    void dropTable(@Param("orgId")String orgId);

    void batchSaveTemp(@Param("orgId")String gasStationId,@Param("list")List<Student> list);

    List<String> findMulStuNo(@Param("orgId")String orgId);

    List<Student> findHasStuNo(@Param("orgId")String orgId);

    void batchSave(List<Student> addStuList);

    List<Student> findSchoolList(Student s);
}