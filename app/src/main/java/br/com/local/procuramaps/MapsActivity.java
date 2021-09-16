package br.com.local.procuramaps;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private String[] permissoes = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    private LocationManager locationManager;

    private LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Permissoes.ValidarPermissoes(permissoes, this, 1);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {

                Log.d("Localização", "onLocationChanged: " + location.toString());
            }
        };

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng Minha_Posição = new LatLng(-23.7027, -46.6893);
        mMap.addMarker(new MarkerOptions().position(Minha_Posição).title("Minha Posição"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Minha_Posição, 18));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int PermissaoResultado : grantResults) {

            if (PermissaoResultado == PackageManager.PERMISSION_DENIED) {

                ValidacaoUsuario();
            }

            else if (PermissaoResultado == PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER, 0, 0,
                            locationListener);
                }
            }
        }
    }

    private  void ValidacaoUsuario() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissão negada!!!");
        builder.setMessage("Para utilizar o App é necessário aceitar as permissões");
        builder.setCancelable(false);
        builder.setPositiveButton("Confirmar", (dialogInterface, i) -> {
            finish();
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
