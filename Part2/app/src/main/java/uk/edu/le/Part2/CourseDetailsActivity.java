package uk.edu.le.Part2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import uk.edu.le.Part2.database.AppDatabase;
import uk.edu.le.Part2.database.Course;
import uk.edu.le.Part2.database.Student;
import uk.edu.le.Part2.database.Enrollment;

public class CourseDetailsActivity extends AppCompatActivity {

    public static final int ADD_STUDENT_ACTIVITY_REQUEST_CODE = 1;
    public static final int RESULT_CUSTOM = 1001;
    private StudentViewModel studentViewModel;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details); // Assuming you have a layout with only RecyclerView

        RecyclerView studentsRecyclerView = findViewById(R.id.studentsRecyclerView);

        int courseId = getIntent().getIntExtra("courseId", -1);
        if (courseId == -1) {
            Toast.makeText(this, "Invalid course ID", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        StudentListAdapter adapter = new StudentListAdapter(new StudentListAdapter.StudentDiff());
        studentsRecyclerView.setAdapter(adapter);
        studentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setOnItemClickListener(student -> {

            Intent intent = new Intent(CourseDetailsActivity.this, StudentDetailsActivity.class);
            intent.putExtra("studentId", student.getStudentId());
            intent.putExtra("studentName", student.getFullName());
            intent.putExtra("studentEmail", student.getEmail());
            intent.putExtra("studentMatric", student.getMatricNum());
            intent.putExtra("courseId", courseId);
            startActivity(intent);

        });

        CourseDetailsViewModel viewModel = new ViewModelProvider(this).get(CourseDetailsViewModel.class);
        studentViewModel = new ViewModelProvider(this).get(StudentViewModel.class);

        viewModel.getCourseWithStudents(courseId).observe(this, courseWithStudents -> {
            if (courseWithStudents != null) {
                adapter.submitList(courseWithStudents.getStudents());
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(CourseDetailsActivity.this, AddStudentActivity.class);
            startActivityForResult(intent, ADD_STUDENT_ACTIVITY_REQUEST_CODE);;
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_STUDENT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            String studentName = data.getStringExtra(AddStudentActivity.EXTRA_REPLY_NAME);
            String studentEmail = data.getStringExtra(AddStudentActivity.EXTRA_REPLY_EMAIL);
            String studentMatric = data.getStringExtra(AddStudentActivity.EXTRA_REPLY_MATRIC);

            // Validate input before creating Student object
            if (studentName == null || studentEmail == null || studentMatric == null || studentMatric.isEmpty()) {
                Toast.makeText(this, "Incomplete student data! " + studentMatric, Toast.LENGTH_SHORT).show();
                return;
            }

            int matricNum;
            try {
                matricNum = Integer.parseInt(studentMatric);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Matric number must be a valid number!", Toast.LENGTH_SHORT).show();
                return;
            }

            Student newStudent = new Student(); // Use default constructor
            newStudent.setFullName(studentName);
            newStudent.setEmail(studentEmail);
            newStudent.setUsername(studentMatric); // Assuming username = matric number
            newStudent.setMatricNum(matricNum);

            int courseId = getIntent().getIntExtra("courseId", -1);
            if (courseId == -1) {
                Toast.makeText(this, "Invalid course ID!", Toast.LENGTH_SHORT).show();
                return;
            }

            //Toast.makeText(context, "Saving student to course: " + courseId, Toast.LENGTH_SHORT).show();

            AppDatabase.databaseWriteExecutor.execute(() -> {

                long studentId = AppDatabase.getDatabase(context).studentDao().insert(newStudent);
                Toast.makeText(context, "Saving student to course: " + studentId, Toast.LENGTH_SHORT).show();

                Enrollment enrollment = new Enrollment((int) studentId, courseId);
                AppDatabase.getDatabase(context).enrollmentDao().insert(enrollment);

                runOnUiThread(() ->
                        Toast.makeText(context, "Student added successfully!", Toast.LENGTH_SHORT).show()
                );
            });

        } else if (resultCode == RESULT_CUSTOM) {
            Toast.makeText(this, R.string.matric_not_num, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, R.string.empty_not_saved, Toast.LENGTH_LONG).show();
        }
    }
}