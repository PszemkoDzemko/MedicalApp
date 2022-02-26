package com.example.aplikacjamedyczna

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainPage : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mainpage)
        supportActionBar?.hide()
    }
}