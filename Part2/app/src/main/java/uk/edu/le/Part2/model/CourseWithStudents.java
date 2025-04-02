package uk.edu.le.Part2.model;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;
import java.util.List;
//get students for a course
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

    public List<Student> getStudents() {
        return students;
    }
}
