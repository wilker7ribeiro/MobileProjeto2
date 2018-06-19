package br.com.wilker.projeto2.activities;

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

import br.com.wilker.projeto2.R;
import br.com.wilker.projeto2.helpers.GPSTracker;
import br.com.wilker.projeto2.helpers.Subscriber;

/**
 * Created by Wilker on 06/06/2018.
 */
public class MapaActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int REQUEST_CODE_PERMISAO = 1;
    private Marker postionMarker;
    private GoogleMap mMap;
    private GPSTracker gps;
    private Location localizacaoAtual;
    private Subscriber onLocalizacaoChangeSubscriber;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapa);

        // prepara o fragmento do google maps
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_view);
        mapFragment.getMapAsync(this);

        // instancia o GPSTracker
        gps = new GPSTracker(this, 500l, 100l);

        // move o mapa para a localização atual quando ela mudar
        onLocalizacaoChangeSubscriber = gps.getLocationChangeObservable().subscribe(new Subscriber<Location>() {
            @Override
            public void onEventEmit(Location location) {
                localizacaoAtual = location;
                moverCameraPara(localizacaoAtual);
            }
        });
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        // remove a inscrição do Observable
        onLocalizacaoChangeSubscriber.unsubscribe();
    }



    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        // Verifica as permisões
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            String [] permissoes = { Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
            // Caso não tem, pede permissão
            ActivityCompat.requestPermissions(this, permissoes, REQUEST_CODE_PERMISAO);
            return;
        }
        
        // adiciona o botão de localização atual no google map
        mMap.setMyLocationEnabled(true);
    }

    // handler de pedidos de permissão
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_PERMISAO:
                // caso a permissão seja negada, volta pra tela anterior
                if (grantResults.length == 0){
                    finish();
                }
             break;
        }
    }

    // move a câmera para a localização passada e adiciona um marker
    public void moverCameraPara(Location location){
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        // Se não existir nenhum marker, cria um
        // Caso exista, só atualiza a localização
        if(postionMarker == null){
            postionMarker = mMap.addMarker(new MarkerOptions()
                    .title(getResources().getString(R.string.voce_esta_aqui))
                    .position(latLng));
        } else {
            postionMarker.setPosition(latLng);
        }

        // cria um objeto de posição de câmera utilizando um builder
        CameraPosition cameraPosition = CameraPosition.builder(mMap.getCameraPosition())
                .target(latLng)
                .bearing(location.getBearing())
                .zoom(16f)
                .build();
        // Move a câmera do google maps
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        mMap.animateCamera(cameraUpdate);
    }


}
