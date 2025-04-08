package uk.edu.le.Part2.database;

import androidx.room.Entity;
import androidx.room.ForeignKey;

//class acts as a bridge, storing the associations between students and courses
@Entity(
        tableName = "enrollment",
        primaryKeys = {"studentId", "courseId"},
        foreignKeys = {
                @ForeignKey(entity = Student.class,
                        parentColumns = "studentId",
                        childColumns = "studentId",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Course.class,
                        parentColumns = "courseId",
                        childColumns = "courseId",
                        onDelete = ForeignKey.CASCADE)
        }
)
public class Enrollment {
    private int studentId;
    private int courseId;

    public Enrollment(int studentId, int courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
    }


    public int getStudentId() {
        return studentId;
    }

    public int getCourseId() {
        return courseId;
    }
}
