package com.example.m1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnCamara.setOnClickListener(){
         startActivity(Intent(this, MA2::class.java) )
              Toast.makeText(this, "Alerta Generada", Toast.LENGTH_SHORT ).show()
       }//fiin btncamera

    }//fin oncreate

}//fin main activity