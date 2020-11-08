package com.example.academyfundamentalsproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.academyfundamentalsproject.MainActivity.Companion.STUDENT_MESSAGE
import com.example.academyfundamentalsproject.databinding.ActivityStudentDetailBinding

class StudentDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStudentDetailBinding
    private lateinit var perfectStudent: Student


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.getParcelableExtra<Student>(STUDENT_MESSAGE).also {
            if (it != null) {
                perfectStudent = it
            } else {
                perfectStudent = Student(name = "Empty Student")
            }
        }
        val newMessage: String =  "Message: ${perfectStudent.number}"
        binding.studentInfo.text = newMessage
        binding.root.setBackgroundColor(perfectStudent.color)
    }
}