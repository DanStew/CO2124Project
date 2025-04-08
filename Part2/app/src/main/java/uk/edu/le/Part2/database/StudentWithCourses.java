package uk.edu.le.Part2.database;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;
import java.util.List;
//get course for a student
public class StudentWithCourses {
    @Embedded
    private Student student;

    @Relation(
            parentColumn = "studentId",
            entityColumn = "courseId",
            associateBy = @Junction(Enrollment.class)
    )
    private List<Course> courses;

    public Student getStudent() {
        return student;
    }
    public void setStudent(Student student){
        this.student = student;

    }

    public List<Course> getCourses() {
        return courses;
    }
    public void setCourses(List<Course> course){
        this.courses = course;
    }
}
