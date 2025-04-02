package uk.edu.le.Part2.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;
import androidx.room.Index;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "course", indices = {@Index(value = "courseCode", unique = true)})
public class Course {
    @PrimaryKey(autoGenerate = true)
    private int courseId;

    private String courseCode;

    private String courseName;

    private String lecturerName;


    public Course(String courseCode, String courseName, String lecturerName) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.lecturerName = lecturerName;
    }
    public Course(){

    }

    // Getters and Setters
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getLecturerName() {
        return lecturerName;
    }

    public void setLecturerName(String lecturerName) {
        this.lecturerName = lecturerName;
    }
}
