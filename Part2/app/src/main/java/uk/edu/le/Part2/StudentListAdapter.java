package uk.edu.le.Part2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import uk.edu.le.Part2.database.Student;

public class StudentListAdapter extends ListAdapter<Student, StudentListAdapter.StudentViewHolder> {

    public StudentListAdapter(@NonNull DiffUtil.ItemCallback<Student> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_student, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student current = getItem(position);
        holder.bind(current);
    }

    static class StudentViewHolder extends RecyclerView.ViewHolder {
        private final TextView studentNameTextView;
        private final TextView studentEmailTextView;
        private final TextView studentIdTextView;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            studentNameTextView = itemView.findViewById(R.id.studentNameTextView);
            studentEmailTextView = itemView.findViewById(R.id.studentEmailTextView);
            studentIdTextView = itemView.findViewById(R.id.studentStudentIdTextView);
        }

        public void bind(Student student) {
            studentNameTextView.setText("Name: " + student.getFullName());
            studentEmailTextView.setText("Email: "  + student.getEmail());
            studentIdTextView.setText("Matric Number: " + String.valueOf(  student.getStudentId()));
        }
    }

    static class StudentDiff extends DiffUtil.ItemCallback<Student> {

        @Override
        public boolean areItemsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
            return oldItem.getStudentId() == newItem.getStudentId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
            return oldItem.getFullName().equals(newItem.getFullName()) &&
                    oldItem.getEmail().equals(newItem.getEmail());
        }
    }
}
