package com.javaseminar.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "start_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    @Column(name = "end_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;

    public Appointment() {
    }

    public Appointment(String userName, String title, String description, Date startTime, Date endTime) {
        this.userName = userName;
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return id == that.id &&
                userName.equals(that.userName) &&
                title.equals(that.title) &&
                Objects.equals(description, that.description) &&
                startTime.equals(that.startTime) &&
                endTime.equals(that.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, title, description, startTime, endTime);
    }
}
