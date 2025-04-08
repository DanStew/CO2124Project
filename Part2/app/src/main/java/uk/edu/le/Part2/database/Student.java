package uk.edu.le.Part2.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "student", indices = {@Index(value = "username", unique = true)})
public class Student {
    @PrimaryKey(autoGenerate = true)
    private int studentId;

    private String fullName;
    private String email;
    @NonNull
    private String username;

    public Student(String fullName, String email, String username){
        this.fullName = fullName;
        this.email = email;
        this.username = username;
    }

    public int getStudentId() {
        return studentId;
    }
    public void setStudentId(int id){
        this.studentId = id;

    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }
}
