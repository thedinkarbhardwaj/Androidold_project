package com.example.googlemap

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.googlemap.apiinterface.ApiService

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.googlemap.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Polygon
import com.google.android.gms.maps.model.PolygonOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var polygon: Polygon? = null
    private lateinit var binding: ActivityMapsBinding
    val polygonOptions = PolygonOptions()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @SuppressLint("SuspiciousIndentation")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
      //  apicall()

        val polygonOptions = PolygonOptions()
            .add(LatLng(-0.09672555327415466, -78.4195785522461))
            .add(LatLng(-0.09672555327415466, -78.4195785522461))
            .add(LatLng(-0.09676306694746017, -78.41960144042969))
            .add(LatLng(-0.09711291640996933, -78.41922760009766))
            .add(LatLng(-0.09707784652709961, -78.41920471191406))
            .add(LatLng(-0.09672555327415466, -78.4195785522461))
            .add(LatLng(-0.0971713662147522, -78.42003631591797))
            .add(LatLng(-0.09720421582460403, -78.42007446289063))
            .add(LatLng(-0.09755592793226242, -78.41967010498047))
            .add(LatLng(-0.09752008318901062, -78.41963958740234))
            .add(LatLng(-0.0971713662147522, -78.42003631591797))
            .add(LatLng(-0.09680663794279099, -78.4197006225586))
            .add(LatLng(-0.0967789888381958, -78.41973876953125))
            .add(LatLng(-0.09712029248476028, -78.4200439453125))
            .add(LatLng(-0.09714850038290024, -78.42000579833984))
            .add(LatLng(-0.09680663794279099, -78.4197006225586))
            .add(LatLng(-0.09637991338968277, -78.41996002197266))
            .add(LatLng(-0.0964178517460823, -78.41998291015625))
            .add(LatLng(-0.09661971032619476, -78.41976928710938))
            .add(LatLng(-0.09659077227115631, -78.41974639892578))
            .add(LatLng(-0.09637991338968277, -78.41996002197266))
            .add(LatLng(-0.09726852923631668, -78.42012023925781))
            .add(LatLng(-0.09724510461091995, -78.4201431274414))
            .add(LatLng(-0.0975784957408905, -78.42044067382813))
            .add(LatLng(-0.09759857505559921, -78.42041778564453))
            .add(LatLng(-0.09726852923631668, -78.42012023925781))

