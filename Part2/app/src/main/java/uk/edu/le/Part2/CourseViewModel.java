package uk.edu.le.Part2;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import uk.edu.le.Part2.database.AppDatabase;
import uk.edu.le.Part2.database.Course;
import uk.edu.le.Part2.database.dao.CourseDao;
import uk.edu.le.Part2.database.dao.EnrollmentDao;
import uk.edu.le.Part2.database.dao.StudentDao;
import uk.edu.le.Part2.repo.CourseRepository;

public class CourseViewModel extends AndroidViewModel {
    private StudentDao studentDao;
    private EnrollmentDao enrollmentDao;
    private CourseDao courseDao;

    private CourseRepository repo;
    private final LiveData<List<Course>> allItems;

    public CourseViewModel( Application application) {
        super(application);
        studentDao = AppDatabase.getDatabase(application).studentDao();
        repo = new CourseRepository(application);
        allItems = repo.getAllCourses();
    }
    LiveData<List<Course>> getAllItems() {return  allItems;}
    public void insert(Course course){repo.insert(course);}

    public void delete(Course course) {
        repo.delete(course);
    }


}
