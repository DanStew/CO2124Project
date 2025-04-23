package uk.edu.le.Part2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CreateCourseActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY_CODE = "uk.edu.le.Part2.REPLY_CODE";
    public static final String EXTRA_REPLY_NAME = "uk.edu.le.Part2.REPLY_NAME";
    public static final String EXTRA_REPLY_LECTURER = "uk.edu.le.Part2.REPLY_LECTURER";

    private EditText editCourseCodeView;
    private EditText editCourseNameView;
    private EditText editLecturerNameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_course);

        // Link UI fields
        editCourseCodeView = findViewById(R.id.edit_course_code);
        editCourseNameView = findViewById(R.id.edit_course_name);
        editLecturerNameView = findViewById(R.id.edit_lecturer_name);
        Button button = findViewById(R.id.button_create);

        // Handle "Create" button click
        button.setOnClickListener(v -> {
            Intent replyIntent = new Intent();

            // Extract and trim input values
            String courseCode = editCourseCodeView.getText().toString().trim();
            String courseName = editCourseNameView.getText().toString().trim();
            String lecturerName = editLecturerNameView.getText().toString().trim();

            // Basic validation for empty fields
            if (courseCode.isEmpty() || courseName.isEmpty() || lecturerName.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
                setResult(RESULT_CANCELED, replyIntent);
                finish();
                return;
            }


            replyIntent.putExtra(EXTRA_REPLY_CODE, courseCode);
            replyIntent.putExtra(EXTRA_REPLY_NAME, courseName);
            replyIntent.putExtra(EXTRA_REPLY_LECTURER, lecturerName);
            setResult(RESULT_OK, replyIntent);
            finish();
        });
    }
}
