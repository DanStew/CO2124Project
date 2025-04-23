package uk.edu.le.Part2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import uk.edu.le.Part2.database.AppDatabase;
import uk.edu.le.Part2.database.Course;
import uk.edu.le.Part2.database.Student;
import uk.edu.le.Part2.database.Enrollment;
import uk.edu.le.Part2.database.StudentWithCourses;

public class CourseDetailsActivity extends AppCompatActivity {

    public static final int ADD_STUDENT_ACTIVITY_REQUEST_CODE = 1;
    public static final int RESULT_CUSTOM = 1001;
    private StudentViewModel studentViewModel;
    private Context context = this;
    private TextView courseCodeTextView;
    private TextView courseNameTextView;
    private TextView lecturerNameTextView;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

         toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Added code - Initialise TextViews
        courseCodeTextView = findViewById(R.id.courseCodeTextView);
        courseNameTextView = findViewById(R.id.courseNameTextView);
        lecturerNameTextView = findViewById(R.id.lecturerNameTextView);

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

        //Observe Course details and populate TextViews and Toolbar title
        viewModel.getCourseWithStudents(courseId).observe(this, course -> {
            if (course != null) {
                courseCodeTextView.setText("Course Code: " + course.getCourse().getCourseCode());
                courseNameTextView.setText("Course Name: " + course.getCourse().getCourseName());
                lecturerNameTextView.setText("Lecturer Name: " + course.getCourse().getLecturerName());
                toolbar.setTitle(course.getCourse().getCourseName());
            } else {
                Toast.makeText(this, "Course details not found.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


        viewModel.getCourseWithStudents(courseId).observe(this, courseWithStudents -> {
            if (courseWithStudents != null && courseWithStudents.getStudents() != null) {
                adapter.submitList(courseWithStudents.getStudents());
            } else {
                Toast.makeText(this, "No students found or course is empty.", Toast.LENGTH_SHORT).show();
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
            if (data == null) {
                Toast.makeText(this, "No data returned!", Toast.LENGTH_SHORT).show();
                return;
            }

            String studentName = data.getStringExtra(AddStudentActivity.EXTRA_REPLY_NAME);
            String studentEmail = data.getStringExtra(AddStudentActivity.EXTRA_REPLY_EMAIL);
            String studentMatric = data.getStringExtra(AddStudentActivity.EXTRA_REPLY_MATRIC);

            // Validate input before creating Student object
            if (studentName == null || studentEmail == null || studentMatric == null || studentMatric.isEmpty()) {
                Toast.makeText(this, "Incomplete student data! " + studentMatric, Toast.LENGTH_SHORT).show();
                return;
            }

            // Validate email format using regex
            String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}";
            if (!studentEmail.matches(emailPattern)) {
                Toast.makeText(this, "Invalid email format!", Toast.LENGTH_SHORT).show();
                return;
            }

            int matricNum;
            try {
                matricNum = Integer.parseInt(studentMatric);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Matric number must be a valid number!", Toast.LENGTH_SHORT).show();
                return;
            }

            Student newStudent = new Student();
            newStudent.setFullName(studentName);
            newStudent.setEmail(studentEmail);

            //student username is the first 3 letters of their full name and there matric num combined.
            String firstThree = studentName.trim().length() >= 3
                    ? studentName.trim().substring(0, 3).toLowerCase()
                    : studentName.trim().toLowerCase();

            newStudent.setUsername(firstThree + studentMatric);
            newStudent.setMatricNum(matricNum);

            int courseId = getIntent().getIntExtra("courseId", -1);
            if (courseId == -1) {
                Toast.makeText(this, "Invalid course ID!", Toast.LENGTH_SHORT).show();
                return;
            }


            AppDatabase.databaseWriteExecutor.execute(() -> {
                //Boolean variable to confirm whether the student already exists or not
                boolean studentAlreadyExists = false;
                try {
                    if(AppDatabase.getDatabase(context).studentDao().checkMatricExists(studentMatric.trim())){
                        //Checking if the relationship between course and student already exists, if so, reject it
                        StudentWithCourses studentsCourses = AppDatabase.getDatabase(context).studentDao().getStudentWithCoursesByMatricNum(studentMatric.trim());
                        //If the student is enrolled on a course
                        if (!(studentsCourses == null)){
                            //Looping through all the students enrolled courses
                            List<Course> courses = studentsCourses.getCourses();
                            for (Course course : courses){
                                //If they are already enrolled on the course, reject it
                                if (course.getCourseId() == courseId){
                                    runOnUiThread(() ->
                                            Toast.makeText(context, "Student is already enrolled on the course, please try again", Toast.LENGTH_SHORT).show()
                                    );
                                    return;
                                }
                            }
                        }
                        //Otherwise, checking that the students information is the same as previous
                        Student student = AppDatabase.getDatabase(context).studentDao().getStudentByMatricNumber(studentMatric.trim());
                        //If the users credentials have changed, reject it
                        System.out.println(student);
                        System.out.println(studentName);
                        System.out.println(studentEmail);
                        System.out.println(student.getFullName());
                        System.out.println(student.getEmail());
                        System.out.println(student.getFullName() != studentName);
                        System.out.println(student.getFullName() != studentName.trim());
                        if (!student.getFullName().equals(studentName) || !student.getEmail().equals(studentEmail)){
                            runOnUiThread(() ->
                                    Toast.makeText(context, "Another student with matric number already exists, please try again", Toast.LENGTH_SHORT).show()
                            );
                            return;
                        }
                        studentAlreadyExists = true;
                    }
                    runOnUiThread(() ->{
                        Toast.makeText(context, "Saving student to course", Toast.LENGTH_SHORT).show();


                    });

                    //Initialising the student id
                    long studentId;
                    //If the student doesn't already exist, add it to the database and get the id
                    if(!studentAlreadyExists){
                        studentId = AppDatabase.getDatabase(context).studentDao().insert(newStudent);
                    }
                    //Otherwise, get the student id through the students matric number
                    else{
                        studentId = AppDatabase.getDatabase(context).studentDao().getStudentIdByMatricNumber(studentMatric.trim());
                    }

                    Enrollment enrollment = new Enrollment((int) studentId, courseId);
                    AppDatabase.getDatabase(context).enrollmentDao().insert(enrollment);

                    runOnUiThread(() ->
                            Toast.makeText(context, "Student added successfully!", Toast.LENGTH_SHORT).show()
                    );

                } catch (Exception e) {
                    Log.e("CourseDetailsActivity", "DB error while adding student", e);
                    runOnUiThread(() ->
                            Toast.makeText(context, "Failed to add student. Please try again.", Toast.LENGTH_SHORT).show()
                    );
                }
            });


        } else if (resultCode == RESULT_CUSTOM) {
            Toast.makeText(this, R.string.matric_not_num, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, R.string.empty_not_saved, Toast.LENGTH_LONG).show();
        }
    }
}