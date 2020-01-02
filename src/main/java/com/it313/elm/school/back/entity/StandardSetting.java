/**
 * Copyright &copy; 2012-2018 <a href="http://www.it313.cn">big-generator</a> All rights reserved.
 */
package com.it313.elm.school.back.entity;

import com.it313.big.common.utils.StringUtils;
import org.hibernate.validator.constraints.Length;

import com.it313.big.common.persistence.DataEntity;

/**
 * 任务统计Entity
 * @author admin
 * @version 2019-09-27
 */
public class StandardSetting extends DataEntity<StandardSetting> {
	
	private static final long serialVersionUID = 1L;
	private String orgId;		// org_id
	private String subjectId;		// subject_id
	private Integer passScore;		// pass_score
	private Integer excellentScore;		// excellent_score
	private String levelContrast;//等级对照（格式：100:A+|86:A|85:A-|76:B|75:B-|61:C|60:C-|56:D|0:D-）
	private String[] levelContrasts;

	public StandardSetting() {
		super();
	}

	public StandardSetting(String id){
		super(id);
	}

	@Length(min=0, max=32, message="org_id长度必须介于 0 和 32 之间")
	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	@Length(min=0, max=32, message="subject_id长度必须介于 0 和 32 之间")
	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	
	public Integer getPassScore() {
		return passScore;
	}

	public void setPassScore(Integer passScore) {
		this.passScore = passScore;
	}
	
	public Integer getExcellentScore() {
		return excellentScore;
	}

	public void setExcellentScore(Integer excellentScore) {
		this.excellentScore = excellentScore;
	}

	public String getLevelContrast() {
		return levelContrast;
	}

	public void setLevelContrast(String levelContrast) {
		this.levelContrast = levelContrast;
	}

	public String[] getLevelContrasts() {
		if(StringUtils.isNotBlank(this.levelContrast)){
			return this.levelContrast.split("\\|");
		}
		return new String[0];
	}
}