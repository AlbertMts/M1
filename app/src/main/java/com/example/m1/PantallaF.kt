package com.example.m1

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_pantalla_f.*
import kotlinx.android.synthetic.main.activity_pruebaplace.*
import java.util.jar.Manifest
import kotlinx.android.synthetic.main.activity_pantalla_f.vislat as vislat1
import kotlinx.android.synthetic.main.activity_pruebaplace.vistall as vistall1

class PantallaF : AppCompatActivity(), OnMapReadyCallback {
    //variables globales para el mapa latitud y longitud
    private lateinit var map: GoogleMap
    var   lattittu: String? = null
    var longgittu: String? = null
    var foto: Uri? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_f)


        val objetoIntent: Intent= intent
        var Descripcion = objetoIntent.getStringExtra("Descripcion")
        textBi.text = Descripcion


        val objectintent: Intent = intent
        lattittu = objectintent.getStringExtra("latitu")
        vistall.text = "$lattittu"
        longgittu = objectintent.getStringExtra("longitu")
        vislat.text = "$longgittu"
        lattittu?.toDouble()
        longgittu?.toDouble()

        //val objetooint: Intent = intent
        //foto = objetooint.getStringExtra("foto") as Nothing?
        //evidenc.setImageURI(foto)


        //
        evidenc.setOnClickListener{
            //check runtime permission
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M  ){
                if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE   ) ==
                        PackageManager.PERMISSION_DENIED){
                    //permision denied
                    val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    //show to popup to request runtime permission
                    requestPermissions(permissions, PERMISION_CODE)
                }//fin if checkserlf permision
                else{
                    //permision already granted
                    pickImageFromGallery();
                }//fin else al ready permision granted
            }//fin if build version
            else{
                //permision <= Marshmallow
                pickImageFromGallery();
            }//fin else permision <= M
        }//FIN EVIDENC
        //

        //crear metodo fragment
        createFrament()

    }//fin funcreate (savedinstance)

    private fun pickImageFromGallery() {
        //intent to pick image
        val intent = Intent (Intent.ACTION_PICK   )
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE   )
    }//fin pickimagefromgallery

    //
    companion object{
        //image pick code
        private  val IMAGE_PICK_CODE = 1000;
        //permision code
        private val PERMISION_CODE = 1001;
    }//fin
    //

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode   ) {
            PERMISION_CODE -> {
                if (  grantResults.size > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED   ){
                    //permision from popup granted
                    pickImageFromGallery()
                }//fin permision granted
                else{
                    //permision from popup denied
                    Toast.makeText(this, "Permisos denegado ", Toast.LENGTH_SHORT   ).show()
                }//fin else granresults

            }//fin permision code
        }//fin when
    }//fin onrequestpermission result

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode ==  IMAGE_PICK_CODE )  {
                    evidenc.setImageURI(data?.data)
                }//fin if
    }//fin on activity main



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
        val marker = MarkerOptions().position(disney).title("Tu ubicación actual")
        map.addMarker(marker)
        map.animateCamera(
                CameraUpdateFactory.newLatLngZoom(disney, 18f), 6000, null
        )


    }//fin metodo onmap ready

    private fun LatLng(lattittu: String?, longgittu: String?): LatLng {
        return LatLng(lattittu, longgittu)
    }//fin fun latlng


}