/**
 * Copyright &copy; 2012-2018 <a href="http://www.it313.cn">big-generator</a> All rights reserved.
 */
package com.it313.elm.school.back.entity;

import com.it313.big.common.utils.excel.annotation.ExcelField;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.it313.big.common.persistence.DataEntity;

/**
 * 学生成绩Entity
 * @author admin
 * @version 2019-08-27
 */
public class StudentAchievement extends DataEntity<StudentAchievement> {

    private static final long serialVersionUID = 1L;
    private String taskId;		// 任务id
    private String taskName;//任务名
    private String stuNo;		// 学生学号
    private String stuName;//学生名字
    private String subjectId;		// 学科id
    private String subjectName;//学科名称
    private String orgId;		// 班级id
    private String orgName;		// 班级名称
    private Double score;		// 成绩
    private Date createTime;		// 创建时间
    private String createUserId;		// 创建人
    private String createUserName;		// 创建人
    private Date updateTime;		// 修改时间
    private String updateUserId;		// 创建人
    private String updateUserName;		// 创建人
    private String schoolOrgId;//学校id

    private Integer beginScore;//起始成绩
    private Integer endScore;//结束成绩

    private String type;//类型（bad:不及格,pass:及格,excellent:优秀,all:所有）

    public StudentAchievement() {
        super();
    }

    public StudentAchievement(String id){
        super(id);
    }

    @Length(min=0, max=32, message="任务id长度必须介于 0 和 32 之间")
    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @Length(min=0, max=32, message="学科id长度必须介于 0 和 32 之间")
    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    @Length(min=0, max=32, message="班级id长度必须介于 0 和 32 之间")
    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    @Length(min=0, max=20, message="班级名称长度必须介于 0 和 20 之间")
    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setStuNo(String stuNo) {
        this.stuNo = stuNo;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    @ExcelField(title="学号", align=2, sort=1)
    public String getStuNo() {
        return stuNo;
    }

    @ExcelField(title="名字", align=2, sort=2)
    public String getStuName() {
        return stuName;
    }

    @ExcelField(title="成绩", align=2, sort=3)
    public Double getScore() {
        return score;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getSubjectName() {
       return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public Integer getBeginScore() {
        return beginScore;
    }

    public void setBeginScore(Integer beginScore) {
        this.beginScore = beginScore;
    }

    public Integer getEndScore() {
        return endScore;
    }

    public void setEndScore(Integer endScore) {
        this.endScore = endScore;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSchoolOrgId() {
        return schoolOrgId;
    }

    public void setSchoolOrgId(String schoolOrgId) {
        this.schoolOrgId = schoolOrgId;
    }
}