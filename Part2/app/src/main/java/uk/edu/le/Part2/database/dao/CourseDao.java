package uk.edu.le.Part2.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import java.util.List;

import uk.edu.le.Part2.database.Course;
import uk.edu.le.Part2.database.CourseWithStudents;

@Dao
public interface CourseDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
     void insert(Course course);

    @Update
    void update(Course course);

    @Delete
    void delete(Course course);

    @Query("DELETE FROM course")
    void deleteAll();

    @Query("SELECT * FROM course ")
    LiveData<List<Course>> getCourseList();

    @Transaction
    @Query("SELECT * FROM course WHERE courseId = :courseId")
    LiveData<CourseWithStudents> getCourseWithStudents(int courseId);
}
