package uk.edu.le.Part2.database.dao;

import androidx.room.*;

import java.util.List;

import uk.edu.le.Part2.database.Enrollment;

@Dao
public interface EnrollmentDao {
    @Insert
    void enrollStudent(Enrollment enrollment);

    @Query("SELECT * FROM enrollment")
    List<Enrollment> getAll(); // For debug/testing

    @Query("DELETE FROM enrollment WHERE courseId = :courseId AND studentId = :studentId")
    void removeStudentFromCourse(int courseId, int studentId);
}
