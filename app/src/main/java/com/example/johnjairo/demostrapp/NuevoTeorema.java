package com.example.johnjairo.demostrapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.johnjairo.demostrapp.logica.FBF;



import static com.example.johnjairo.demostrapp.R.id.editText_conclusion;
import static com.example.johnjairo.demostrapp.R.id.editText_expresion;
import static com.example.johnjairo.demostrapp.R.id.editText_hipotesis;
import static com.example.johnjairo.demostrapp.R.id.editText_inferencia;
import static com.example.johnjairo.demostrapp.R.id.layout_Premisas;
import static com.example.johnjairo.demostrapp.R.id.layout_ContenedorPremisas;
import static com.example.johnjairo.demostrapp.R.id.spinner_inferencia;


/**
 * Created by rocka on 23/03/2017.
 */

public class NuevoTeorema extends AppCompatActivity{



    private String textFocus;
    private EditText textHip, textConcl, textInf, textExpre;
    private Button añadirButton, eliminarHipButton, btnNegacion, btnAbrePar, btnCierraPar, btnFlecha,
            btnFlechaBi, btnDisy, btnConj, btnValidar, btnComprobarDemos;
    private Spinner spinnerPremisaConclusion, spinnerJustificacion;
    private static final String KEY_TEXT_VALUE = "booleana";
    private LinearLayout nd_layout_justificacion, layoutContenedorHip;
    private RelativeLayout layoutHip;
    private TextView textViewExpresion;
    private TableLayout Tabla;
    CustomKeyboard customKeyboard;
    private  Integer ic_Size;

