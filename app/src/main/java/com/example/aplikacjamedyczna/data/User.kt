package com.example.aplikacjamedyczna.data

data class User(val uid:String? = null,
                val name:String? = null,
                val surname:String? = null,
                val pesel:String? = null,
                val phone:String? = null,
                val address:String? = null,
                val visits:List<String>? = null)
