package com.example.m1

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import kotlinx.android.synthetic.main.activity_m_a2.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MA2 : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener {
    //val lista = listOf("Enfermedad Cardiovascular","Crisis asmatica","Enfermedad Pulmonar Obstructiva Crónica (EPOC)","Neumonía","Bronquilitis",
      //   "Dolor abdominal","Enfermedad diarreica aguda","Politraumatismos","Descompensación diabética","Otro"    )
    var editText: EditText? = null
    var textView2: TextView? = null //latitud
    var textView3: TextView? = null //longitud

    //barra de busqueda

    private val request_camera = 1001
    private val request_gallery = 1002
    var foto: Uri? = null

    /////////////////////////////////////////////////////////////
    private lateinit var map: GoogleMap
    companion object{
        const val REQUEST_CODE_LOCATION = 0
    }//fin companion
    //*****************************************************************//


    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        //createMarker()
        map.setOnMyLocationButtonClickListener(this)
        map.setOnMyLocationClickListener(this)
        enableLocation()



    }

    //88888888888888888888888888888888888888888888888888888888


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_m_a2)
        abrecamara_Click()
        createMapFragment()//


        //-------------------------------------------------------------------------------
        //bot+on visualizar
        benv.setOnClickListener {
            var Descripcion:String = editTextDes.text.toString()
            var lati: String = textView2?.text.toString()
            var longi: String = textView3?.text.toString()


            

            val intent: Intent = Intent(this, PantallaF::class.java)


            intent.putExtra("Descripcion", Descripcion)
            intent.putExtra("latitu", lati)
            intent.putExtra("longitu", longi)

            intent.putExtra("foto", imgfoto)


            startActivity(intent)


            
            
            
        }//fin setonclick listener
        //fin boton visualizar
        //-------------------------------------------------------------------------------

        //barra busqueda asignamos variables
        editText = findViewById(R.id.edit_text)
        textView2 = findViewById(R.id.text_view2) //latitud
        textView3 = findViewById(R.id.text_view3) //longitud
        //fin barra busqueda

        //Iinicializamos google places
        Places.initialize(applicationContext, "AIzaSyA9r9VKV45qCWjHDC7GnRp09KvlKc23SVs")
        //Fin Inicialización de places

        //EditTextFocusable
        editText?.setFocusable(false)

        editText?.setOnClickListener(View.OnClickListener { //inicializamos la bandera de places
            val fieldList = Arrays.asList(
                    Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME
            )
            //creamos el intent
            val intent = Autocomplete.IntentBuilder(
                    AutocompleteActivityMode.OVERLAY, fieldList
            ).build(this@MA2)
            //iniciamos activityresukt
            startActivityForResult(intent, 100)
        })
        //Fin editText focusable
        //fin barra de busqueda

        //..................................................................................