//         Customize polygon appearance if desired
       // polygonOptions.fillColor(resources.getColor(R.color.red))
        polygonOptions.fillColor(Color.GREEN).strokeColor(Color.RED)


        mMap.addPolygon(polygonOptions)
      //  polygonOptions.fillColor(resources.getColor(androidx.appcompat.R.color.primary_dark_material_dark))
        // Move camera to center of the polygon
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(-0.09726852923631668, -78.42012023925781), 12f))
    }

    fun apicall(){


        val retrofit = Retrofit.Builder()
            .baseUrl("https://igor.center/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        val requestData = RequestData(
            latitud = "-0.2145870",
            longitud = "78.2541201",
            usuarioId = 1,
            fechaSolicitud = "2023-06-24T14:15:23",
            numeroSubzonas = 5
        )


        val call = apiService.sendData(requestData)
        call.enqueue(object : Callback<PolygonResponse> {
            override fun onResponse(call: Call<PolygonResponse>, response: Response<PolygonResponse>) {
                if (response.isSuccessful) {

                    var latitudeee:Double = 0.0
                    var longitudeee = 0.0

                    // Handle successful response
                    val responseBody = response.body()
                    val responseString = responseBody?.toString()


//                    for (subZona in responseBody?.subZonas!!) {
//                        println("SubZona: ${subZona.nombre}")
//                        for (point in subZona.poligono!!) {
//                            Log.d(
//                                "DinkarPoligon",
//                                "Latitud: ${point.latitud}, Longitud: ${point.longitud}"
//                            )
//                            var latitude = point.latitud
//                            var longitude = point.longitud
//                            polygonOptions.add(LatLng(latitude!!, longitude!!))
//
//                            latitudeee = latitude
//                            longitudeee = longitude
//
//                        }
//
//                        mMap.addPolygon(polygonOptions)
//                        polygonOptions.fillColor(resources.getColor(androidx.appcompat.R.color.primary_dark_material_dark))
//                        // Move camera to center of the polygon
//                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(latitudeee, longitudeee), 15f))
//
//                        // Process the responseString as needed
//                    }


                    // if we display only box not point to point then we can use below code
                    for (subZona in responseBody?.subZonas.orEmpty()) {
                        val polygonOptions = PolygonOptions() // Create a new PolygonOptions for each subZona

                        println("SubZona: ${subZona.nombre}")
                        subZona.poligono?.let { poligonoList ->
                            for (point in poligonoList) {
                                point.latitud?.let { latitude ->
                                    point.longitud?.let { longitude ->
                                        Log.d("DinkarPoligon", "Latitud: $latitude, Longitud: $longitude")
                                        polygonOptions.add(LatLng(latitude, longitude))
                                    }
                                }
                            }
                        }

                        if (polygonOptions.points.isNotEmpty()) {
                            mMap.addPolygon(polygonOptions)
                            //polygonOptions.fillColor(resources.getColor(androidx.appcompat.R.color.primary_dark_material_dark))

                            // Calculate center of polygon
                            val centerLat = subZona.centro?.latitud ?: 0.0
                            val centerLng = subZona.centro?.longitud ?: 0.0

                           // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(centerLat, centerLng), 15f))
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(-0.09726852923631668, -78.42012023925781), 15f))
                        }
                    }

                }
            }

            override fun onFailure(call: Call<PolygonResponse>, t: Throwable) {
            Toast.makeText(this@MapsActivity,"kdj" + call.toString(),Toast.LENGTH_LONG).show()
                Log.d("DinkarLog",call.toString())

            }
        })

    }

    private fun getMarkerIconForMarca(marca: String): BitmapDescriptor {
        val color: Int = when (marca) {
            "Yellow" -> Color.YELLOW
            "Green" -> Color.GREEN
            "Brown" -> Color.parseColor("#8B4513")  // Brown color
            else -> Color.RED
        }

        val markerBitmap = Bitmap.createBitmap(48, 48, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(markerBitmap)
        val paint = Paint()
        paint.color = color
        canvas.drawCircle(24f, 24f, 24f, paint)

        return BitmapDescriptorFactory.fromBitmap(markerBitmap)
    }

    fun localjsonfile(){
        //        val jsonData =
//            resources.openRawResource(R.raw.polygon_data).bufferedReader().use { it.readText() }
//        val jsonObject = JSONObject(jsonData)
//        val subZonasArray = jsonObject.getJSONArray("subZonas")
//
//        Log.e("check===",subZonasArray.length().toString())
//        for (i in 0 until subZonasArray.length()) {
//            val subZonaObject = subZonasArray.getJSONObject(i)
//            val poligonoArray = subZonaObject.getJSONArray("poligono")
//
//            val markerPositions = ArrayList<LatLng>()
//
//            Log.e("check===sdgdf",poligonoArray.length().toString())
//
//            for (j in 0 until poligonoArray.length()) {
//
//                val pointObject = poligonoArray.getJSONObject(j)
//                val latitude = pointObject.getDouble("latitud")
//                val longitude = pointObject.getDouble("longitud")
//                polygonOptions.add(LatLng(latitude, longitude))
//                markerPositions.add(LatLng(latitude, longitude))
//                Log.e("LATLONG","lati" + latitude.toString() + "long" + longitude.toString())
//            }
//            // Add polygon to the map
//            polygon = mMap.addPolygon(polygonOptions)
//
//           // val markerIcon = getMarkerIconForMarca(subZonaObject.getString("marca"))
//            for (position in markerPositions) {
//              //  mMap.addMarker(MarkerOptions().position(position))
//              //  mMap.addMarker(MarkerOptions().position(position).icon(markerIcon))
//            }
////            polygon!!.tag = subZonaObject.getString("nombre")
//
//            // Move camera to center of the polygon
//          //  mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(-0.09637991338968277, -78.41996002197266), 12f))
//        }
    }

}
