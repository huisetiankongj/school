/**
 * Copyright &copy; 2012-2018 <a href="http://www.it313.cn">big-generator</a> All rights reserved.
 */
package com.it313.elm.school.back.entity;

import org.hibernate.validator.constraints.Length;

import com.it313.big.common.persistence.DataEntity;

/**
 * 任务明细Entity
 * @author admin
 * @version 2019-09-17
 */
public class ExamTaskDetail extends DataEntity<ExamTaskDetail> {
	
	private static final long serialVersionUID = 1L;
	private String taskId;		// task_id
	private String orgId;		// org_id
	
	public ExamTaskDetail() {
		super();
	}

	public ExamTaskDetail(String id){
		super(id);
	}

	@Length(min=0, max=32, message="task_id长度必须介于 0 和 32 之间")
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	@Length(min=0, max=32, message="org_id长度必须介于 0 和 32 之间")
	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
}