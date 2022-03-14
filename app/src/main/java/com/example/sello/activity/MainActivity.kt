package com.example.sello.activity

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.example.sello.R
import com.google.type.Date

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val timeIdOrder = Date.
        Log.d(TAG, "onCreate xxxxxxxxxxx: $timeIdOrder")
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

    }


}


