package uk.edu.le.Part2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import uk.edu.le.Part2.database.Student;

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

        editCourseCodeView = findViewById(R.id.edit_course_code);
        editCourseNameView = findViewById(R.id.edit_course_name);
        editLecturerNameView = findViewById(R.id.edit_lecturer_name);
        Button button = findViewById(R.id.button_create);

        button.setOnClickListener(v -> {
            Student student = new Student("james Arnold", "dskmldg@gmail.com", "jones");

            Intent replyIntent = new Intent();
            String courseCode = editCourseCodeView.getText().toString();
            String courseName = editCourseNameView.getText().toString();
            String lecturerName = editLecturerNameView.getText().toString();

            if (courseCode.trim().isEmpty() || courseName.trim().isEmpty() || lecturerName.trim().isEmpty()) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                replyIntent.putExtra(EXTRA_REPLY_CODE, courseCode);
                replyIntent.putExtra(EXTRA_REPLY_NAME, courseName);
                replyIntent.putExtra(EXTRA_REPLY_LECTURER, lecturerName);
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        });
    }
}
