package uk.edu.le.Part2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import uk.edu.le.Part2.database.Course;
import uk.edu.le.Part2.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final int ADD_COURSE_ACTIVITY_REQUEST_CODE = 1;

    private ActivityMainBinding binding;
    private CourseViewModel courseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate started");

        // initialise view binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialise ViewModel
        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        Log.d(TAG, "ViewModel initialised ");

        // Set up RecyclerView
        RecyclerView recyclerView = findViewById(R.id.courseRecyclerView);
        final CourseListAdapter adapter = new CourseListAdapter(new CourseListAdapter.CourseDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Observe courses and update RecyclerView
        Button viewCoursesButton = findViewById(R.id.viewCoursesButton);
        recyclerView.setVisibility(View.GONE);

        viewCoursesButton.setOnClickListener(v -> {
            recyclerView.setVisibility(View.VISIBLE);
            courseViewModel.getAllItems().observe(MainActivity.this, courses -> {
                if (courses != null) {
                    adapter.submitList(courses);
                }
            });
        });

        // Item click to navigate to CourseDetailsActivity
        adapter.setOnItemClickListener(course -> {
            Intent intent = new Intent(MainActivity.this, CourseDetailsActivity.class);
            intent.putExtra("courseId", course.getCourseId());
            startActivity(intent);
        });

        // Item long click to show delete confirmation
        adapter.setOnItemLongClickListener(course -> showDeleteConfirmationDialog(course));

        // Set up FloatingActionButton to add new courses
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, CreateCourseActivity.class);
            startActivityForResult(intent, ADD_COURSE_ACTIVITY_REQUEST_CODE);
        });
    }

    private void showDeleteConfirmationDialog(Course course) {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to delete this course?")
                .setPositiveButton("Yes", (dialog, which) -> deleteCourse(course))
                .setNegativeButton("No", null)
                .show();
    }

    private void deleteCourse(Course course) {
        courseViewModel.delete(course);
        Toast.makeText(this, "Course deleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_COURSE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            String courseCode = data.getStringExtra(CreateCourseActivity.EXTRA_REPLY_CODE);
            String courseName = data.getStringExtra(CreateCourseActivity.EXTRA_REPLY_NAME);
            String lecturerName = data.getStringExtra(CreateCourseActivity.EXTRA_REPLY_LECTURER);

            Course newCourse = new Course(courseCode, courseName, lecturerName);
            courseViewModel.insert(newCourse);
        } else {
            Toast.makeText(this, R.string.empty_not_saved, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
