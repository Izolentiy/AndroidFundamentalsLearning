package org.izolentiy.studentslist.hv;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private List<Student> students;
    private StudentAdapter studentAdapter;
    private final Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        students = generateStudents();
        setContentView(R.layout.activity_main);
        setupRecyclerView();
        setupElements();
    }

    private void setupRecyclerView() {
        RecyclerView studentsList = findViewById(R.id.activity_main__rvlist);
        studentAdapter = new StudentAdapter(students, this::onStudentClick);
        studentsList.setAdapter(studentAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        studentsList.setLayoutManager(linearLayoutManager);
    }

    private void setupElements() {
        Button deleteBtn = findViewById(R.id.activity_main__deletebtn);
        Button saveBtn = findViewById(R.id.activity_main__savebtn);
        Button addBtn = findViewById(R.id.activity_main__add_student);
        CheckBox checkBox = findViewById(R.id.activity_main__ismale);
        TextView header = findViewById(R.id.activity_main__header);
        TextView firstName = findViewById(R.id.activity_main__fname);
        TextView lastName = findViewById(R.id.activity_main__lname);

        header.setText("Студент");
        deleteBtn.setText("Удалить");
        saveBtn.setText("Сохранить");
        addBtn.setText("Добавить");
        checkBox.setText("Мужской пол");
        checkBox.setClickable(false);

        header.setVisibility(View.GONE);
        deleteBtn.setVisibility(View.GONE);
        saveBtn.setVisibility(View.GONE);
        checkBox.setVisibility(View.GONE);
        firstName.setVisibility(View.GONE);
        lastName.setVisibility(View.GONE);

    }

    private void onStudentClick(Student student) {
        // Код выполняемый при нажатии на студента из списка
        TextView firstName = findViewById(R.id.activity_main__fname);
        TextView lastName= findViewById(R.id.activity_main__lname);
        ImageView photo = findViewById(R.id.activity_main__photo);
        CheckBox isMale = findViewById(R.id.activity_main__ismale);
        Button addBtn = findViewById(R.id.activity_main__add_student);
        Button saveBtn = findViewById(R.id.activity_main__savebtn);
        Button deleteBtn = findViewById(R.id.activity_main__deletebtn);

        firstName.setText(student.getFirstName());
        lastName.setText(student.getLastName());
        photo.setImageResource(student.getPhoto());
        isMale.setChecked(student.isMale());

        TextView header = findViewById(R.id.activity_main__header);

        addBtn.setVisibility(View.GONE);
        header.setVisibility(View.VISIBLE);
        firstName.setVisibility(View.VISIBLE);
        lastName.setVisibility(View.VISIBLE);
        isMale.setVisibility(View.VISIBLE);
        deleteBtn.setVisibility(View.VISIBLE);
        saveBtn.setVisibility(View.VISIBLE);
    }

    private void onAddClick() {

    }

    private void onSaveClick() {

    }

    private List<Student> generateStudents() {
        List<Student> students = new ArrayList<>();
        students.add(new Student("Игорь", "Петров", true, R.drawable.male_1));
        students.add(new Student("Виталий", "Озёров", true, R.drawable.male_2));
        students.add(new Student("Андрей", "Овечкин", true, R.drawable.male_3));
        students.add(new Student("Екатерина", "Совельева", false, R.drawable.female_1));
        students.add(new Student("Зарина", "Расова", false, R.drawable.female_2));
        students.add(new Student("Анастасия", "Шубина", false, R.drawable.female_3));
        return students;
    }

}
