package com.erwin.historygo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.GroundOverlayOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.GroundOverlay








class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnPolygonClickListener {

    override fun onPolygonClick(p0: Polygon?) {
        Toast.makeText(this, "Kliknięto w obszar gry! " + p0.toString(),
                Toast.LENGTH_SHORT).show();
    }

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }
    
    override fun onMapReady(googleMap: GoogleMap) {

        /* ----------  MAPA ---------- */

        mMap = googleMap
        mMap.setMinZoomPreference(11.toFloat())
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(
                this, R.raw.map_style));

        // restrict to Poznan
        val Poznan = LatLngBounds(
                LatLng( 52.367342, 16.810038), LatLng( 52.458707, 17.042851))
        mMap.setLatLngBoundsForCameraTarget(Poznan)



        /* ----------  MIEJSCA - KOORDYNATY  ---------- */

        val zamek = LatLng(52.4080892, 16.9182926)
        val ratusz = LatLng(52.4085252, 16.9338935)
        val zoo = LatLng(52.4004405,16.9955802)
        val politechnika = LatLng(52.401879, 16.9512155)
        val malta = LatLng(52.4002572, 16.9800766)


        /* ----------  MIEJSCA -  MARKERY ---------- */

        mMap.addMarker(MarkerOptions().position(zamek)
                .title("Zamek")
                .snippet("Zamek Cesarski, wybudowany w 1910 roku.")
        )

        mMap.addMarker(MarkerOptions().position(ratusz)
                .title("Ratusz")
                .snippet("Przyjdź o 12.00 zobaczyć koziołki.")
        )

        mMap.addMarker(MarkerOptions().position(zoo)
                .title("Zoo")
                .snippet("Słonie, tygrysy i dużo więcej.")
        )

        mMap.addMarker(MarkerOptions().position(politechnika)
                .title("Politechnika")
                .snippet("Najważniejsza uczelnia w mieście.")
        )

        mMap.addMarker(MarkerOptions().position(malta)
                .title("Malta")
                .snippet("Sztuczne jezioro, utworzone w roku 1952.")
        )



        mMap.moveCamera(CameraUpdateFactory.newLatLng(zamek))




        val cytadela = googleMap.addPolygon(PolygonOptions()

                .clickable(true)
                .add(
                        LatLng(52.4214738, 16.924781),
                        LatLng(52.4255463, 16.9318352),
                        LatLng(52.4229068, 16.9459831),
                        LatLng( 52.4170502, 16.9370637)
                       )
                )
                .setFillColor(0xff388E3C.toInt())




        googleMap.setOnPolygonClickListener(this);

/*
        val NEWARK = LatLng(52.421402, 16.892746)

        val newarkMap = GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.drawable.old_poznan))
                .position(NEWARK, 8600f, 6500f)
        mMap.addGroundOverlay(newarkMap)
*/



        // GroundOverlay - limitation of picture size to 1 mb


        val newarkBounds = LatLngBounds(
                LatLng(52.377924, 16.892746),       // South west corner
                LatLng(52.425314, 16.961760))       // North east corner

        val newarkMap = GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.drawable.old_poznan))
                .positionFromBounds(newarkBounds)
                .transparency(0.4.toFloat())
         mMap.addGroundOverlay(newarkMap)

    }






        // lewy górny 52.421402, 16.892746
        // lewy dolnyy 52.377924, 16.892746
        // prawy górny 52.415314, 16.961760
        // prawy dolny 52.373157, 16.961760



}
