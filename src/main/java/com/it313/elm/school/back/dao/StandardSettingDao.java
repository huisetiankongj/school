/**
 * Copyright &copy; 2012-2018 <a href="http://www.it313.cn">big-generator</a> All rights reserved.
 */
package com.it313.elm.school.back.dao;

import java.util.List;

import com.it313.big.common.persistence.CrudDao;
import com.it313.big.common.persistence.annotation.MyBatisDao;
import com.it313.elm.school.back.entity.StandardSetting;

/**
 * 任务统计DAO接口
 * @author admin
 * @version 2019-09-27
 */
@MyBatisDao
public interface StandardSettingDao extends CrudDao<StandardSetting> {

    /**
     * 批量删除
     * @param standardSetting
     */
	void batchDelete( List<StandardSetting> standardSetting);

    List<StandardSetting> getByOrgId(String orgId);
}