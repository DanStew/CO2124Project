package uk.edu.le.Part2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import uk.edu.le.Part2.database.Course;

public class CourseListAdapter extends ListAdapter<Course, CourseViewHolder> {

    // Interface to handle item click and long-press events
    public interface OnItemClickListener {
        void onItemClick(Course course);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(Course course);
    }

    private OnItemClickListener itemClickListener;
    private OnItemLongClickListener itemLongClickListener;

    public CourseListAdapter(@NonNull DiffUtil.ItemCallback<Course> diffCallback) {
        super(diffCallback);
    }

    // Set the click listener for the adapter
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    // Set the long-click listener for the adapter
    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.itemLongClickListener = listener;
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_course, parent, false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CourseViewHolder holder, int position) {
        Course current = getItem(position);
        holder.bind(current);

        // Handle regular click
        holder.itemView.setOnClickListener(v -> {
            if (itemClickListener != null && position != RecyclerView.NO_POSITION) {
                itemClickListener.onItemClick(current);
            }
        });

        // Handle long-click to trigger deletion
        holder.itemView.setOnLongClickListener(v -> {
            if (itemLongClickListener != null && position != RecyclerView.NO_POSITION) {
                itemLongClickListener.onItemLongClick(current);
                return true;
            }
            return false;
        });
    }

    static class CourseDiff extends DiffUtil.ItemCallback<Course> {
        @Override
        public boolean areItemsTheSame(@NonNull Course oldCourse, @NonNull Course newCourse) {
            return oldCourse.getCourseId() == newCourse.getCourseId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Course oldCourse, @NonNull Course newCourse) {
            return oldCourse.getCourseName().equals(newCourse.getCourseName())
                    && oldCourse.getCourseCode().equals(newCourse.getCourseCode())
                    && oldCourse.getLecturerName().equals(newCourse.getLecturerName());
        }
    }
}
