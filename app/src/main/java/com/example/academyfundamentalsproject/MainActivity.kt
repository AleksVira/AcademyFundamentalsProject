package com.example.academyfundamentalsproject

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.academyfundamentalsproject.databinding.ActivityMainBinding
import timber.log.Timber
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var studentForSending: Student

    companion object {
        const val STUDENT_MESSAGE = "student_message"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("MyTAG_MainActivity_onCreate(): ")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding) {
            simpleTextView.setOnClickListener { askGoogle() }
            studentButton.setOnClickListener { onButtonPressed() }
        }
    }

    private fun onButtonPressed() {
        changeStudentColor()
        binding.root.setBackgroundColor(getNewRandomColor())
        if (studentForSending != null) {
            sendMessageToNewActivity(studentForSending)
        } else {
            sendMessageToNewActivity(Student(name = "John Doe"))
        }
    }

    private fun changeStudentColor() {
        val rgb = getNewRandomColor()
        studentForSending.color = rgb
    }

    private fun getNewRandomColor(): Int {
        val r = java.util.Random().nextInt(255)
        val g = java.util.Random().nextInt(255)
        val b = java.util.Random().nextInt(255)
        val rgb = Color.rgb(r, g, b)
        return rgb
    }

    private fun sendMessageToNewActivity(student: Student) {
        Timber.d("MyTAG_MainActivity_sendMessageToNewActivity() called with: student = [$student]")
        val intent = Intent(this, StudentDetailActivity::class.java).apply {
            putExtra(STUDENT_MESSAGE, student)
        }
        startActivity(intent)
    }

    private fun askGoogle() {
        val yaQuery: String = getString(R.string.main_question)
        val uri: Uri = Uri.parse("https://yandex.ru/search/touch/?text=$yaQuery")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()

        // Именно в onStart(), чтобы каждый раз генерился новый студент
        val nameNumber = Random.nextInt(0, 10)
        val orderNumber = Random.nextInt(100, 199)
        studentForSending = Student(name = "Student $nameNumber", number = orderNumber)
        binding.studentButton.text = "${studentForSending.name} --> ${studentForSending.number}"
        Timber.d("MyTAG_MainActivity_onStart(): ")
    }

    override fun onResume() {
        super.onResume()
        Timber.d("MyTAG_MainActivity_onResume(): ")
    }

    override fun onPause() {
        super.onPause()
        Timber.d("MyTAG_MainActivity_onPause(): ")
    }

    override fun onStop() {
        super.onStop()
        Timber.d("MyTAG_MainActivity_onStop(): ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("MyTAG_MainActivity_onDestroy(): ")
    }
}