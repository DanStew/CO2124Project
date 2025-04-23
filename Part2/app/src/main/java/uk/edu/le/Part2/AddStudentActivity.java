package uk.edu.le.Part2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import uk.edu.le.Part2.database.Student;

public class AddStudentActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_add_student);
        //getting the user input
        editStudentNameView = findViewById(R.id.edit_student_name);
        editStudentEmailView = findViewById(R.id.edit_student_email);
        editStudentMatricView = findViewById(R.id.edit_student_matric);
        Button addStudents = findViewById(R.id.button_add);

        addStudents.setOnClickListener(view -> {

            Intent replyIntent = new Intent();
            String studentName = editStudentNameView.getText().toString().trim();
            String studentEmail = editStudentEmailView.getText().toString().trim();
            String studentMatric = editStudentMatricView.getText().toString().trim();

            int matric = -1;
            if (!studentMatric.isEmpty()) {
                try {
                    matric = Integer.parseInt(studentMatric);
                } catch (NumberFormatException e) {
                    Log.e("AddStudentActivity" ,"Matric is not an integer" );
                    matricCheck = true;
                }
            }

            if (studentName.isEmpty() || studentEmail.isEmpty() || studentMatric.isEmpty()) {
                setResult(RESULT_CANCELED, replyIntent);
            } else if (matricCheck) {
                setResult(RESULT_CUSTOM, replyIntent);
            } else {
                replyIntent.putExtra(EXTRA_REPLY_NAME, studentName);
                replyIntent.putExtra(EXTRA_REPLY_EMAIL, studentEmail);
                replyIntent.putExtra(EXTRA_REPLY_MATRIC, studentMatric);
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        });
    }
}
