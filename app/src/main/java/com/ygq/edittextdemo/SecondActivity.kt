package com.ygq.edittextdemo

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class SecondActivity :AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transit_record)
    }

    fun leftBack(view: View) {
        startActivity(Intent(this,MainActivity::class.java))
    }
}