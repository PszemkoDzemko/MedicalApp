package com.example.aplikacjamedyczna.User

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.aplikacjamedyczna.R

class UserMainPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mainpage)
        supportActionBar?.hide()
    }
}