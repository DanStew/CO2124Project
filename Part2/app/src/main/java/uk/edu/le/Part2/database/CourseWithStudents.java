package uk.edu.le.Part2.database;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;
import java.util.List;
//retrieve a Course object and all the Student objects that are related to that course via the Enrollment table
public class CourseWithStudents {
    @Embedded
    private Course course;

    @Relation(
            parentColumn = "courseId",
            entityColumn = "studentId",
            associateBy = @Junction(Enrollment.class)
    )
    private List<Student> students;

    public Course getCourse() {
        return course;
    }
    public void setCourse(Course course){
        this.course = course;
    }

    public List<Student> getStudents() {
        return students;
    }
    public void setStudents(List<Student> students){
        this.students = students;
    }
}
