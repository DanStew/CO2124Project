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

public class CourseViewHolder extends RecyclerView.ViewHolder {
    private final TextView courseCodeTextView;
    private final TextView courseNameTextView;
    private final TextView lecturerNameTextView;
    private final Context context; // Add Context

    public CourseViewHolder(@NonNull View itemView) {
        super(itemView);
        courseCodeTextView = itemView.findViewById(R.id.courseCodeTextView);
        courseNameTextView = itemView.findViewById(R.id.courseNameTextView);
        lecturerNameTextView = itemView.findViewById(R.id.lecturerNameTextView);
        context = itemView.getContext(); // Initialise Context

        itemView.setOnClickListener(v -> {
            Course currentCourse = (Course) itemView.getTag();
            if (currentCourse != null) {
                Intent intent = new Intent(context, CourseDetailsActivity.class);
                intent.putExtra("courseCode", currentCourse.getCourseCode());
                intent.putExtra("courseName", currentCourse.getCourseName());
                intent.putExtra("lecturerName", currentCourse.getLecturerName());
                Log.d("CourseViewHolder", "11: " + currentCourse.getCourseName());
                context.startActivity(intent);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void bind(Course course) {
        if (course != null) {
            courseCodeTextView.setText("Code: " + course.getCourseCode());
            courseNameTextView.setText("Name: " + course.getCourseName());
            lecturerNameTextView.setText("Lecturer: " + course.getLecturerName());
            itemView.setTag(course);
            Log.d("CourseViewHolder", "Binding course: " + course.getCourseName());
        } else {
            Log.e("CourseViewHolder", "Course object is null!");
        }
    }

    static CourseViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_course, parent, false);
        return new CourseViewHolder(view);
    }
}