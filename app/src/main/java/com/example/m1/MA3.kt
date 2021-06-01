package com.example.m1

import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.VoicemailContract
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import kotlinx.android.synthetic.main.activity_m_a2.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_m_a3.*
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode


class MA3 : AppCompatActivity() {
    private val request_camera = 1002
    var foto: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_m_a3)

        abrecamara_click()

        val AUTOCOMPLETE_REQUEST_CODE = 1

        // Set the fields to specify which types of place data to
        // return after the user has made a selection.
        val fields = listOf(Place.Field.ID, Place.Field.NAME)

        // Start the autocomplete intent.
        val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
            .build(this)
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)

    }

    private fun abrecamara_click (){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ){
            if (checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED || checkSelfPermission(
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED ) {
                val permisosCamara = arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE )
                requestPermissions(permisosCamara, request_camera)
            }else{
                abrecamara()
            }
        }else {
            abrecamara()
        }
    }//fin función que abre camara_click

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when( requestCode) {
            request_camera -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    abrecamara()
                else
                    Toast.makeText(applicationContext, "No puedes acceder a la camara", Toast.LENGTH_SHORT).show()
            }//fin request gallery

        }//fin when request code
    }

    private fun abrecamara (){
        val value = ContentValues()
        value.put(MediaStore.Images.Media.TITLE, "Imagen de la alerta"  )
        foto = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, value  )
        val camaraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE  )
        camaraIntent.putExtra(MediaStore.EXTRA_OUTPUT, foto  )
        startActivityForResult(camaraIntent, request_camera     )
    }//fin funcion abrecamara

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == request_camera){
            fotoev.setImageURI(foto)
        }//fin if resultcode
    }//fin onactivityresul

}