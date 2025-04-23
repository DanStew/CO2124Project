package uk.edu.le.Part2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import uk.edu.le.Part2.database.AppDatabase;
import uk.edu.le.Part2.database.Student;

public class EditStudentActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY_ID = "uk.edu.le.Part2.REPLY_ID";
    public static final String EXTRA_REPLY_NAME = "uk.edu.le.Part2.REPLY_NAME";
    public static final String EXTRA_REPLY_EMAIL = "uk.edu.le.Part2.REPLY_EMAIL";
    public static final String EXTRA_REPLY_MATRIC = "uk.edu.le.Part2.REPLY_MATRIC";
    public static final int RESULT_CUSTOM = 1001;

    private EditText editStudentNameView;
    private EditText editStudentEmailView;
    private EditText editStudentMatricView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student);

        // Get views
        editStudentNameView = findViewById(R.id.edit_student_name);
        editStudentEmailView = findViewById(R.id.edit_student_email);
        editStudentMatricView = findViewById(R.id.edit_student_matric);
        Button button = findViewById(R.id.button_edit);

        // Get data from intent
        int studentId = getIntent().getIntExtra("studentId", -1);
        String studentName = getIntent().getStringExtra("studentName");
        String studentEmail = getIntent().getStringExtra("studentEmail");
        int studentMatric = getIntent().getIntExtra("studentMatric", -1);

        // Populate fields if values exist
        if (studentName != null && !studentName.isEmpty()) {
            editStudentNameView.setText(studentName);
        }
        if (studentEmail != null && !studentEmail.isEmpty()) {
            editStudentEmailView.setText(studentEmail);
        }
        if (studentMatric != -1) {
            editStudentMatricView.setText(String.valueOf(studentMatric));
        }

        // Submit button logic
        button.setOnClickListener(view -> {
            Intent replyIntent = new Intent();

            String name = editStudentNameView.getText().toString().trim();
            String email = editStudentEmailView.getText().toString().trim();
            String matricInput = editStudentMatricView.getText().toString().trim();

            // Input validation
            if (name.isEmpty() || email.isEmpty() || matricInput.isEmpty()) {
                Toast.makeText(this, "Please fill out all fields.", Toast.LENGTH_SHORT).show();
                setResult(RESULT_CANCELED, replyIntent);
                finish();
                return;
            }

            // Email format validation
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Invalid email address.", Toast.LENGTH_SHORT).show();
                setResult(RESULT_CANCELED, replyIntent);
                finish();
                return;
            }

            //Ensuring the matric number is an integer
            int matric;
            try {
                matric = Integer.parseInt(matricInput);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Matric number must be numeric.", Toast.LENGTH_SHORT).show();
                setResult(RESULT_CUSTOM, replyIntent);
                finish();
                return;
            }

            // Check if matric number already exists in the database
            int finalMatric = matric;
            AppDatabase.databaseWriteExecutor.execute(() -> {
                //Checking if the matric number exists in the database
                if (AppDatabase.getDatabase(EditStudentActivity.this).studentDao().checkMatricExists(String.valueOf(finalMatric))) {
                    //If the matric number hasn't changed, allow it. Else, reject it
                    Student student = AppDatabase.getDatabase(EditStudentActivity.this).studentDao().getStudentByMatricNumber(String.valueOf(finalMatric));
                    if (student.getStudentId() != studentId) {
                        runOnUiThread(() -> {
                            Toast.makeText(EditStudentActivity.this, "Matric number already exists, choose another.", Toast.LENGTH_SHORT).show();
                        });
                        return;
                    }
                }

                // If matric number is valid and doesn't already exist, proceed with updating student data
                replyIntent.putExtra(EXTRA_REPLY_ID, studentId);
                replyIntent.putExtra(EXTRA_REPLY_NAME, name);
                replyIntent.putExtra(EXTRA_REPLY_EMAIL, email);
                replyIntent.putExtra(EXTRA_REPLY_MATRIC, matric);

                setResult(RESULT_OK, replyIntent);
                finish();
            });
        });
    }
}
