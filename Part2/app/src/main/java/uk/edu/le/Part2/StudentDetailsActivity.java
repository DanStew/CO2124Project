package uk.edu.le.Part2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;
import android.util.Log;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import uk.edu.le.Part2.database.AppDatabase;
import uk.edu.le.Part2.database.Enrollment;
import uk.edu.le.Part2.database.Student;
import uk.edu.le.Part2.database.StudentWithCourses;
import uk.edu.le.Part2.database.dao.StudentDao;
import uk.edu.le.Part2.StudentViewModel;

public class StudentDetailsActivity extends AppCompatActivity {

    public static final int EDIT_STUDENT_ACTIVITY_REQUEST_CODE = 1;
    public static final int RESULT_CUSTOM = 1001;
    private StudentViewModel studentViewModel;
    private CourseListAdapter adapter;
    private RecyclerView recyclerView;
    private int studentId;
    private int courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);

        // Init ViewModel and RecyclerView once
        studentViewModel = new ViewModelProvider(this).get(StudentViewModel.class);
        adapter = new CourseListAdapter(new CourseListAdapter.CourseDiff());


        recyclerView = findViewById(R.id.courseRecyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        studentId = getIntent().getIntExtra("studentId", -1);
        courseId = getIntent().getIntExtra("courseId", -1);

        if (studentId != -1) {
            String studentName = getIntent().getStringExtra("studentName");
            String studentEmail = getIntent().getStringExtra("studentEmail");
            int studentMatric = getIntent().getIntExtra("studentMatric", -1);

            ((TextView) findViewById(R.id.studentNameTextView)).setText("Name: " + studentName);
            ((TextView) findViewById(R.id.studentEmailTextView)).setText("Email: " + studentEmail);
            ((TextView) findViewById(R.id.studentMatricTextView)).setText("Matric Number: " + studentMatric);

            // Observe courses for this student
            studentViewModel.getCoursesForStudent(studentId).observe(this, courses -> {
                if (courses != null && !courses.isEmpty()) {
                    Log.d("StudentDetailsActivity", "Courses for student: " + courses.size());
                    adapter.submitList(courses);
                } else {
                    Toast.makeText(this, "No courses found for this student.", Toast.LENGTH_SHORT).show();
                }
            });

            showStudentOptionsDialog(studentId, studentName, studentEmail, studentMatric);

        } else {
            Toast.makeText(this, "Invalid student ID", Toast.LENGTH_SHORT).show();
        }

    }

    private void showStudentOptionsDialog(int StudentId, String studentName, String studentEmail, int studentMatric) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("What would you like to do with this student?");

        builder.setItems(new CharSequence[]{"Edit", "Remove", "Cancel"},
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0: // Edit
                                editStudent(studentId, studentName, studentEmail, studentMatric);
                                break;
                            case 1: // Remove
                                removeStudent();
                                break;
                            case 2: // Cancel
                                dialog.dismiss();
                                break;
                        }
                    }
                });
        builder.create().show();
    }

    private void editStudent(int studentId, String studentName, String studentEmail, int studentMatric) {
        Intent intent = new Intent(StudentDetailsActivity.this, EditStudentActivity.class);
        intent.putExtra("studentId", studentId);
        intent.putExtra("studentName", studentName);
        intent.putExtra("studentEmail", studentEmail);
        intent.putExtra("studentMatric", studentMatric);
        startActivityForResult(intent, EDIT_STUDENT_ACTIVITY_REQUEST_CODE);
    }

    private void removeStudent() {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            Enrollment enrollment = AppDatabase.getDatabase(this).enrollmentDao().getEnrollment(studentId, courseId);
            AppDatabase.getDatabase(this).enrollmentDao().delete(enrollment);
            finish();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_STUDENT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            int studentId = data.getIntExtra(EditStudentActivity.EXTRA_REPLY_ID, -1);
            String editStudentName = data.getStringExtra(EditStudentActivity.EXTRA_REPLY_NAME);
            String editStudentEmail = data.getStringExtra(EditStudentActivity.EXTRA_REPLY_EMAIL);
            int editStudentMatric = data.getIntExtra(EditStudentActivity.EXTRA_REPLY_MATRIC, -1);

            studentViewModel.getStudentById(studentId).observe(this, student -> {
                if (student != null) {
                    student.setFullName(editStudentName);
                    student.setEmail(editStudentEmail);
                    student.setMatricNum(editStudentMatric);

                    studentViewModel.update(student);

                    studentViewModel.getStudentById(studentId).observe(this, updatedStudent -> {
                        if (updatedStudent != null) {
                            ((TextView) findViewById(R.id.studentNameTextView))
                                    .setText("Name: " + updatedStudent.getFullName());
                            ((TextView) findViewById(R.id.studentEmailTextView))
                                    .setText("Email: " + updatedStudent.getEmail());
                            ((TextView) findViewById(R.id.studentMatricTextView))
                                    .setText("Matric Number: " + updatedStudent.getMatricNum());
                        }
                    });
                }
            });
        } else if (resultCode == RESULT_CUSTOM) {
            Toast.makeText(this, R.string.matric_not_num, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, R.string.student_empty_not_saved, Toast.LENGTH_LONG).show();
        }
    }

}