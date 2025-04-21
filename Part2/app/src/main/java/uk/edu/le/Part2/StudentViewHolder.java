package uk.edu.le.Part2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import uk.edu.le.Part2.database.Course;
import uk.edu.le.Part2.database.Student;

public class StudentViewHolder extends RecyclerView.ViewHolder {
    private final Context context;

    public StudentViewHolder(@NonNull View itemView) {
        super(itemView);
        context = itemView.getContext(); // Initialise Context

        itemView.setOnClickListener(v -> {
            Student currentStudent = (Student) itemView.getTag();
            if (currentStudent != null) {
                Intent intent = new Intent(context, StudentDetailsActivity.class);
                Log.d("CourseViewHolder", "11: " + currentStudent.getFullName());
                context.startActivity(intent);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void bind(Student student) {
        if (student != null) {
            TextView studentNameTextView = itemView.findViewById(R.id.studentNameTextView);
            studentNameTextView.setText("Name: " + student.getFullName());
            itemView.setTag(student);

            Log.d("StudentViewHolder", "Binding student: " + student.getFullName());
        } else {
            Log.e("StudentViewHolder", "Student object is null!");
        }
    }

    static StudentViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_student, parent, false);
        return new StudentViewHolder(view);
    }

}
