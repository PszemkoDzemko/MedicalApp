package com.example.aplikacjamedyczna

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.aplikacjamedyczna.doctor.Doctor
import com.example.aplikacjamedyczna.user.User

private const val DATABASE_NAME = "Med"
private const val DATABASE_VERSION = 2
private const val TABLE_USER = "users"
private const val TABLE_DOCTOR = "doctors"
private const val COLUMN_USER_ID = "id"


class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    private val CREATE_USER_TABEL =("CREATE TABLE \"users\" (\n" +
            "\t\"id\"\tINTEGER,\n" +
            "\t\"name\"\tTEXT,\n" +
            "\t\"surname\"\tTEXT,\n" +
            "\t\"email\"\tTEXT,\n" +
            "\t\"phone\"\tTEXT,\n" +
            "\t\"password\"\tTEXT,\n" +
            "\tPRIMARY KEY(\"id\" AUTOINCREMENT)\n" +
            ");")

    private val CREATE_DOCTOR_TABLE =("CREATE TABLE \"doctors\" (\n" +
            "\t\"id\"\tINTEGER,\n" +
            "\t\"name\"\tTEXT,\n" +
            "\t\"surname\"\tTEXT,\n" +
            "\t\"email\"\tTEXT,\n" +
            "\t\"specialization\"\tTEXT,\n" +
            "\t\"password\"\tTEXT,\n" +
            "\tPRIMARY KEY(\"id\" AUTOINCREMENT)\n" +
            ");")

    private val DROP_USER_TABLE = "DROP TABLE IF EXISTS $TABLE_USER"
    private val DROP_DOCTOR_TABLE = "DROP TABLE IF EXISTS $TABLE_DOCTOR"

    override fun onCreate(p0: SQLiteDatabase) {
        p0.execSQL(CREATE_USER_TABEL)
        p0.execSQL(CREATE_DOCTOR_TABLE)
    }

    override fun onUpgrade(p0: SQLiteDatabase, p1: Int, p2: Int) {
        p0.execSQL(DROP_USER_TABLE)
        p0.execSQL(DROP_DOCTOR_TABLE)
        onCreate(p0)
    }
    fun addUser(user: User){
        val p0 =this.writableDatabase
        val values = ContentValues()
        values.put("name",user.name)
        values.put("surname",user.surname)
        values.put("email",user.email)
        values.put("phone",user.phone)
        values.put("password",user.password)

        p0.insert(TABLE_USER,null,values)
        //p0.close() Odkomentuj to później
    }
    fun checkUser(email:String,password:String):Boolean{
        val columns = arrayOf(COLUMN_USER_ID)
        val p0 = this.readableDatabase
        val selection = "email = ? AND password = ?"
        val selectionArgs = arrayOf(email,password)
        val cursor = p0.query(TABLE_USER,columns, selection,selectionArgs,null,null,null)
        val cursorCount = cursor.count
        cursor.close()
        //p0.close() Odkomentuj to później
        if (cursorCount > 0) return true

        return false
    }

    fun addDoctor(doctor: Doctor){
        val p0 = this.writableDatabase
        val values = ContentValues()
        values.put("name",doctor.name)
        values.put("surname",doctor.surname)
        values.put("email",doctor.email)
        values.put("specialization",doctor.specialization)
        values.put("password",doctor.password)
        p0.insert(TABLE_DOCTOR,null,values)
        //p0.close() Odkomentuj to później
    }

    fun checkDoctor(email:String,password:String):Boolean {
        val columns = arrayOf(COLUMN_USER_ID)
        val p0 = this.readableDatabase
        val selection = "email = ? AND password = ?"
        val selectionArgs = arrayOf(email,password)
        val cursor = p0.query(TABLE_DOCTOR,columns, selection,selectionArgs,null,null,null)
        val cursorCount = cursor.count
//      Log.e(email,"$cursorCount")
        cursor.close()
        //p0.close() Odkomentuj to później
        if(cursorCount > 0) return true

        return false
    }

    fun readAllDoctors(): Cursor {
        val query = "SELECT * FROM $TABLE_DOCTOR"
        val p0 = this.readableDatabase
        return p0.rawQuery(query, null)
    }

}