package uk.edu.le.Part2;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CourseDetailsActivity extends AppCompatActivity {

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




        CourseDetailsViewModel viewModel = new ViewModelProvider(this).get(CourseDetailsViewModel.class);

        viewModel.getCourseWithStudents(courseId).observe(this, courseWithStudents -> {
            if (courseWithStudents != null) {
                adapter.submitList(courseWithStudents.getStudents());
            }
        });

    }
}