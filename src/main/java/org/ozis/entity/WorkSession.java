package org.ozis.entity;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "session")
public class WorkSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "focus_task")
    private Task focus;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(name = "session_date", nullable = false)
    private LocalDate date;

    @Column(name = "activity", columnDefinition = "TEXT")
    private String activity;

    @Column(name = "result", columnDefinition = "TEXT")
    private String result;

    @Column(name = "not_finished", columnDefinition = "TEXT")
    private String notFinished;

    @Column(name = "next", columnDefinition = "TEXT")
    private String next;

    @Column(name = "open_question", columnDefinition = "TEXT")
    private String openQuestion;


    public WorkSession() {
    }

    public WorkSession(Project project, Task focus) {
        this.project = project;
        this.focus = focus;
        this.startTime = LocalTime.now();
        this.date = LocalDate.now();
    }


    public Integer getId() {
        return id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Task getFocus() {
        return focus;
    }

    public void setFocus(Task focus) {
        this.focus = focus;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getNotFinished() {
        return notFinished;
    }

    public void setNotFinished(String notFinished) {
        this.notFinished = notFinished;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getOpenQuestion() {
        return openQuestion;
    }

    public void setOpenQuestion(String openQuestion) {
        this.openQuestion = openQuestion;
    }
}