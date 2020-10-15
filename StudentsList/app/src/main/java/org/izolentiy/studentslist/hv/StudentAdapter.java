package org.izolentiy.studentslist.hv;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private final List<Student> students;
    private final Listener onStudentClickListener;

    public StudentAdapter(List<Student> students, Listener onStudentClickListener) {
        this.students = students;
        this.onStudentClickListener = onStudentClickListener;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_item, parent, false);
        view.setOnClickListener(v -> onStudentClickListener.onStudentClick((Student) v.getTag()));
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student = students.get(position);
        holder.bind(student);
        holder.itemView.setTag(student);
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    static final class StudentViewHolder extends RecyclerView.ViewHolder {

        private final TextView firstName;
        private final TextView lastName;
        private final ImageView photo;

        private StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            firstName = itemView.findViewById(R.id.student_item__fname);
            lastName = itemView.findViewById(R.id.student_item__lname);
            photo = itemView.findViewById(R.id.student_item__avatar);
        }

        private void bind(@NonNull Student student) {
            firstName.setText(student.getFirstName());
            lastName.setText(student.getLastName());
            photo.setImageResource(student.getPhoto());
        }

    }

    interface Listener {
        void onStudentClick(Student student);
    }

}
