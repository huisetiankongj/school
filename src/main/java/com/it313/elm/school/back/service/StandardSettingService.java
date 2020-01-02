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
import com.it313.elm.school.back.entity.StandardSetting;
import com.it313.elm.school.back.dao.StandardSettingDao;

/**
 * 任务统计Service
 * @author admin
 * @version 2019-09-27
 */
@Service
@Transactional(readOnly = true)
public class StandardSettingService extends CrudService<StandardSettingDao, StandardSetting> {

     /**
      * 查询单个
      * @param id
      */
	public StandardSetting get(String id) {
		return super.get(id);
	}
	
	/**
	 * 遍历列表
	 * @param standardSetting
	 */
	public List<StandardSetting> findList(StandardSetting standardSetting) {
		return super.findList(standardSetting);
	}
	
	public Page<StandardSetting> findPage(Page<StandardSetting> page, StandardSetting standardSetting) {
		return super.findPage(page, standardSetting);
	}
	
	/**
	 * 保存数据
	 * @param standardSetting
	 */
	@Transactional(readOnly = false)
	public void save(StandardSetting standardSetting) {
		if(standardSetting != null && StringUtils.isNotBlank(standardSetting.getId())){
			dao.update(standardSetting);
		} else {
			standardSetting.preInsert();
			dao.insert(standardSetting);
		}
	}

	/**
	 * 批量删除数据
	 * @param standardSetting
	 */
	@Transactional(readOnly = false)
	public void delete( List<StandardSetting> standardSetting) {
		dao.batchDelete(standardSetting);
	}

    public List<StandardSetting> getByOrgId(String orgId) {
		return dao.getByOrgId(orgId);
    }
}