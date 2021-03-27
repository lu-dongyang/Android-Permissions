package com.example.work



import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle


import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_gps.*
import java.util.*


class gps :AppCompatActivity(){

    lateinit var lm : LocationManager
    lateinit var loc: Location

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gps)



        if(ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION),111)

        lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager
//        loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)!!

        var ll = object : LocationListener {
            override fun onLocationChanged(p0: Location) {
                reverseGeocode(p0)
            }

        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,100.2f,ll)
    }

    private fun reverseGeocode(loc: Location) {
        var gc = Geocoder(this, Locale.getDefault())
        var addresses = gc.getFromLocation(loc.latitude,loc.longitude,2)
        var address = addresses.get(0)
        textview2.setText("current Location of you device is \n${address.getAddressLine(0)}\n${address.locality}")


    }



}



