package br.com.wilker.projeto2;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import br.com.wilker.projeto2.helpers.GPSTracker;
import br.com.wilker.projeto2.helpers.Subscriber;

/**
 * Created by Wilker on 06/06/2018.
 */
public class MapaActivity extends AppCompatActivity implements OnMapReadyCallback {

    private Marker postionMarker;
    private GoogleMap mMap;
    private GPSTracker gps;
    private Location localizacaoAtual;
    private Subscriber onLocalizacaoChangeSubscriber;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapa);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_view);
        mapFragment.getMapAsync(this);
        gps = new GPSTracker(this, 500l, 100l);
        onLocalizacaoChangeSubscriber = gps.getLocationChangeObservable().subscribe(new Subscriber<Location>() {
            @Override
            public void onEventEmit(Location location) {
                localizacaoAtual = location;
                moveMapToLocalizacaoAtual();
            }
        });
    }



    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            String [] permissoes = { Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
            ActivityCompat.requestPermissions(this, permissoes, 1);
            return;
        }
        mMap.getUiSettings().setScrollGesturesEnabled(false);
        mMap.setMyLocationEnabled(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

    }

    public void moveMapToLocalizacaoAtual(){
        moverCameraPara(localizacaoAtual);
    }

    public void moverCameraPara(Location location){
        LatLng markarLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        if(postionMarker == null){
            postionMarker = mMap.addMarker(new MarkerOptions()
                    .title(getResources().getString(R.string.voce_esta_aqui))
                    .position(markarLatLng));
        } else {
            postionMarker.setPosition(markarLatLng);
        }
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        CameraPosition cameraPosition = CameraPosition.builder(mMap.getCameraPosition())
                .target(latLng)
                .bearing(location.getBearing())
                .zoom(16f)
                .build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        mMap.animateCamera(cameraUpdate);
    }


}
