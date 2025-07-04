package com.example.googleplacesapi

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import java.util.Arrays

class MainActivity : AppCompatActivity() {

    private lateinit var placesClient: PlacesClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val ai: ApplicationInfo = applicationContext.packageManager
//            .getApplicationInfo(applicationContext.packageName, PackageManager.GET_META_DATA)
//        val value = ai.metaData[""]
//        val apiKey = value.toString()

        // Initializing the Places API
        // with the help of our API_KEY
//        if (!Places.isInitialized()) {
//            Places.initialize(applicationContext, "AIzaSyAa0pGRj6oGQDYGgG3LrZqihsRBOZeKK5s")
//            Toast.makeText(this@MainActivity,"init",Toast.LENGTH_SHORT).show()
//        }

        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, "AIzaSyDj6D3QtBy7hfNpDe0Gqy2OjBCxrGF0d5k")
            placesClient = Places.createClient(this)
        }

        // Initialize Autocomplete Fragment
        val autocompleteFragment = supportFragmentManager
            .findFragmentById(R.id.autocomplete_fragment1) as AutocompleteSupportFragment

        // Specify the types of place data to return
        autocompleteFragment.setPlaceFields(
            listOf(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.ADDRESS,
                Place.Field.LAT_LNG
            )
        )

        // Set up AutocompleteFragment options
        autocompleteFragment.setHint("Search for places")
//        autocompleteFragment.setCountry("US")
        autocompleteFragment.setTypeFilter(TypeFilter.ADDRESS)

        // Set up AutocompleteFragment listener
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                // Handle the selected place
                val textView = findViewById<TextView>(R.id.tv1)
                textView.text = "Place: ${place.name}, Address: ${place.address}, LatLng: ${place.latLng}"
            }

            override fun onError(status: Status) {
                // Handle errors
                Log.e("PlacesAPI", "onError: ${status.statusMessage}")
                Toast.makeText(applicationContext, "Error: ${status.statusMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }



// if you want to create in kt file then need to use this function
    private fun showPlacePicker() {
        //zip1 = zip_code.text.trim().toString()

        val list: ArrayList<String> = ArrayList()
        list.add("UK")
        // list.add("AU")
        list.add("LK")
        list.add("IN")
        Places.initialize(getApplicationContext(),"AIzaSyDj6D3QtBy7hfNpDe0Gqy2OjBCxrGF0d5k")
        val fields = Arrays.asList(
            Place.Field.ID,
            Place.Field.NAME,
            Place.Field.LAT_LNG,
            Place.Field.ADDRESS,
            Place.Field.VIEWPORT,
            Place.Field.ADDRESS_COMPONENTS,
            Place.Field.USER_RATINGS_TOTAL,
        )
        val intent = Autocomplete.IntentBuilder(
            AutocompleteActivityMode.OVERLAY
            , fields
        )
            .setCountries(list)
            //.setInitialQuery(zip1)
            .build(this@MainActivity)
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            if (resultCode == Activity.RESULT_OK) {
                val place = Autocomplete.getPlaceFromIntent(data!!)
                Log.e("place",""+place.toString())
                if (place != null) {
                    val latLng = place.latLng
//                    strCurrentLat = latLng!!.latitude.toString()
//                    strCurrentLong = latLng.longitude.toString()
//                    strLocation = place.address.toString()
//
//                   Log.d("addreess=====", ""+place.addressComponents)
//
//                    edt_your_location.setTextColor(ContextCompat.getColor(this@AddAddress, R.color.black))
//                    edt_your_location.setText(strLocation.trim())
//                    edt_your_location.setTextColor(ContextCompat.getColor(this,R.color.black))

                    var strLatitude = latLng!!.latitude.toString()
                    var strLongitude = latLng.longitude.toString()
                    Log.e("lat long", strLatitude + "   " + strLongitude + "           " + place.address)


                }

            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                val status = Autocomplete.getStatusFromIntent(data!!)
                Log.e("place","RESULT_ERROR "+status.toString())
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.e("place","RESULT_CANCELED ")
            }
        }
    }

    }
