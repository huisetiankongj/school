/**
 * Copyright &copy; 2012-2018 <a href="http://www.it313.cn">big-generator</a> All rights reserved.
 */
package com.it313.elm.school.back.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.it313.big.common.persistence.DataEntity;

/**
 * 任务统计Entity
 * @author admin
 * @version 2019-09-17
 */
public class ExamTaskStatistics extends DataEntity<ExamTaskStatistics> {
	
	private static final long serialVersionUID = 1L;
	private String taskId;		// task_id
	private String taskName;//任务名称
	private Integer examType;		// 考试类型（1平时2期中3期末）
	private String orgId;		// 班级
	private String orgName;//班级名称
	private String subjectId;		// subject_id
	private String subjectName;		// subject_name
	private Double avgScore;		// avg_score
	private Integer badNum;		// 不及格人数
	private Double badScore;		// 不及格总分
	private Integer passNum;		// 及格人数
	private Double passcore;		// 及格总分
	private Integer excellentNum;		// 优秀人数
	private Double excellentScore;		// 不及格总分
	private Integer allNum;		// 总人数
	private Double minScore;		//最小分数
	private Double maxScore;		//最大分数
	private Double totalScore;		//总分数
	private Date createTime;		// 创建时间
	private String schoolOrgId;//学校id

	private Date beginTime; // 起始时间
	private Date endTime; // 结束时间
	
	public ExamTaskStatistics() {
		super();
	}

	public ExamTaskStatistics(String id){
		super(id);
	}

	@Length(min=0, max=32, message="task_id长度必须介于 0 和 32 之间")
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	public Integer getExamType() {
		return examType;
	}

	public void setExamType(Integer examType) {
		this.examType = examType;
	}
	
	@Length(min=0, max=32, message="班级长度必须介于 0 和 32 之间")
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
	
	@Length(min=0, max=20, message="subject_name长度必须介于 0 和 20 之间")
	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	
	public Double getAvgScore() {
		return avgScore;
	}

	public void setAvgScore(Double avgScore) {
		this.avgScore = avgScore;
	}
	
	public Integer getBadNum() {
		return badNum;
	}

	public void setBadNum(Integer badNum) {
		this.badNum = badNum;
	}
	
	public Integer getPassNum() {
		return passNum;
	}

	public void setPassNum(Integer passNum) {
		this.passNum = passNum;
	}
	
	public Integer getExcellentNum() {
		return excellentNum;
	}

	public void setExcellentNum(Integer excellentNum) {
		this.excellentNum = excellentNum;
	}
	
	public Integer getAllNum() {
		return allNum;
	}

	public void setAllNum(Integer allNum) {
		this.allNum = allNum;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Double getBadScore() {
		return badScore;
	}

	public void setBadScore(Double badScore) {
		this.badScore = badScore;
	}

	public Double getPasscore() {
		return passcore;
	}

	public void setPasscore(Double passcore) {
		this.passcore = passcore;
	}

	public Double getExcellentScore() {
		return excellentScore;
	}

	public void setExcellentScore(Double excellentScore) {
		this.excellentScore = excellentScore;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getSchoolOrgId() {
		return schoolOrgId;
	}

	public void setSchoolOrgId(String schoolOrgId) {
		this.schoolOrgId = schoolOrgId;
	}

	public Double getMinScore() {
		return minScore;
	}

	public void setMinScore(Double minScore) {
		this.minScore = minScore;
	}

	public Double getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(Double maxScore) {
		this.maxScore = maxScore;
	}

	public Double getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Double totalScore) {
		this.totalScore = totalScore;
	}
}