package com.example.googlemap1

import android.content.pm.PackageManager
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions

class MainActivity : AppCompatActivity() ,OnMapReadyCallback{
    private  val REQUEST_PERMISSIONS=1
    override fun onRequestPermissionsResult(requestCode: Int,
        permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(grantResults.isEmpty()) return
        when(requestCode){
            REQUEST_PERMISSIONS->{
                for(result in grantResults)
                    if(result != PackageManager.PERMISSION_GRANTED)
                        finish()
                    else{
                        val map=supportFragmentManager.findFragmentById(R.id.map)
                                as SupportMapFragment
                        map.getMapAsync(this)
                    }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION)!=
            PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),REQUEST_PERMISSIONS)
        else{
            val map=supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
            map.getMapAsync(this)
        }
    }

    override fun onMapReady(map: GoogleMap) {
        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)!=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION)!=
                PackageManager.PERMISSION_GRANTED)
                    return
        map.isMyLocationEnabled=true
        val marker=MarkerOptions()
        marker.position(LatLng(24.864432208167862,120.99069698317923))
        marker.title("明新科技大學")
        marker.draggable(true)
        map.addMarker(marker)
        marker.position(LatLng(24.86957430395818,120.99654985619759))
        marker.title("新豐車站")
        marker.draggable(true)
        map.addMarker(marker)
        val polylineOpt=PolylineOptions()
        polylineOpt.add(LatLng(24.864432208167862,120.99069698317923))
        polylineOpt.add(LatLng(24.86957430395818,120.99654985619759))
        polylineOpt.color(Color.BLUE)
        val polyline=map.addPolyline(polylineOpt)
        polyline.width=10f
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(
            LatLng(24.867371456616123,120.99330154447567),15f))
    }
}