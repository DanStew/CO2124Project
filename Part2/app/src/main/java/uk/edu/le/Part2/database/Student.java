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
    private int matricNum;
    @NonNull
    private String username;


    public Student(String fullName, String email, @NonNull String username, int matricNum){
        this.fullName = fullName;
        this.email = email;
        this.username = username;
        this.matricNum = matricNum;
    }

    public Student(String fullName, String email, @NonNull String username){
        this.fullName = fullName;
        this.email = email;
        this.username = username;
    }
    public Student() {}

    // Getters
    public int getStudentId() {
        return studentId;
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

    public int getMatricNum() {
        return matricNum;
    }

    // Setters
    public void setStudentId(int id){
        this.studentId = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    public void setMatricNum(int matricNum) {
        this.matricNum = matricNum;
    }
}
