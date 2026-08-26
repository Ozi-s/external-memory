package org.ozis.dto;

import java.time.LocalDate;

public class TaskDto {
    private int taskId;
    private String taskName;
    private String description;
    private int executionPeriod;
    private LocalDate deadline;
    private String projectName;
    private String statusName;

    public TaskDto(int taskId, String taskName, String description,
                          int executionPeriod, LocalDate deadline,
                          String projectName, String statusName) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.description = description;
        this.executionPeriod = executionPeriod;
        this.deadline = deadline;
        this.projectName = projectName;
        this.statusName = statusName;
    }

    // Геттеры
    public int getTaskId() { return taskId; }
    public String getTaskName() { return taskName; }
    public String getDescription() { return description; }
    public int getExecutionPeriod() { return executionPeriod; }
    public LocalDate getDeadline() { return deadline; }
    public String getProjectName() { return projectName; }
    public String getStatusName() { return statusName; }

}