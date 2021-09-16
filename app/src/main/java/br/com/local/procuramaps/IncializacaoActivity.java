package br.com.local.procuramaps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import android.os.Bundle;



public class IncializacaoActivity extends AppCompatActivity {

    Button btnBuscar;
    EditText lt, lg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incializacao);

        btnBuscar = findViewById(R.id.btnBuscar);
        /*lt = findViewById(R.id.Latitude);
        lg = findViewById(R.id.Longitude);*/

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(lt.getText().toString().isEmpty() || lg.getText().toString().isEmpty()) {
                    Toast.makeText(IncializacaoActivity.this, "Campo Vazio!!!", Toast.LENGTH_LONG).show();
                } else if (Double.parseDouble(lt.getText().toString()) > 90 || Double.parseDouble(lt.getText().toString()) < -90) {
                    Toast.makeText(IncializacaoActivity.this, "Insira uma Latitude Válida", Toast.LENGTH_SHORT).show();
                } else if (Double.parseDouble(lg.getText().toString()) > 180 || Double.parseDouble(lg.getText().toString()) < -180) {
                    Toast.makeText(IncializacaoActivity.this, "Insira uma Longitude Válida", Toast.LENGTH_SHORT).show();
                } else {
                    Intent buscar = new Intent(IncializacaoActivity.this, MapsActivity.class);
                    buscar.putExtra("Latitude", lt.getText().toString());
                    buscar.putExtra("Longitude", lg.getText().toString());
                    startActivity(buscar);
                }
            }
        });
    }
}