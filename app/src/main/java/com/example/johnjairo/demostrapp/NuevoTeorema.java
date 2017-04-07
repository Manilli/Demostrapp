package com.example.johnjairo.demostrapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;

import com.example.johnjairo.demostrapp.logica.FBF;


import static com.example.johnjairo.demostrapp.R.id.editText;
import static com.example.johnjairo.demostrapp.R.id.editText2;
import static com.example.johnjairo.demostrapp.R.id.spinner_inferencia;


/**
 * Created by rocka on 23/03/2017.
 */

public class NuevoTeorema extends AppCompatActivity {
    int Bandera = 0;
    private EditText textHip;
    private EditText edit2expresion2;
    private Button añadirButton;
    private Spinner spinner;
    private static final String KEY_TEXT_VALUE = "booleana";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevoteorema);

        textHip = (EditText) findViewById(editText);
        edit2expresion2  = (EditText) findViewById(editText2);
        añadirButton  = (Button) findViewById(R.id.btn_añadir);

        // Esto es para el spinner
        spinner = (Spinner) findViewById(spinner_inferencia);
        ArrayAdapter<CharSequence> inferenciaAdapter = ArrayAdapter.createFromResource(this,
                R.array.premisa_conclusion_array, android.R.layout.simple_spinner_dropdown_item);
        inferenciaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(inferenciaAdapter);

        Spinner spinner2 = (Spinner) findViewById(R.id.spiner_justificacion);
        ArrayAdapter<CharSequence> justificacion_Adapter = ArrayAdapter.createFromResource(this,
                R.array.justificacion_array, android.R.layout.simple_spinner_dropdown_item);
        justificacion_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(justificacion_Adapter);

        edit2expresion2.setKeyListener(null);

        if (Bandera ==1){
            spinner.setEnabled(false);
            añadirButton.setEnabled(false);
            textHip.setEnabled(false);
        }
        añadirButton.setOnClickListener(new View.OnClickListener() {

            /*
                        @Override
                        public void onClick(View view) {
                            attemptLogin();
                        }
                        */
            @Override
            public void onClick(View view) {
                String expresion = textHip.getText().toString();
                String expresion2;
                try {
                    FBF f  = new FBF(expresion);
                    //textHip.setBackgroundColor(Color.GREEN);
                    expresion2= edit2expresion2.getText().toString();
                    if(spinner.getSelectedItem().equals("Premisa")){
                        if(!expresion2.equals("")){
                            expresion2+= "," + textHip.getText();
                        }else {
                            expresion2+= textHip.getText();
                        }
                    }else if(spinner.getSelectedItem().equals("Conclusión")){
                        String valor= expresion;
                        if(valor.contains("⊢")){
                            String[] split = valor.split("⊢", 2);
                            if(!split[1].equals("")){
                                expresion2+= "," + textHip.getText();
                            }else {
                                expresion2+= textHip.getText();
                            }
                        }else {
                            expresion2+= "⊢" + textHip.getText();
                            spinner.setEnabled(false);
                            añadirButton.setEnabled(false);
                            textHip.setEnabled(false);
                            Bandera = 1;
                        }
                    }
                    edit2expresion2.setText(expresion2);
                    textHip.setText("");
                    //textHip.setBackgroundColor(Color.WHITE);
                    textHip.getBackground().setColorFilter(Color.rgb(200,230,201), PorterDuff.Mode.SRC_IN);


                } catch (Exception e) {
                    //textHip.setBackgroundColor(Color.rgb(255,102,102));
                    textHip.getBackground().setColorFilter(Color.rgb(255,102,102), PorterDuff.Mode.SRC_IN);
                }
            }
        });
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Read values from the "savedInstanceState"-object and put them in your textview
        boolean a = savedInstanceState.getBoolean(KEY_TEXT_VALUE);
        textHip.setEnabled(a);
        spinner.setEnabled(a);
        añadirButton.setEnabled(a);

    }
    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_TEXT_VALUE, textHip.isEnabled());
    }
}