package com.example.myapplication.ui.home

import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class Item : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.display_image)

        val imageView = findViewById<ImageView>(R.id.image)
        val imageUri: Uri? = intent.getParcelableExtra("imageUri")

        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        imageUri?.let {
            imageView.setImageURI(it)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val formattedDate = getFormattedDate()

        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
                val toolbarText = findViewById<TextView>(R.id.textView)

                if (toolbarText.visibility == View.VISIBLE) {
                    toolbarText.visibility = View.INVISIBLE
                } else {
                    toolbarText.visibility = View.VISIBLE
                    toolbarText.text = formattedDate

                    Handler(Looper.getMainLooper()).postDelayed({
                        toolbarText.visibility = View.INVISIBLE
                    }, 3000)
                }
            }
        }

        return super.onTouchEvent(event)
    }

    /**
     * return formattedDate: "EEEE a hh:mm"
     */
    private fun getFormattedDate(): String {
        val inputDateString = intent.getStringExtra("imageDate") ?: "2023:12:30 01:54:56"
        val inputFormat = SimpleDateFormat("yyyy:MM:dd HH:mm:ss", Locale.getDefault())
        inputFormat.timeZone = TimeZone.getDefault()
        val outputFormat = SimpleDateFormat("EEEE a yyyy:MM:dd hh:mm", Locale.getDefault())
        outputFormat.timeZone = TimeZone.getDefault()
        val date: Date? = inputFormat.parse(inputDateString)

        return if (date != null) outputFormat.format(date) else "날짜 파싱 오류"
    }
}
