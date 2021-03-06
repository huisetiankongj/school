/**
 * Copyright &copy; 2012-2018 <a href="http://www.it313.cn">big-generator</a> All rights reserved.
 */
package com.it313.elm.school.back.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.it313.big.common.persistence.DataEntity;

/**
 * 考试任务Entity
 * @author admin
 * @version 2019-08-27
 */
public class ExamTask extends DataEntity<ExamTask> {

	private static final long serialVersionUID = 1L;
	private String etTitle;		// 考试标题
	private String etSubjectIds;		// 考试学科ids
	private String etSubjectNames;		// 考试学科names
	private Integer type;//所属类型（1平时2期中3期末）
	private Integer etOrgType;		// 考试类型（1所有2年级3班级）
	private String etOrgIds;		// 考试班级ids
	private String etOrgNames;		// 考试班级names
	private String createUserId;		// 创建人
	private String createUserName;		// 创建人
	private String state;		// 状态
	private String updateUserId;		// 修改人
	private String updateUserName;		// 修改人

	private String schoolOrgId;//学校id

	public ExamTask() {
		super();
	}

	public ExamTask(String id){
		super(id);
	}

	@Length(min=0, max=50, message="考试标题长度必须介于 0 和 50 之间")
	public String getEtTitle() {
		return etTitle;
	}

	public void setEtTitle(String etTitle) {
		this.etTitle = etTitle;
	}

	@Length(min=0, max=200, message="考试学科ids长度必须介于 0 和 200 之间")
	public String getEtSubjectIds() {
		return etSubjectIds;
	}

	public void setEtSubjectIds(String etSubjectIds) {
		this.etSubjectIds = etSubjectIds;
	}

	@Length(min=0, max=100, message="考试学科names长度必须介于 0 和 100 之间")
	public String getEtSubjectNames() {
		return etSubjectNames;
	}

	public void setEtSubjectNames(String etSubjectNames) {
		this.etSubjectNames = etSubjectNames;
	}

	@Length(min=0, max=1, message="考试类型（1所有2年级3班级）长度必须介于 0 和 1 之间")
	public Integer getEtOrgType() {
		return etOrgType;
	}

	public void setEtOrgType(Integer etOrgType) {
		this.etOrgType = etOrgType;
	}

	@Length(min=0, max=500, message="考试班级ids长度必须介于 0 和 500 之间")
	public String getEtOrgIds() {
		return etOrgIds;
	}

	public void setEtOrgIds(String etOrgIds) {
		this.etOrgIds = etOrgIds;
	}

	@Length(min=0, max=200, message="考试班级names长度必须介于 0 和 200 之间")
	public String getEtOrgNames() {
		return etOrgNames;
	}

	public void setEtOrgNames(String etOrgNames) {
		this.etOrgNames = etOrgNames;
	}

	@Length(min=0, max=32, message="创建人长度必须介于 0 和 32 之间")
	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	@Length(min=0, max=1, message="状态长度必须介于 0 和 1 之间")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Length(min=0, max=32, message="创建人长度必须介于 0 和 32 之间")
	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getUpdateUserName() {
		return updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	public String getSchoolOrgId() {
		return schoolOrgId;
	}

	public void setSchoolOrgId(String schoolOrgId) {
		this.schoolOrgId = schoolOrgId;
	}
}