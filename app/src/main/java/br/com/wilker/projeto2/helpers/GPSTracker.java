package br.com.wilker.projeto2.helpers;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

/**
 * Classe Helper de GPS
 * Created by Wilker on 06/06/2018.
 */
public class GPSTracker extends Service implements LocationListener {

    private final Context mContext;

    private EventEmitter<Location> locationChangeObservable = new EventEmitter<>();

    // flags de status do GPSTracker
    boolean isGPSHabilitado = false;
    boolean isInternetHabilitado = false;
    boolean canObterLocalizacao = false;

    // Atributos da localização
    private Location location;
    private double latitude;
    private double longitude;
    private float bearing;

    // Distancia em metros para atualizar a localização
    private long DISTANCIA_MINIMA_ENTRE_ATUALIZACOES = 10; // default 10 metros

    // Tempo minimo entre atualizações
    private long TEMPO_MINIMO_ENTRE_ATUALIZACOES = 1000 * 60 * 1; // default 1 minuto

    // Classe de gerenciamento de localização do android
    protected LocationManager locationManager;

    public GPSTracker(Context context) {
        this.mContext = context;
        getLocation();
    }

    public GPSTracker(Context context, Long tempoMinimoEntreUpdatesEmMs, Long distaciaMinimaEntreUpdatesEmMetros) {
        this.mContext = context;
        this.DISTANCIA_MINIMA_ENTRE_ATUALIZACOES = distaciaMinimaEntreUpdatesEmMetros;
        this.TEMPO_MINIMO_ENTRE_ATUALIZACOES = tempoMinimoEntreUpdatesEmMs;
        getLocation();
    }

    public Location getLocation() {
        try {
            locationManager = (LocationManager) mContext
                    .getSystemService(LOCATION_SERVICE);


            // Verifica quais os provider está habilitado
            isGPSHabilitado = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isInternetHabilitado = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSHabilitado && !isInternetHabilitado) {
               // se nenhum tiver habilitado faz nada
            } else {
                this.canObterLocalizacao = true;
                // Primeiramente pega a localização via internet que é "mais rápido" mas menos preciso
                if (isInternetHabilitado) {
                    // configura o locationManager com Internet
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            TEMPO_MINIMO_ENTRE_ATUALIZACOES,
                            DISTANCIA_MINIMA_ENTRE_ATUALIZACOES, this);

                    if (locationManager != null) {
                        // Atualiza as variaveis de localização
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                // se o GPS tiver habilitado, utiliza o gps
                if (isGPSHabilitado) {
                    if (location == null) {
                        // configura o locationManager com GPS
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                TEMPO_MINIMO_ENTRE_ATUALIZACOES,
                                DISTANCIA_MINIMA_ENTRE_ATUALIZACOES, this);

                        if (locationManager != null) {
                            // Atualiza as variaveis de localização
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return location;
    }

    /**
     * Para de utilizar o GPS
     * */
    public void pararDeUsarGPS(){
        if(locationManager != null){
            locationManager.removeUpdates(GPSTracker.this);
        }
    }

    // obter latitude
    public double getLatitude(){
        if(location != null){
            latitude = location.getLatitude();
        }
        return latitude;
    }

    // obter longitude
    public double getLongitude(){
        if(location != null){
            longitude = location.getLongitude();
        }
        return longitude;
    }

    // obter "angulo"
    public float getBearing(){
        if(location != null){
            bearing = location.getBearing();
        }
        return bearing;
    }

    // obtem o EventEmitter
    public EventEmitter<Location> getLocationChangeObservable() {
        return locationChangeObservable;
    }

    // Verifica se o GPS ou WiFi ta habilitado para obter loalização
    public boolean canObterLocalizacao() {
        return this.canObterLocalizacao;
    }


    // Ao receber uma mudança de localização do LocationManager, emit o evento;
    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
        locationChangeObservable.emit(location);
    }

    // Override da interface
    @Override
    public void onProviderDisabled(String provider) {
    }

    // Override da interface
    @Override
    public void onProviderEnabled(String provider) {
    }

    // Override da interface
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    // Override da interface
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

}
