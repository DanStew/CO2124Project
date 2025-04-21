package uk.edu.le.Part2.repo;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

import uk.edu.le.Part2.database.AppDatabase;
import uk.edu.le.Part2.database.Student;
import uk.edu.le.Part2.database.StudentWithCourses;
import uk.edu.le.Part2.database.Enrollment;
import uk.edu.le.Part2.database.dao.StudentDao;
import uk.edu.le.Part2.database.dao.EnrollmentDao;

public class StudentRepository {
    private StudentDao studentDao;
    private EnrollmentDao enrollmentDao;
    private LiveData<List<Student>> allStudents;

    public StudentRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        studentDao = db.studentDao();
        enrollmentDao = db.enrollmentDao();
        allStudents = studentDao.getAllStudents();
    }

    public LiveData<List<Student>> getAllStudents() {
        return allStudents;
    }

    public LiveData<StudentWithCourses> getStudentWithCourses(int studentId) {
        return studentDao.getStudentWithCourses(studentId);
    }

    public void enrollStudentInCourse(int courseId, int studentId) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            enrollmentDao.insert(new Enrollment(courseId, studentId));
        });
    }

    public void insertEnrollment(Enrollment enrollment) {
        AppDatabase.databaseWriteExecutor.execute(() -> enrollmentDao.insert(enrollment));
    }

    public void insert(Student student) {
        AppDatabase.databaseWriteExecutor.execute(() -> studentDao.insert(student));
    }

    public void update(Student student) {
        AppDatabase.databaseWriteExecutor.execute(()-> studentDao.update(student));
    }

    public void delete(Student student) {
        AppDatabase.databaseWriteExecutor.execute(()-> studentDao.delete(student));
    }

    public LiveData<Student> getStudentById(int id) {
        return studentDao.getStudentById(id);
    }

}
