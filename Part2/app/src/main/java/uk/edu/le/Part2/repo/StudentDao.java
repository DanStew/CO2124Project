package uk.edu.le.Part2.repo;

import androidx.room.*;

import uk.edu.le.Part2.model.Student;

@Dao
public interface StudentDao {
    @Insert
    void insertStudent(Student student);
}
