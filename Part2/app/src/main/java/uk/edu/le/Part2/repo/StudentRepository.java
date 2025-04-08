package uk.edu.le.Part2.repo;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import uk.edu.le.Part2.database.AppDatabase;
import uk.edu.le.Part2.database.Student;
import uk.edu.le.Part2.database.dao.StudentDao;
import uk.edu.le.Part2.database.StudentWithCourses;

public class StudentRepository {
    private StudentDao studentDao;
    private LiveData<List<Student>> allStudents;

    public StudentRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        studentDao = db.studentDao();
        allStudents = studentDao.getAllStudents();
    }

    public LiveData<List<Student>> getAllStudents() {
        return allStudents;
    }

    public LiveData<StudentWithCourses> getStudentWithCourses(int studentId) {
        return studentDao.getStudentWithCourses(studentId);
    }
    public void insert(Student student) {
        AppDatabase.databaseWriteExecutor.execute(() -> studentDao.insert(student));
    }
    public void update(Student student){
        AppDatabase.databaseWriteExecutor.execute(()-> studentDao.delete(student));
    }
    public void delete(Student student){
        AppDatabase.databaseWriteExecutor.execute(()-> studentDao.delete(student));
    }


}