/*
        val spinner = findViewById<Spinner>(R.id.Spin)
        val lista = resources.getStringArray(R.array.list   )
        val adaptador = ArrayAdapter (this, android.R.layout.simple_spinner_item, lista     )
        spinner.adapter = adaptador
        spinner.onItemSelectedListener = object:
                AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
            ) {
                Toast.makeText(this@MA2, lista[position] , Toast.LENGTH_LONG).show()
            }//fin onitemselected
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }//fin onNnothingselected


        }//fin adapterviewonitemselectedlistener
*/
        //...................................................................................


                //AdapterView.OnItemSelectedListener
        //OnItemSelectedListe   ner1





    }//fin override fun oncrete

    ///////////////////////////////////////////////////////////
    private fun createMapFragment() {
        //val mapFragment = supportFragmentManager.findFragmentById(R.id.fragmentMap) as SupportMapFragment
        //mapFragment.getMapAsync(this)
    }

    private fun isLocationPermissionGranted() = ContextCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_FINE_LOCATION
    )   == PackageManager.PERMISSION_GRANTED
    //fin funcion isLocationPermissionGranted

    private fun enableLocation() {
        if(!::map.isInitialized) return
        if (isLocationPermissionGranted()   )   {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


                return
            }
            map.isMyLocationEnabled = true     //si
        }else {//fin if locationpermissiongranted
            requestLocationPermission()     //no
            }

    }//fin function enableLocation



    private fun requestLocationPermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)    ) {
            Toast.makeText(this, "Ve a ajustes y activa los permisos", Toast.LENGTH_SHORT).show()
        }else {//fin if activity compact
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE_LOCATION)
            }//fin else
    }//fin requestLocationPermission

    ///////////////////////////////////////////////////////

    private fun abrecamara_Click(){
        //btnCamara.setOnClickListener(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ){
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED ) {
                val permisosCamara = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                requestPermissions(permisosCamara, request_camera)
            }else{
                abrecamara()
            }
        }else {
            abrecamara()
        }

       // }//fin set oncliclck listener boton

    }//fin funcion abrecamera

    //////////////////////////////////////////////////////////////////////////////////
    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        //***************************************
        when(requestCode   ){
            REQUEST_CODE_LOCATION -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return
                }
                map.isMyLocationEnabled = true
            } else {//fin request
                Toast.makeText(this, "Para activar localización ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
            }//fin else
            else -> {}
        }//fin when

        ///***************************************

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when( requestCode) {
            request_camera -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    abrecamara()
                else
                    Toast.makeText(applicationContext, "No puedes acceder a la camara", Toast.LENGTH_SHORT).show()
            }//fin request gallery

        }//fin when

    }//fin on request

    //.........................
    override fun onResumeFragments() {
        super.onResumeFragments()
        if(!::map.isInitialized  ) return
        if (!isLocationPermissionGranted()    ){
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            map.isMyLocationEnabled = false
            Toast.makeText(this, "Para activar localización ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
        }//fin if
    }//fin on resume fragmebt
    //...................................

    ///////////////////////////////////////////////////////////////////////
    private fun abrecamara (){
        val value = ContentValues()
        value.put(MediaStore.Images.Media.TITLE, "Nueva imagen")
        foto = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, value)
        val camaraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        camaraIntent.putExtra(MediaStore.EXTRA_OUTPUT, foto)
        startActivityForResult(camaraIntent, request_camera)

    }//fin abrir cam

    ////////////////////////////////////////////////////////////////
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == request_camera){
            imgfoto.setImageURI(foto)
        }//fin if resultcode

        //
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            //cuando fue exitoso

            //val destinationLatLng: LatLng = place.getLatLng()
            //inicializamos place
            val place = Autocomplete.getPlaceFromIntent(data!!)
            //obtenemos la dirección en editext
            editText!!.setText(place.address)
            //obtenemos nombre de localidad
            //------------textView1!!.text = String.format("Nombre de localidad: %s", place.name)
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

    }//fin onactivityresul

    override fun onMyLocationButtonClick(): Boolean {
        return false
    }//fin onMyLocationBUtton

    override fun onMyLocationClick(p0: Location) {
        Toast.makeText(this, "Te encuentras en ${p0.latitude}, ${p0.longitude} ", Toast.LENGTH_SHORT) .show()
        val loc = LatLng(p0.latitude, p0.longitude)
        map.addMarker(MarkerOptions().position(loc).title("My pos"))
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 18f), 4000, null)
//        val pos = LatLng(p0.latitude, p0.longitude)
//        map.addMarker(MarkerOptions().position(pos).title("Me")   )
//        map.animateCamera(
//                CameraUpdateFactory.newLatLngZoom(pos, 18f),
//                7500,
//                null
//       )
    }//fin onMyLocationClick

//    private fun createMarker() {
//        val favoritePlace = LatLng(28.044195,-16.5363842)
//        map.addMarker(MarkerOptions().position(favoritePlace).title("Mi playa favorita!"))
//        map.animateCamera(
//                CameraUpdateFactory.newLatLngZoom(favoritePlace, 18f),
//                7500,
//                null
//        )
//    }
    /////////////////////////////////////////////////////////////




}//fin appcpmpaactivity

private fun Intent.putExtra(imgfoto: String, imgfoto1: ImageView?) {

}
