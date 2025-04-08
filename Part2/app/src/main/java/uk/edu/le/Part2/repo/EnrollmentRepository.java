package uk.edu.le.Part2.repo;

import android.app.Application;

import uk.edu.le.Part2.database.AppDatabase;
import uk.edu.le.Part2.database.Enrollment;
import uk.edu.le.Part2.database.dao.EnrollmentDao;

public class EnrollmentRepository {
    private EnrollmentDao enrollmentDao;

    public EnrollmentRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        enrollmentDao = db.enrollmentDao();
    }

    public void enrollStudent(Enrollment enrollment) {
        AppDatabase.databaseWriteExecutor.execute(() -> enrollmentDao.enrollStudent(enrollment));
    }

    public void removeStudentFromCourse(int courseId, int studentId) {
        AppDatabase.databaseWriteExecutor.execute(() -> enrollmentDao.removeStudentFromCourse(courseId, studentId));
    }

}
