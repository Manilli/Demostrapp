package com.example.johnjairo.demostrapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
/**
 * Created by rocka on 23/03/2017.
 */

public class NuevoTeorema extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevoteorema);

        // Esto es para el spinner
        Spinner spinner = (Spinner) findViewById(R.id.spinner_inferencia);
        ArrayAdapter<CharSequence> inferenciaAdapter = ArrayAdapter.createFromResource(this,
                R.array.premisa_conclusion_array, android.R.layout.simple_spinner_dropdown_item);
        inferenciaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(inferenciaAdapter);

        Spinner spinner2 = (Spinner) findViewById(R.id.spiner_justificacion);
        ArrayAdapter<CharSequence> justificacion_Adapter = ArrayAdapter.createFromResource(this,
                R.array.justificacion_array, android.R.layout.simple_spinner_dropdown_item);
        justificacion_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(justificacion_Adapter);

    }
}