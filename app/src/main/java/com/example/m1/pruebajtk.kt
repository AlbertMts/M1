package com.example.m1

import android.content.Intent
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import kotlinx.android.synthetic.main.activity_pruebajtk.*
import java.util.*


class pruebajtk : AppCompatActivity() {
    var editText: EditText? = null
    var textView1: TextView? = null
    var textView2: TextView? = null //latitud
    var textView3: TextView? = null //longitud

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pruebajtk)

        //boton
        btnll.setOnClickListener {

            var lati: String = textView2?.text.toString()
            var longi: String = textView3?.text.toString()
            val intent: Intent = Intent(this, pruebaplace::class.java)
            intent.putExtra("latitu", lati)
            intent.putExtra("longitu", longi)
            startActivity(intent)
        }
        //fin boton

        //asignamos variabbles
        editText = findViewById(R.id.edit_text)
        textView1 = findViewById(R.id.text_view1)
        textView2 = findViewById(R.id.text_view2)
        textView3 = findViewById(R.id.text_view3)

        //inicializamo places
        Places.initialize(applicationContext, "AIzaSyA9r9VKV45qCWjHDC7GnRp09KvlKc23SVs")

        //edittext focusable

        editText?.setFocusable(false)

        editText?.setOnClickListener(View.OnClickListener { //inicializamos la bandera de places
            val fieldList = Arrays.asList(
                    Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME
            )
            //creamos el intent
            val intent = Autocomplete.IntentBuilder(
                    AutocompleteActivityMode.OVERLAY, fieldList
            ).build(this@pruebajtk)
            //iniciamos activityresukt
            startActivityForResult(intent, 100)
        })
    }//fin override

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            //cuando fue exitoso

            //val destinationLatLng: LatLng = place.getLatLng()
            //inicializamos place
            val place = Autocomplete.getPlaceFromIntent(data!!)
            //obtenemos la dirección en editext
            editText!!.setText(place.address)
            //obtenemos nombre de localidad
            textView1!!.text = String.format("Nombre de localidad: %s", place.name)
            //obtenemos latitud y longitud
            textView2!!.text = place.latLng?.latitude.toString() //latitude
            textView3!!.text = place.latLng?.longitude.toString() //longitude

        } else if (resultCode == AutocompleteActivity.RESULT_ERROR) { // fin if
            //cuando error
            //inicializamos estado
            val status = Autocomplete.getStatusFromIntent(data!!)
            //display toast
            Toast.makeText(applicationContext, status.statusMessage, Toast.LENGTH_SHORT).show()
        }
    }


}