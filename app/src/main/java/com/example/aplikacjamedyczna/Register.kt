package com.example.aplikacjamedyczna

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Register : AppCompatActivity() {
    private lateinit var toLoginLabel: TextView
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registration)
        supportActionBar?.hide()

        toLoginLabel = findViewById(R.id.toLoginLabel)
        toLoginLabel.setOnClickListener {
            val intentLogin = Intent(applicationContext, MainActivity::class.java)
            startActivity(intentLogin)
        }

    }
}