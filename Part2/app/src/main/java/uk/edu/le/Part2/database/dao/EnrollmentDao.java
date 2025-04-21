package uk.edu.le.Part2.database.dao;

import androidx.room.*;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import uk.edu.le.Part2.database.Enrollment;
import uk.edu.le.Part2.database.Course;
import androidx.lifecycle.LiveData;

@Dao
public interface EnrollmentDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Enrollment enrollment);

    @Query("SELECT * FROM enrollment WHERE studentId = :studentId AND courseId = :courseId LIMIT 1")
    Enrollment getEnrollment(int studentId, int courseId);

    @Query("SELECT * FROM enrollment")
    List<Enrollment> getAll(); // For debug/testing

    @Query("DELETE FROM enrollment WHERE courseId = :courseId AND studentId = :studentId")
    void removeStudentFromCourse(int courseId, int studentId);

    @Transaction
    @Query("SELECT * FROM Course WHERE courseId IN (SELECT courseId FROM Enrollment WHERE studentId = :studentId)")
    LiveData<List<Course>> getCoursesForStudent(long studentId);

    @Delete
    void delete(Enrollment enrollment);
}
