package com.qusion.roundprogressbar

import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()

        val random = Random(101)
        var progress: Int
        animate_button.setOnClickListener {
            progress = random.nextInt(0, 100)
            progressbar.progress = progress
            balance.text = "$${progress + 120f}"
        }
    }

}
