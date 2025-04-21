package uk.edu.le.Part2.repo;

import android.app.Application;

import uk.edu.le.Part2.database.AppDatabase;
import uk.edu.le.Part2.database.Enrollment;
import uk.edu.le.Part2.database.dao.EnrollmentDao;
import uk.edu.le.Part2.database.Course;

import androidx.lifecycle.LiveData;
import java.util.List;


public class EnrollmentRepository {
    private EnrollmentDao enrollmentDao;

    public EnrollmentRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        enrollmentDao = db.enrollmentDao();
    }

    public void insertEnrollment(Enrollment enrollment) {
        AppDatabase.databaseWriteExecutor.execute(() -> enrollmentDao.insert(enrollment));
    }

    public void removeStudentFromCourse(int courseId, int studentId) {
        AppDatabase.databaseWriteExecutor.execute(() -> enrollmentDao.removeStudentFromCourse(courseId, studentId));
    }

    public LiveData<List<Course>> getCoursesForStudent(long studentId) {
        return enrollmentDao.getCoursesForStudent(studentId);
    }

}
