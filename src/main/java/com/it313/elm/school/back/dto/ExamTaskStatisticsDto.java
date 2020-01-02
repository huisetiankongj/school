package com.it313.elm.school.back.dto;

public class ExamTaskStatisticsDto {

    private static final long serialVersionUID = 1L;
    private String taskId;		// task_id
    private String taskName;//任务名称
    private Integer examType;		// 考试类型（1平时2期中3期末）
    private String orgId;		// 班级
    private String orgName;//班级名称
    private String remark;		// 备注

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Integer getExamType() {
        return examType;
    }

    public void setExamType(Integer examType) {
        this.examType = examType;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
