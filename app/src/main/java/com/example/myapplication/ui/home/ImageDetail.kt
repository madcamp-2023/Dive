package com.example.myapplication.ui.home

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import eightbitlab.com.blurview.BlurView
import eightbitlab.com.blurview.RenderEffectBlur
import eightbitlab.com.blurview.RenderScriptBlur
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class ImageDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.image_detail)

        val fullScreenImage = findViewById<ImageView>(R.id.fullScreenImage)
//        val overlayImage = findViewById<ImageView>(R.id.overlayImage)
        val imageUri: Uri? = intent.getParcelableExtra("imageUri")

        fullScreenImage.scaleType = ImageView.ScaleType.CENTER_CROP
        imageUri?.let {
            fullScreenImage.setImageURI(it)
        }

        val blurView = findViewById<BlurView>(R.id.blur_view)
        val blurRadius = 6f

        val root = findViewById<ViewGroup>(R.id.image_detail_root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            blurView.setupWith(root, RenderEffectBlur())
                .setBlurRadius(blurRadius)
        }else{
            blurView.setupWith(root, RenderScriptBlur(this))
                .setBlurRadius(blurRadius)
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
