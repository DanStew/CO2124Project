package uk.edu.le.Part2.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import uk.edu.le.Part2.database.Student;
import uk.edu.le.Part2.database.StudentWithCourses;

@Dao
public interface StudentDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Student student); // Returns new ID

    @Update
    void update(Student student);

    @Delete
    void delete(Student student);

    @Query("DELETE FROM student")
    void deleteAll();

    @Query("SELECT * FROM student")
    LiveData<List<Student>> getAllStudents();

    @Transaction
    @Query("SELECT * FROM student WHERE studentId = :studentId")
    LiveData<StudentWithCourses> getStudentWithCourses(int studentId);

    @Query("SELECT * FROM Student WHERE studentId = :studentId")
    LiveData<Student> getStudentById(int studentId);
}
