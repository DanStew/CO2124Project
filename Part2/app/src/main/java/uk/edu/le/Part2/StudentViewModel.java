package uk.edu.le.Part2;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import uk.edu.le.Part2.database.Course;
import uk.edu.le.Part2.database.Student;
import uk.edu.le.Part2.database.Enrollment;
import uk.edu.le.Part2.repo.StudentRepository;
import uk.edu.le.Part2.repo.EnrollmentRepository;

public class StudentViewModel extends AndroidViewModel {
    private StudentRepository repo;
    private EnrollmentRepository enrollmentRepo;
    private final LiveData<List<Student>> allItems;
    private LiveData<List<Course>> coursesForStudent;

    public StudentViewModel(Application application) {
        super(application);
        repo = new StudentRepository(application);
        enrollmentRepo = new EnrollmentRepository(application);
        allItems = repo.getAllStudents();
    }

    public void enrollStudentInCourse(int courseId, int studentId) {
        repo.enrollStudentInCourse(courseId, studentId);
    }

    public void insertEnrollment(Enrollment enrollment) {
        repo.insertEnrollment(enrollment);
    }

    public void insert(Student student) {
        repo.insert(student);
    }

    public LiveData<List<Student>> getAllItems() {
        return  allItems;
    }

    public LiveData<List<Course>> getCoursesForStudent(long studentId) {
        return enrollmentRepo.getCoursesForStudent(studentId);
    }

    public LiveData<Student> getStudentById(int id) {
        return repo.getStudentById(id);
    }

    public void update(Student student) {
        repo.update(student);
    }

}
