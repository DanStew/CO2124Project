package uk.edu.le.Part2.repo;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import java.util.List;

import uk.edu.le.Part2.model.Course;

@Dao
public interface CourseDoa {
    @Insert
    void insertCourse(Course course);

    @Query("SELECT * FROM course")
    LiveData<List<Course>> getCourseList();
}
