package uk.edu.le.Part2;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import uk.edu.le.Part2.database.AppDatabase;
import uk.edu.le.Part2.database.dao.CourseDao;
import uk.edu.le.Part2.database.CourseWithStudents;
import uk.edu.le.Part2.repo.CourseRepository;
import uk.edu.le.Part2.repo.StudentRepository;

public class CourseDetailsViewModel extends AndroidViewModel {
    private CourseDao courseDao;
    private final CourseRepository repo;

    public CourseDetailsViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getDatabase(application);
        courseDao = db.courseDao();
        repo = new CourseRepository(application);
    }

    public LiveData<CourseWithStudents> getCourseWithStudents(int courseId) {
        return courseDao.getCourseWithStudents(courseId);
    }

}