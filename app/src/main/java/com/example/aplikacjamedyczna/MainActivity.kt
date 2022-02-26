package com.example.aplikacjamedyczna

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
    }
    val registerToLabel = findViewById<TextView>(R.id.toRegisterLabel)
    val registerToLfsdfabel = findViewById<TextView>(R.id.toRegisterLabel)

}