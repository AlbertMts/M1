package com.example.m1

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_pruebaplace.*

class pruebaplace : AppCompatActivity() , OnMapReadyCallback {
    //variables globales para el mapa latitud y longitud
    private lateinit var map:GoogleMap
    var   lattittu: String? = null
    var longgittu: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pruebaplace)

        val objetoIntent: Intent= intent
         lattittu = objetoIntent.getStringExtra("latitu")
        vistall.text = "$lattittu"
         longgittu = objetoIntent.getStringExtra("longitu")
        vislat.text = "$longgittu"
        lattittu?.toDouble()
        longgittu?.toDouble()

        //crear metodo fragment
        createFrament()


    }//fin fun oncreate

    private fun createFrament() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }//fin de metodo createfragmnet

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
       //createMarker()
        val disney = LatLng(
                lattittu?.toDouble()!!, longgittu?.toDoubleOrNull()!!
        )
        val marker = MarkerOptions().position( disney  ).title("Tu ubicación actual")
        map.addMarker(marker )
        map.animateCamera(
                CameraUpdateFactory.newLatLngZoom(disney, 18f), 6000, null
        )


    }//fin metodo onmap ready

    private fun LatLng(lattittu: String?, longgittu: String?): LatLng {
        return LatLng(lattittu, longgittu)
    }


}

