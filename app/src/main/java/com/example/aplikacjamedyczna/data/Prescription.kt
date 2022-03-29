package com.example.aplikacjamedyczna.data

data class Prescription(
    val id:String?=null,
    val code:String?=null,
    val details:String?=null,
    val done:Boolean = false,
    val id_pac:String?=null,
    val id_doc:String?=null,
    val data:String?=null
)
