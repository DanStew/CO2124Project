package uk.edu.le.Part2.repo;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

import uk.edu.le.Part2.database.AppDatabase;
import uk.edu.le.Part2.database.Course;
import uk.edu.le.Part2.database.dao.CourseDao;
import uk.edu.le.Part2.database.CourseWithStudents;

public class CourseRepository {
    private final CourseDao courseDao;
    private final LiveData<List<Course>> allCourses;

    public CourseRepository(Application application) {
        // Initialise AppDatabase and CourseDao
        AppDatabase db = AppDatabase.getDatabase(application);
        courseDao = db.courseDao();
        allCourses = courseDao.getCourseList();  // LiveData for all courses
        Log.d("courseViewModel", "before");
        Log.d("CourseViewModel", "LiveData is " + (allCourses == null ? "null" : "not null"));
    }

    // Get all courses
    public LiveData<List<Course>> getAllCourses() {
        Log.d("courseRep", "Fetching all courses from the database");
        return courseDao.getCourseList();
    }

    // Get course with students by courseId
    public LiveData<CourseWithStudents> getCourseWithStudents(int courseId) {
        return courseDao.getCourseWithStudents(courseId);
    }

    // Insert a course into the database (background thread)
    public void insert(Course course) {
        AppDatabase.databaseWriteExecutor.execute(() -> courseDao.insert(course));
    }

    // Delete a course from the database (background thread)
    public void delete(Course course) {
        AppDatabase.databaseWriteExecutor.execute(() -> courseDao.delete(course));
    }

    // Update a course if needed
    public void update(Course course) {
        AppDatabase.databaseWriteExecutor.execute(() -> courseDao.update(course));
    }
}
