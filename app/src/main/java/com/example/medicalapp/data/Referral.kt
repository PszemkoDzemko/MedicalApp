package com.example.medicalapp.data

data class Referral(
    val id:String? = null,
    val data:String?=null,
    val id_pac:String?=null,
    val id_doc:String?=null,
    val info:String?=null,
    val reason:String?=null,
    val done:Boolean?=false)