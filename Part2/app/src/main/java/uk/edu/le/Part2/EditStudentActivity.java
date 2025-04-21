package uk.edu.le.Part2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EditStudentActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY_ID = "uk.edu.le.Part2.REPLY_ID";
    public static final String EXTRA_REPLY_NAME = "uk.edu.le.Part2.REPLY_NAME";
    public static final String EXTRA_REPLY_EMAIL = "uk.edu.le.Part2.REPLY_EMAIL";
    public static final String EXTRA_REPLY_MATRIC = "uk.edu.le.Part2.REPLY_MATRIC";
    public static final int RESULT_CUSTOM = 1001;
    private EditText editStudentNameView;
    private EditText editStudentEmailView;
    private EditText editStudentMatricView;
    private boolean matricCheck = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student);

        int studentId = getIntent().getIntExtra("studentId", -1);
        String studentName = getIntent().getStringExtra("studentName");
        String studentEmail = getIntent().getStringExtra("studentEmail");
        int studentMatric = getIntent().getIntExtra("studentMatric", -1);

        editStudentNameView = findViewById(R.id.edit_student_name);
        editStudentEmailView = findViewById(R.id.edit_student_email);
        editStudentMatricView = findViewById(R.id.edit_student_matric);
        Button button = findViewById(R.id.button_edit);

        if (studentName != null) {
            editStudentNameView.setText(studentName);
        }

        if (studentEmail != null) {
            editStudentEmailView.setText(studentEmail);
        }

        if (studentMatric != -1) {
            editStudentMatricView.setText(String.valueOf(studentMatric));
        }

        button.setOnClickListener(view -> {

            Intent replyIntent = new Intent();
            String editStudentName = editStudentNameView.getText().toString().trim();
            String editStudentEmail = editStudentEmailView.getText().toString().trim();
            String editStudentMatric = editStudentMatricView.getText().toString().trim();

            int matric = -1;
            if (!editStudentMatric.isEmpty()) {
                try {
                    matric = Integer.parseInt(editStudentMatric);
                } catch (NumberFormatException e) {
                    matricCheck = true;
                }
            }

            if (editStudentName.isEmpty() || editStudentEmail.isEmpty() || editStudentMatric.isEmpty()) {
                setResult(RESULT_CANCELED, replyIntent);
            } else if (matricCheck) {
                setResult(RESULT_CUSTOM, replyIntent);
            } else {
                replyIntent.putExtra(EXTRA_REPLY_ID, studentId);
                replyIntent.putExtra(EXTRA_REPLY_NAME, editStudentName);
                replyIntent.putExtra(EXTRA_REPLY_EMAIL, editStudentEmail);
                replyIntent.putExtra(EXTRA_REPLY_MATRIC, Integer.parseInt(editStudentMatric));
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        });
    }

}
