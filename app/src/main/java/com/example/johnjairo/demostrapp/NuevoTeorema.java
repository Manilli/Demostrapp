package com.example.johnjairo.demostrapp;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.johnjairo.demostrapp.logica.FBF;


import static com.example.johnjairo.demostrapp.R.id.editText_expresion;
import static com.example.johnjairo.demostrapp.R.id.editText_hipotesis;
import static com.example.johnjairo.demostrapp.R.id.editText_inferencia;
import static com.example.johnjairo.demostrapp.R.id.spinner_inferencia;


/**
 * Created by rocka on 23/03/2017.
 */

public class NuevoTeorema extends AppCompatActivity {

    private String textFocus;
    private EditText textHip, textInf, textExpre;
    private Button añadirButton, btnNegacion, btnAbrePar, btnCierraPar, btnFlecha, btnFlechaBi, btnDisy, btnConj, btnValidar, btnComprobarDemos;
    private Spinner spinnerPremisaConclusion, spinnerJustificacion;
    private static final String KEY_TEXT_VALUE = "booleana";
    private LinearLayout nd_layout_justificacion;
    private TextView textViewExpresion;
    private TableLayout Tabla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevoteorema);

        textHip = (EditText) findViewById(editText_hipotesis);
        textInf = (EditText) findViewById(editText_inferencia);
        textExpre = (EditText) findViewById(editText_expresion);
        añadirButton  = (Button) findViewById(R.id.btn_añadir);
        btnNegacion = (Button) findViewById(R.id.btn_negacion);
        btnAbrePar = (Button) findViewById(R.id.btn_abre_parentesis);
        btnCierraPar = (Button) findViewById(R.id.btn_cierra_parentesis);
        btnFlecha = (Button) findViewById(R.id.btn_condicional);
        btnFlechaBi = (Button) findViewById(R.id.btn_bicondicional);
        btnDisy = (Button) findViewById(R.id.btn_disyuncion);
        btnConj = (Button) findViewById(R.id.btn_conjuncion);
        btnValidar = (Button) findViewById(R.id.btn_validar);
        btnComprobarDemos = (Button) findViewById(R.id.btn_comprobarDemos);
        nd_layout_justificacion = (LinearLayout) findViewById(R.id.nd_layout_justificacion);
        textViewExpresion = (TextView) findViewById(R.id.textView_expresion);
        Tabla = (TableLayout) findViewById(R.id.Tabla);
        spinnerPremisaConclusion = (Spinner) findViewById(spinner_inferencia);
        spinnerJustificacion = (Spinner) findViewById(R.id.spinner_justificacion);

        //Ocultamos los elementos a usaremos luego de establecer premisa y conclusión
        nd_layout_justificacion.setVisibility(View.GONE);
        btnValidar.setVisibility(View.GONE);
        textViewExpresion.setVisibility(View.GONE);
        textExpre.setVisibility(View.GONE);
        Tabla.setVisibility(View.GONE);
        btnComprobarDemos.setVisibility(View.GONE);

        // Esto es para el spinnerPremisaConclusion
        ArrayAdapter<CharSequence> inferenciaAdapter = ArrayAdapter.createFromResource(this,
                R.array.premisa_conclusion_array, android.R.layout.simple_spinner_dropdown_item);
        inferenciaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPremisaConclusion.setAdapter(inferenciaAdapter);

        // Esto es para el spinnerJustificacion
        ArrayAdapter<CharSequence> justificacion_Adapter = ArrayAdapter.createFromResource(this,
                R.array.justificacion_array, android.R.layout.simple_spinner_dropdown_item);
        justificacion_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerJustificacion.setAdapter(justificacion_Adapter);

        //Bloqueamos entrada de texto en la inferencia
        textInf.setKeyListener(null);

        //Para saber si el focus está en éste editText
        textHip.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    setTextFocus("textHip");
                    //Toast.makeText(getApplicationContext(), "got the focus", Toast.LENGTH_LONG).show();
                }else {
                    //Toast.makeText(getApplicationContext(), "lost the focus", Toast.LENGTH_LONG).show();
                }
            }
        });

        //Para saber si el focus está en éste editText
        textExpre.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    setTextFocus("textExpre");
                    //Toast.makeText(getApplicationContext(), "got the focus", Toast.LENGTH_LONG).show();
                }else {
                    //Toast.makeText(getApplicationContext(), "lost the focus", Toast.LENGTH_LONG).show();
                }
            }
        });

        añadirButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String expresion = textHip.getText().toString();
                String expresion2;
                try {
                    FBF f  = new FBF(expresion);
                    //textHip.setBackgroundColor(Color.GREEN);
                    expresion2= textInf.getText().toString();
                    if(spinnerPremisaConclusion.getSelectedItem().equals("Premisa")){
                        if(!expresion2.equals("")){
                            expresion2+= "," + textHip.getText();
                        }else {
                            expresion2+= textHip.getText();
                        }
                    }else if(spinnerPremisaConclusion.getSelectedItem().equals("Conclusión")){
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
                            spinnerPremisaConclusion.setEnabled(false);
                            añadirButton.setEnabled(false);
                            textHip.setEnabled(false);
                            nd_layout_justificacion.setVisibility(View.VISIBLE);
                            btnValidar.setVisibility(View.VISIBLE);
                            textViewExpresion.setVisibility(View.VISIBLE);
                            textExpre.setVisibility(View.VISIBLE);
                            Tabla.setVisibility(View.VISIBLE);
                            btnComprobarDemos.setVisibility(View.VISIBLE);
                        }
                    }
                    textInf.setText(expresion2);
                    textHip.setText("");
                    //textHip.setBackgroundColor(Color.WHITE);
                    textHip.getBackground().setColorFilter(Color.rgb(200,230,201), PorterDuff.Mode.SRC_IN);


                } catch (Exception e) {
                    //textHip.setBackgroundColor(Color.rgb(255,102,102));
                    textHip.getBackground().setColorFilter(Color.rgb(255,102,102), PorterDuff.Mode.SRC_IN);
                }
            }
        });

        //Funcionalidad de los botones de los operandos
        btnNegacion.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                agregarOperandos('¬');
            }
        });

        btnAbrePar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                agregarOperandos('(');
            }
        });

        btnCierraPar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                agregarOperandos(')');
            }
        });

        btnFlecha.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                agregarOperandos('→');
            }
        });

        btnFlechaBi.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                agregarOperandos('↔');
            }
        });

        btnDisy.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                agregarOperandos('∨');
            }
        });

        btnConj.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                agregarOperandos('∧');
            }
        });

    }

    public String getTextFocus() {
        return textFocus;
    }

    public void setTextFocus(String textFocus) {
        this.textFocus = textFocus;
    }

    //Dependiendo de dónde esté el focus, agrega el operando pulsado
    public void agregarOperandos(char c){
        int pos=0;
        String texto;
        if(this.getTextFocus().equals("textHip") && (textHip.isEnabled())){
            pos = textHip.getSelectionStart();
            texto = textHip.getText().toString();
            texto = texto.substring(0, pos) + c + texto.substring(pos, texto.length());
            textHip.setText(texto);
            textHip.setSelection(pos+1);
        }
        else if(this.getTextFocus().equals("textExpre")){
            pos = textExpre.getSelectionStart();
            texto = textExpre.getText().toString();
            texto = texto.substring(0, pos) + c + texto.substring(pos, texto.length());
            textExpre.setText(texto);
            textExpre.setSelection(pos+1);
        }
    }

    //Guardamos algunos estados para cuando se rote la pantalla
    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_TEXT_VALUE, textHip.isEnabled());
    }

    //Cargamos los estados guardados al girar la pantalla
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Read values from the "savedInstanceState"-object and put them in your textview
        boolean a = savedInstanceState.getBoolean(KEY_TEXT_VALUE);
        textHip.setEnabled(a);
        spinnerPremisaConclusion.setEnabled(a);
        añadirButton.setEnabled(a);
        if (a){

        }
        else {
            nd_layout_justificacion.setVisibility(View.VISIBLE);
            btnValidar.setVisibility(View.VISIBLE);
            textViewExpresion.setVisibility(View.VISIBLE);
            textExpre.setVisibility(View.VISIBLE);
            Tabla.setVisibility(View.VISIBLE);
            btnComprobarDemos.setVisibility(View.VISIBLE);
        }

    }

}