    static int totalEditTexts = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevoteorema);

        ic_Size = 38;
        textHip = (EditText) findViewById(editText_hipotesis);
        textInf = (EditText) findViewById(editText_inferencia);
        textExpre = (EditText) findViewById(editText_expresion);
        textConcl = (EditText) findViewById(editText_conclusion);
        añadirButton  = (Button) findViewById(R.id.btn_añadir);
        eliminarHipButton = (Button) findViewById(R.id.btn_eliminarHip);
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

        // initialize the instance variable customKeyboard
        //customKeyboard = new CustomKeyboard(this, R.id.keyboardview, R.layout.keyboard);
        // register the edittext
        //customKeyboard.registerEditText(R.id.editText_hipotesis);

        //Ocultamos los elementos a usaremos luego de establecer premisa y conclusión
        eliminarHipButton.setVisibility(View.GONE);
        nd_layout_justificacion.setVisibility(View.GONE);
        btnValidar.setVisibility(View.GONE);
        textViewExpresion.setVisibility(View.GONE);
        textExpre.setVisibility(View.GONE);
        Tabla.setVisibility(View.GONE);
        btnComprobarDemos.setVisibility(View.GONE);

        // Esto es para el spinnerJustificacion
        ArrayAdapter<CharSequence> justificacion_Adapter = ArrayAdapter.createFromResource(this,
                R.array.justificacion_array, android.R.layout.simple_spinner_dropdown_item);
        justificacion_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerJustificacion.setAdapter(justificacion_Adapter);

        //Bloqueamos entrada de texto en la inferencia
        textInf.setKeyListener(null);

        layoutContenedorHip = (LinearLayout)findViewById(layout_ContenedorPremisas);
        layoutHip = (RelativeLayout)findViewById(layout_Premisas);
        final Integer buttonId = new Integer(0);

        añadirButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("OnClick", "OnClickxd");
                totalEditTexts++;

                final RelativeLayout layoutHipCopy = new RelativeLayout(layoutHip.getContext());
                final EditText textHipCopy = new AppCompatEditText(NuevoTeorema.this);
                final Button buttonElimHip = new AppCompatButton(NuevoTeorema.this);

                eliminarHipButton.setVisibility(View.GONE);
                layoutHipCopy.setLayoutParams(layoutHip.getLayoutParams());
                textHipCopy.setLayoutParams(textHip.getLayoutParams());
                buttonElimHip.setLayoutParams(eliminarHipButton.getLayoutParams());

                layoutHipCopy.setMinimumWidth(layoutHip.getWidth());
                layoutHipCopy.setMinimumHeight(layoutHip.getHeight());

                textHipCopy.setWidth(textHip.getWidth());
                textHipCopy.setHeight(textHip.getHeight());
                textHipCopy.setInputType(textHip.getInputType());
                textHipCopy.setHighlightColor(textHip.getHighlightColor());
                textHipCopy.setLinkTextColor(textHip.getLinkTextColors());
                textHipCopy.getBackground().setColorFilter(Color.rgb(200,230,201), PorterDuff.Mode.SRC_IN);
                textHipCopy.setEms(textHip.getMinEms());
                textHipCopy.setGravity((textHip.getGravity()));
                textHipCopy.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        try{
                            String expresion = textHipCopy.getText().toString();
                            FBF f=new FBF(expresion);
                            textHipCopy.getBackground().setColorFilter(Color.rgb(200,230,201), PorterDuff.Mode.SRC_IN);
                        }catch(Exception e){
                            textHipCopy.getBackground().setColorFilter(Color.rgb(255,102,102), PorterDuff.Mode.SRC_IN);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        try{
                            String expresion = textHipCopy.getText().toString();
                            FBF f=new FBF(expresion);

                            Bitmap resourceImg = BitmapFactory.decodeResource(getResources(), R.drawable.ic_checkmark);
                            Bitmap resizedImg = Bitmap.createScaledBitmap(resourceImg, ic_Size, ic_Size, true);
                            Drawable drawableImg = new BitmapDrawable(getResources(), resizedImg);

                            textHipCopy.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableImg, null);
                        }catch(Exception e){
                            Bitmap resourceImg = BitmapFactory.decodeResource(getResources(), R.drawable.ic_error);
                            Bitmap resizedImg = Bitmap.createScaledBitmap(resourceImg, ic_Size, ic_Size, true);
                            Drawable drawableImg = new BitmapDrawable(getResources(), resizedImg);

                            textHipCopy.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableImg, null);
                        }
                    }
                });

                buttonElimHip.setWidth(eliminarHipButton.getWidth());
                buttonElimHip.setHeight(eliminarHipButton.getHeight());
                buttonElimHip.setText("-");
                buttonElimHip.setTextColor(eliminarHipButton.getTextColors());
                buttonElimHip.setTextSize(30);
                buttonElimHip.setGravity(eliminarHipButton.getGravity());
                buttonElimHip.setPadding(0,0,0,0);

                //El background del boton
                /*Bitmap resourceImgDelete = BitmapFactory.decodeResource(getResources(), R.drawable.ic_delete);
                Bitmap resizedImgDelete = Bitmap.createScaledBitmap(resourceImgDelete, 25, 25, true);
                Drawable drawableImgDelete = new BitmapDrawable(getResources(), resizedImgDelete);
                buttonElimHip.setBackground(drawableImgDelete);
                */
                buttonElimHip.setId(buttonId + 1);

                buttonElimHip.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        layoutContenedorHip.removeView(layoutHipCopy);
                    }
                });

                //textHipCopy = textHip;
                //textHipCopy.setLayoutParams(textHip.getLayoutParams());// = textHip;
                //EditText textHipCopy = new EditText(this);
                //layoutContenedorHip.removeView(textHip);
                //textHipCopy.setGravity(Gravity.FILL_VERTICAL);
                //layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;


                //if you want to identify the created editTexts, set a tag, like below
                textHipCopy.setId(100 + totalEditTexts);
                buttonElimHip.setId(200 + totalEditTexts);
                layoutHipCopy.setId(300 + totalEditTexts);
                textHipCopy.setSaveEnabled(true);
                buttonElimHip.setSaveEnabled(true);
                layoutHipCopy.setSaveEnabled(true);

                textHip.clearFocus();
                textConcl.clearFocus();
                textHipCopy.requestFocus();

                //textHipCopy.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
                //textHipCopy.setHighlightColor(ContextCompat.getColor(NuevoTeorema.this, R.color.primary_light));
                //textHipCopy.setLinkTextColor(ContextCompat.getColor(NuevoTeorema.this, R.color.primary_light));
                //textHipCopy.getBackground().setColorFilter(Color.rgb(200,230,201), PorterDuff.Mode.SRC_IN);
                //textHipCopy.setEms(5);

                //buttonElimHip.setText("X");
                //buttonElimHip.setTag("buttonElimHip" + totalEditTexts);


                layoutHipCopy.addView(textHipCopy);
                layoutHipCopy.addView(buttonElimHip);
                layoutContenedorHip.addView(layoutHipCopy);


                String expresion = textHip.getText().toString();
                String expresion2;
                try {
                    FBF f  = new FBF(expresion);
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
                    //textHip.getBackground().setColorFilter(Color.rgb(200,230,201), PorterDuff.Mode.SRC_IN);

                } catch (Exception e) {
                    //textHip.getBackground().setColorFilter(Color.rgb(255,102,102), PorterDuff.Mode.SRC_IN);
                }
            }
        });

        textHip.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    String expresion = textHip.getText().toString();
                    FBF f=new FBF(expresion);
                    textHip.getBackground().setColorFilter(Color.rgb(200,230,201), PorterDuff.Mode.SRC_IN);
                }catch(Exception e){
                    textHip.getBackground().setColorFilter(Color.rgb(255,102,102), PorterDuff.Mode.SRC_IN);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                try{
                    String expresion = textHip.getText().toString();
                    FBF f=new FBF(expresion);

                    Bitmap resourceImg = BitmapFactory.decodeResource(getResources(), R.drawable.ic_checkmark);
                    Bitmap resizedImg = Bitmap.createScaledBitmap(resourceImg, ic_Size, ic_Size, true);
                    Drawable drawableImg = new BitmapDrawable(getResources(), resizedImg);

                    textHip.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableImg, null);
                }catch(Exception e){
                    Bitmap resourceImg = BitmapFactory.decodeResource(getResources(), R.drawable.ic_error);
                    Bitmap resizedImg = Bitmap.createScaledBitmap(resourceImg, ic_Size, ic_Size, true);
                    Drawable drawableImg = new BitmapDrawable(getResources(), resizedImg);

                    textHip.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableImg, null);
                }
            }
        });

        textConcl.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    String expresion = textConcl.getText().toString();
                    FBF f=new FBF(expresion);
                    textConcl.getBackground().setColorFilter(Color.rgb(200,230,201), PorterDuff.Mode.SRC_IN);
                }catch(Exception e){
                    textConcl.getBackground().setColorFilter(Color.rgb(255,102,102), PorterDuff.Mode.SRC_IN);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                try{
                    String expresion = textConcl.getText().toString();
                    FBF f=new FBF(expresion);

                    Bitmap resourceImg = BitmapFactory.decodeResource(getResources(), R.drawable.ic_checkmark);
                    Bitmap resizedImg = Bitmap.createScaledBitmap(resourceImg, ic_Size, ic_Size, true);
                    Drawable drawableImg = new BitmapDrawable(getResources(), resizedImg);

                    textConcl.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableImg, null);
                }catch(Exception e){
                    Bitmap resourceImg = BitmapFactory.decodeResource(getResources(), R.drawable.ic_error);
                    Bitmap resizedImg = Bitmap.createScaledBitmap(resourceImg, ic_Size, ic_Size, true);
                    Drawable drawableImg = new BitmapDrawable(getResources(), resizedImg);

                    textConcl.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableImg, null);
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
        EditText focusText;
        focusText = (EditText)this.getCurrentFocus();

        if(focusText != null){
            pos = focusText.getSelectionStart();
            texto = focusText.getText().toString();
            texto = texto.substring(0, pos) + c + texto.substring(pos, texto.length());
            focusText.setText(texto);
            focusText.setSelection(pos+1);
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

    /*//Ocultar el teclado al dar ATRÁS
    @Override
    public void onBackPressed() {
        if(customKeyboard!=null && customKeyboard.isCustomKeyboardVisible() ) customKeyboard.hideCustomKeyboard(); else super.onBackPressed();
    }
    */
}