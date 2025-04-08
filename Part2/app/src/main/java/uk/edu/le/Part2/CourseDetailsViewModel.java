package uk.edu.le.Part2;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import uk.edu.le.Part2.database.AppDatabase;
import uk.edu.le.Part2.database.dao.CourseDao;
import uk.edu.le.Part2.database.CourseWithStudents;

public class CourseDetailsViewModel extends AndroidViewModel {
    private CourseDao courseDao;

    public CourseDetailsViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getDatabase(application);
        courseDao = db.courseDao();
    }

    public LiveData<CourseWithStudents> getCourseWithStudents(int courseId) {

        return courseDao.getCourseWithStudents(courseId);
    }
}