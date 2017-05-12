package com.example.johnjairo.demostrapp;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.johnjairo.demostrapp.logica.Axiomas;
import com.example.johnjairo.demostrapp.logica.FBF;
import com.example.johnjairo.demostrapp.logica.Hipotesis;
import com.example.johnjairo.demostrapp.logica.Reglas;


import java.util.ArrayList;

import static android.widget.Toast.LENGTH_SHORT;
import static com.example.johnjairo.demostrapp.R.id.editText_conclusion;
import static com.example.johnjairo.demostrapp.R.id.editText_expresion;
import static com.example.johnjairo.demostrapp.R.id.editText_hipotesis;
import static com.example.johnjairo.demostrapp.R.id.editText_inferencia;
import static com.example.johnjairo.demostrapp.R.id.editText_sust1;
import static com.example.johnjairo.demostrapp.R.id.editText_sust2;
import static com.example.johnjairo.demostrapp.R.id.layout_Conclusion;
import static com.example.johnjairo.demostrapp.R.id.layout_Premisas;
import static com.example.johnjairo.demostrapp.R.id.layout_ContenedorPremisas;
import static com.example.johnjairo.demostrapp.R.id.layout_inferencia;
import static com.example.johnjairo.demostrapp.R.id.nd_mainlayout;
import static com.example.johnjairo.demostrapp.R.id.spinner_inferencia;


/**
 * Created by rocka on 23/03/2017.
 */

public class NuevoTeorema extends AppCompatActivity{



    private String textFocus;
    private EditText textHip, textConcl, textInf, textExpre, textSust1, textSust2;
    private Button añadirButton, eliminarHipButton, btnNegacion, btnAbrePar, btnCierraPar, btnFlecha,
            btnFlechaBi, btnDisy, btnConj, btnValidar, btnComprobarDemos, btnFijar, btnSusti;
    private ImageButton btnInfo;
    private Spinner spinnerPremisaConclusion, spinnerJustificacion, spinnerBicondicional, spinnerPremisas, spinnerSustitucion,
            spinnerPaso, spinnerPaso1;
    private static final String KEY_TEXT_VALUE = "booleana";
    private LinearLayout ndMainLayout, nd_layout_justificacion, layoutContenedorHip, layoutConclusion, layoutOperadores,
            layoutOperadoresJustificacion, layoutInferencia;
    private RelativeLayout layoutHip;
    private TextView textViewNuevaDemosTitle, textViewExpresion, textViewSust1, textViewSust2, textViewBicondicional;
    private TableLayout tablaDemostracion;
    CustomKeyboard customKeyboard;
    private Integer ic_Size, supuesto, bicondicionalActual=0, pasoDemostracion=0, pasoDemostracion2=0, banderaPremisaYConclusion=-1;
    private Axiomas axiomas;
    private Reglas reglas;
    private Hipotesis hipotesis, hipotesis2;
    private boolean esBicondicional;
    private ArrayList<FBF> antecedentes;
    private ArrayList<FBF> consecuentes;
    ArrayList<String> premisasArray = new ArrayList<String>();
    ArrayList<String> pasoArray = new ArrayList<String>();
    ArrayList<String> paso1Array = new ArrayList<String>();
    ArrayList<String> bicondicionalArray = new ArrayList<String>();
    ArrayList<String> reglaSustiArray = new ArrayList<String>();
    ArrayList<String> justificacionArray = new ArrayList<String>();
    ArrayList<Integer> banderaHipotesis = new ArrayList<>();

    static int totalEditTexts = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevoteorema);

        DisplayMetrics dm = new DisplayMetrics();
        NuevoTeorema.this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        ic_Size = (int)(20 * (dm.densityDpi / 160f));
        supuesto = 0;
        //ic_Size = 38;
        try {
            axiomas = new Axiomas();
        } catch (Exception e) {
            e.printStackTrace();
        }
        reglas = new Reglas();

        textHip = (EditText) findViewById(editText_hipotesis);
        textInf = (EditText) findViewById(editText_inferencia);
        textExpre = (EditText) findViewById(editText_expresion);
        textConcl = (EditText) findViewById(editText_conclusion);
        textSust1 = (EditText) findViewById(editText_sust1);
        textSust2 = (EditText) findViewById(editText_sust2);
        añadirButton  = (Button) findViewById(R.id.btn_añadir);
        eliminarHipButton = (Button) findViewById(R.id.btn_eliminarHip);
        btnNegacion = (Button) findViewById(R.id.btn_negacion);
        btnAbrePar = (Button) findViewById(R.id.btn_abre_parentesis);
        btnCierraPar = (Button) findViewById(R.id.btn_cierra_parentesis);
        btnFlecha = (Button) findViewById(R.id.btn_condicional);
        btnFlechaBi = (Button) findViewById(R.id.btn_bicondicional);
        btnDisy = (Button) findViewById(R.id.btn_disyuncion);
        btnConj = (Button) findViewById(R.id.btn_conjuncion);
        btnFijar = (Button) findViewById(R.id.btn_fijar);
        btnValidar = (Button) findViewById(R.id.btn_validar);
        btnComprobarDemos = (Button) findViewById(R.id.btn_comprobarDemos);
        btnSusti = (Button) findViewById(R.id.btn_sustituir);
        btnInfo = (ImageButton) findViewById(R.id.infoButton);
        textViewNuevaDemosTitle = (TextView) findViewById(R.id.nuevaDemosTitle);
        textViewNuevaDemosTitle.setSelected(true);
        textViewExpresion = (TextView) findViewById(R.id.textView_expresion);
        textViewSust1 = (TextView) findViewById(R.id.textView_sust1);
        textViewSust2 = (TextView) findViewById(R.id.textView_sust2);
        textViewBicondicional = (TextView) findViewById(R.id.textView_bicondicional);
        spinnerPremisaConclusion = (Spinner) findViewById(spinner_inferencia);
        spinnerJustificacion = (Spinner) findViewById(R.id.spinner_justificacion);
        spinnerBicondicional = (Spinner) findViewById(R.id.spinner_bicondicional);
        spinnerPremisas = (Spinner) findViewById(R.id.spinner_premisas);
        spinnerSustitucion = (Spinner) findViewById(R.id.spinner_reglaSusti);
        spinnerPaso = (Spinner) findViewById(R.id.spinner_paso);
        spinnerPaso1 = (Spinner) findViewById(R.id.spinner_paso1);
        ndMainLayout = (LinearLayout)findViewById(nd_mainlayout);
        layoutInferencia = (LinearLayout)findViewById(layout_inferencia);
        layoutContenedorHip = (LinearLayout)findViewById(layout_ContenedorPremisas);
        layoutHip = (RelativeLayout)findViewById(layout_Premisas);
        layoutConclusion = (LinearLayout)findViewById(layout_Conclusion);
        nd_layout_justificacion = (LinearLayout) findViewById(R.id.nd_layout_justificacion);
        layoutOperadores = (LinearLayout) findViewById(R.id.layout_operadores);
        layoutOperadoresJustificacion = (LinearLayout) findViewById(R.id.layout_operadoresJusti);
        tablaDemostracion = (TableLayout) findViewById(R.id.Tabla);
        //premisasArray = (ArrayList) getResources().getStringArray(R.array.premisas_array);

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
        tablaDemostracion.setVisibility(View.GONE);
        btnComprobarDemos.setVisibility(View.GONE);

        justificacionArray.add("Seleccione");
        justificacionArray.add("Premisa");
        justificacionArray.add("Sustitución");
        justificacionArray.add("Modus Ponems");
        justificacionArray.add("Supuesto");
        justificacionArray.add("Axioma 1");
        justificacionArray.add("Axioma 2");
        justificacionArray.add("Axioma 3");
        justificacionArray.add("Axioma 4");

        // Esto es para el spinnerJustificacion
        ArrayAdapter<String> justificacion_Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, justificacionArray);
        justificacion_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerJustificacion.setAdapter(justificacion_Adapter);



        premisasArray.add("Seleccione");
        // Esto es para el spinnerPremisas
        ArrayAdapter<String> premisas_Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, premisasArray);
        premisas_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPremisas.setAdapter(premisas_Adapter);

        // Esto es para el spinnerPaso
        ArrayAdapter<String> paso_Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, pasoArray);
        paso_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPaso.setAdapter(paso_Adapter);

        // Esto es para el spinnerPaso1
        ArrayAdapter<String> paso1_Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, paso1Array);
        paso1_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPaso1.setAdapter(paso1_Adapter);

        // Esto es para el spinnerBicondicional
        ArrayAdapter<String> bicond_Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, bicondicionalArray);
        bicond_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBicondicional.setAdapter(bicond_Adapter);

        reglaSustiArray.add("Seleccione");
        reglaSustiArray.add("RFP5");
        reglaSustiArray.add("RFP6");
        reglaSustiArray.add("RFP7");
        reglaSustiArray.add("Conmutatividad");
        // Esto es para el spinner de la regla de sustitución
        ArrayAdapter<String> sustitu_Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, reglaSustiArray);
        sustitu_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSustitucion.setAdapter(sustitu_Adapter);

        //Bloqueamos entrada de texto en la inferencia
        textInf.setKeyListener(null);
        deshabilitarComponentes(0);

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
                textHipCopy.setTag("premisa");

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
                            Bitmap resourceImg = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_error);
                            Bitmap resizedImg = Bitmap.createScaledBitmap(resourceImg,  ic_Size, ic_Size, true);
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
                            tablaDemostracion.setVisibility(View.VISIBLE);
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

        textExpre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    String expresion = textExpre.getText().toString();
                    FBF f=new FBF(expresion);
                    textExpre.getBackground().setColorFilter(Color.rgb(200,230,201), PorterDuff.Mode.SRC_IN);
                }catch(Exception e){
                    textExpre.getBackground().setColorFilter(Color.rgb(255,102,102), PorterDuff.Mode.SRC_IN);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                try{
                    String expresion = textExpre.getText().toString();
                    FBF f=new FBF(expresion);

                    Bitmap resourceImg = BitmapFactory.decodeResource(getResources(), R.drawable.ic_checkmark);
                    Bitmap resizedImg = Bitmap.createScaledBitmap(resourceImg, ic_Size, ic_Size, true);
                    Drawable drawableImg = new BitmapDrawable(getResources(), resizedImg);

                    textExpre.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableImg, null);
                }catch(Exception e){
                    Bitmap resourceImg = BitmapFactory.decodeResource(getResources(), R.drawable.ic_error);
                    Bitmap resizedImg = Bitmap.createScaledBitmap(resourceImg, ic_Size, ic_Size, true);
                    Drawable drawableImg = new BitmapDrawable(getResources(), resizedImg);

                    textExpre.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableImg, null);
                }
            }
        });

        textSust1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    String expresion = textSust1.getText().toString();
                    FBF f=new FBF(expresion);
                    textSust1.getBackground().setColorFilter(Color.rgb(200,230,201), PorterDuff.Mode.SRC_IN);
                }catch(Exception e){
                    textSust1.getBackground().setColorFilter(Color.rgb(255,102,102), PorterDuff.Mode.SRC_IN);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                try{
                    String expresion = textSust1.getText().toString();
                    FBF f=new FBF(expresion);

                    Bitmap resourceImg = BitmapFactory.decodeResource(getResources(), R.drawable.ic_checkmark);
                    Bitmap resizedImg = Bitmap.createScaledBitmap(resourceImg, ic_Size, ic_Size, true);
                    Drawable drawableImg = new BitmapDrawable(getResources(), resizedImg);

                    textSust1.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableImg, null);
                }catch(Exception e){
                    Bitmap resourceImg = BitmapFactory.decodeResource(getResources(), R.drawable.ic_error);
                    Bitmap resizedImg = Bitmap.createScaledBitmap(resourceImg, ic_Size, ic_Size, true);
                    Drawable drawableImg = new BitmapDrawable(getResources(), resizedImg);

                    textSust1.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableImg, null);
                }
            }
        });

        textSust2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    String expresion = textSust2.getText().toString();
                    FBF f=new FBF(expresion);
                    textSust2.getBackground().setColorFilter(Color.rgb(200,230,201), PorterDuff.Mode.SRC_IN);
                }catch(Exception e){
                    textSust2.getBackground().setColorFilter(Color.rgb(255,102,102), PorterDuff.Mode.SRC_IN);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                try{
                    String expresion = textSust2.getText().toString();
                    FBF f=new FBF(expresion);

                    Bitmap resourceImg = BitmapFactory.decodeResource(getResources(), R.drawable.ic_checkmark);
                    Bitmap resizedImg = Bitmap.createScaledBitmap(resourceImg, ic_Size, ic_Size, true);
                    Drawable drawableImg = new BitmapDrawable(getResources(), resizedImg);

                    textSust2.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableImg, null);
                }catch(Exception e){
                    Bitmap resourceImg = BitmapFactory.decodeResource(getResources(), R.drawable.ic_error);
                    Bitmap resizedImg = Bitmap.createScaledBitmap(resourceImg, ic_Size, ic_Size, true);
                    Drawable drawableImg = new BitmapDrawable(getResources(), resizedImg);

                    textSust2.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableImg, null);
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

        spinnerJustificacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView <?> parent, View view, int position,long id) {
                if (spinnerJustificacion.getSelectedItem().equals("Premisa")){
                    deshabilitarComponentes(2);
                    btnInfo.setVisibility(View.GONE);
                } else if(spinnerJustificacion.getSelectedItem().toString().contains("Axioma")){
                    deshabilitarComponentes(3);
                    btnInfo.setVisibility(View.VISIBLE);
                }else if(spinnerJustificacion.getSelectedItem().toString().contains("Sustitución")){
                    deshabilitarComponentes(1);
                    btnInfo.setVisibility(View.VISIBLE);
                }else if (spinnerJustificacion.getSelectedItem().equals("Modus Ponems")){
                    deshabilitarComponentes(4);
                    btnInfo.setVisibility(View.VISIBLE);
                    /*spinnerPaso1.removeAllViews();
                    for(int i=0; i<spinnerPaso.getCount(); i++){
                        spinnerPaso1.addView(spinnerPaso.getChildAt(i));
                    }*/
                }
                else if(spinnerJustificacion.getSelectedItem().toString().contains("Supuesto")){
                    deshabilitarComponentes(5);
                    btnInfo.setVisibility(View.GONE);
                }else if (spinnerJustificacion.getSelectedItem().equals("Seleccione")){
                    deshabilitarComponentes(3);
                    btnInfo.setVisibility(View.GONE);
                }

                else {
                    deshabilitarComponentes(4);
                    textExpre.setEnabled(true);
                    btnInfo.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        btnFijar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String expresion = textConcl.getText().toString();

                try {
                    if (expresion.charAt(0)=='⊢'){
                        if(detectarBiCondicional(expresion.substring(1))){
                            esBicondicional= true;
                            antecedentesConsecuentes(expresion.substring(1));
                            cargarDemostracionesBicondicional();
                            cargarAntecedentes(2);
                        } else {
                            //text1.setBackground(Color.red);  ERROR
                            return;
                        }
                    } else {
                        hipotesis = new Hipotesis(expresion);
                        cargarAntecedentes(1);
                    }
                    //FBF f  = new FBF(expresion);
                    //text1.setBackground(Color.GREEN);
                    //text1.setEnabled(false);

                    ArrayList<View> viewsPremisas = new ArrayList<View>();
                    viewsPremisas = getViewsByTag(layoutInferencia,"premisa");

                    for (int i=0; i<=viewsPremisas.size(); i++){
                        EditText e = (EditText)viewsPremisas.get(i);
                        String textopremisa = e.getText().toString();
                        try{
                            FBF f = new FBF(textopremisa);
                            //ArrayList<View> viewsPremisas = new ArrayList<View>();
                            //viewsPremisas = getViewsByTag(layoutInferencia,"premisa");

                            //Agregamos las premisas
                            premisasArray.add(e.getText().toString());
                            banderaPremisaYConclusion = 1;
                            //int j = viewsPremisas.size();
                            //Toast.makeText(NuevoTeorema.this, Integer.toString(viewsPremisas.size()), LENGTH_SHORT).show();

                            if (i == viewsPremisas.size()-1){
                                textViewNuevaDemosTitle.setText(textViewNuevaDemosTitle.getText().toString() + e.getText().toString() + " |- " + textConcl.getText().toString());
                            } else {
                                textViewNuevaDemosTitle.setText(textViewNuevaDemosTitle.getText().toString() + e.getText().toString() + ", ");
                            }

                        }catch(Exception exc){
                            Toast.makeText(NuevoTeorema.this, "Hay errores de sintaxis.\n" + "Corríjalos para poder continuar.", LENGTH_SHORT).show();
                            banderaPremisaYConclusion = -1;
                            premisasArray.clear();
                            premisasArray.add("Seleccione");
                            textViewNuevaDemosTitle.setText("");
                            break;
                        }
                    }
                } catch (Exception e) {
                    //text1.setBackground(Color.red); ERROR
                }

                try {
                    FBF f = new FBF(textConcl.getText().toString());
                }
                catch (Exception ex){
                    Toast.makeText(NuevoTeorema.this, "Hay errores de sintaxis.\n" + "Corríjalos para poder continuar.", LENGTH_SHORT).show();
                    banderaPremisaYConclusion = -1;
                    textViewNuevaDemosTitle.setText("");
                }
                if (banderaPremisaYConclusion != -1){
                    deshabilitarComponentes(6);
                    ndMainLayout.removeView(layoutOperadores);
                    layoutOperadoresJustificacion.addView(layoutOperadores);
                }
                //deshabilitarComponentes(5);
            }
        });

        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String regla= spinnerJustificacion.getSelectedItem().toString();
                String mensaje="";
                boolean mostrar= false;
                switch(regla){
                    case "Axioma 1": mensaje= "Idempotencia: p∨p→p";
                        mostrar=true;
                        break;
                    case "Axioma 2": mensaje= "Adjuncion: p→p∨q";
                        mostrar=true;
                        break;
                    case "Axioma 3": mensaje= "Conmutatividad: p∨q→q∨p";
                        mostrar=true;
                        break;
                    case "Axioma 4": mensaje= "Adicion: (p→q)→(r∨p→r∨q)";
                        mostrar=true;
                        break;
                    case "Sustitución": if(spinnerSustitucion.getSelectedItem().equals("RFP5")){
                        mensaje ="Definición de formas proposicionales conjuntivas \n "
                                + "Sean r y s fbfs, entonces la fórmula r∧s se \n"
                                + "considera bien formada y se define como: ¬(¬r∨¬s)";
                        mostrar=true;
                    }else if(spinnerSustitucion.getSelectedItem().equals("RFP6")){
                        mensaje ="Definición de formas proposicionales condicionales \n "
                                + "Sean r y s fbfs, entonces la fórmula r→s se \n"
                                + "considera bien formada y se define como: ¬r∨s)";
                        mostrar=true;
                    }else if(spinnerSustitucion.getSelectedItem().equals("RFP7")){
                        mensaje ="Definición de formas proposicionales bicondicionales \n "
                                + "Sean r y s fbfs, entonces la fórmula r↔s se \n"
                                + "considera bien formada y se define como: (r→s)∧(s→r)";
                        mostrar=true;
                    }
                        break;
                    case "Modus Ponems": mensaje="p, p→q |-q";
                        mostrar=true;
                        break;
                    case "Seleccione": mostrar=false;
                        break;
                }
                if(mostrar) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(NuevoTeorema.this);

                    builder.setTitle(spinnerJustificacion.getSelectedItem().toString());

                    builder.setMessage(mensaje)
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // TODO: handle the OK
                                }
                            })
                            /*.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            })*/;

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        });

        btnValidar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean error=false;
                String expresion = textExpre.getText().toString();
                String justificacion= spinnerJustificacion.getSelectedItem().toString();
                String stringValidar="";
                stringValidar= validar();
                if(!stringValidar.equals("")){
                    Toast.makeText(NuevoTeorema.this, stringValidar, LENGTH_SHORT).show();
                    //JOptionPane.showMessageDialog(this, stringValidar);
                    return;
                }
                try {
                    //if(justificacion.contains("Modus Ponems"))expresion = "a";

                    FBF f = new FBF(expresion);
                    boolean v = true;
                    if(spinnerJustificacion.getSelectedItem().toString().contains("Supuesto")){
                        error=false;
                        supuesto=1;
                        justificacion = "Supuesto";
                    }else if(spinnerJustificacion.getSelectedItem().toString().contains("Premisa")){
                        error=false;
                    } else if(spinnerJustificacion.getSelectedItem().toString().contains("Axioma")){
                        String item = spinnerJustificacion.getSelectedItem().toString();

                        switch(item){
                            case "Axioma 1":
                                v = axiomas.validar(1, f);
                                break;
                            case "Axioma 2":
                                v = axiomas.validar(2, f);
                                break;
                            case "Axioma 3":
                                v = axiomas.validar(3, f);
                                break;
                            case "Axioma 4":
                                v = axiomas.validar(4, f);
                                break;
                        }
                    /*}else if(spinnerJustificacion.getSelectedItem().toString().contains("Modus Ponems")){
                        int paso1= Integer.parseInt(spinnerPaso.getSelectedItem().toString());
                        int paso2= Integer.parseInt(spinnerPaso1.getSelectedItem().toString());
                        String expresion1= "";//tablaDemostracion.getValueAt(paso1-1, 1).toString(); Hay que hacerlo de modo distinto
                        String expresion2= "";//tablaDemostracion.getValueAt(paso2-1, 1).toString(); Hay que hacerlo de modo distinto
                        String ponem= reglas.modusPonems(expresion1, expresion2);
                        if(ponem!=null){
                            expresion= ponem;
                            justificacion= "Modus Ponems entre "+ paso1 + " y " + paso2;
                        }else {
                            error=true;
                            //JOptionPane.showMessageDialog(this, "No es posible realizar modus ponems entre los pasos seleccionados");
                            Toast.makeText(NuevoTeorema.this, "No es posible realizar modus ponems entre los pasos seleccionados", LENGTH_SHORT).show();
                        }*/
                    } else {
                        String s = comprobarTeorema(spinnerJustificacion.getSelectedItem().toString());
                        if(s.equals("")){
                            error=true;
                        } else {
                            justificacion += s;
                        }
                    }

                    if(!error){
                        if(v){
                            textExpre.setText("");
                            //agregarFBF(expresion,justificacion);  //Hay que migrar a una solución diferente para manejar la tabla
                        }else{
                            Bitmap resourceImg = BitmapFactory.decodeResource(getResources(), R.drawable.ic_error);
                            Bitmap resizedImg = Bitmap.createScaledBitmap(resourceImg, ic_Size, ic_Size, true);
                            Drawable drawableImg = new BitmapDrawable(getResources(), resizedImg);

                            textExpre.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableImg, null);
                        }
                    }
                } catch (Exception e) {
                    //e.printStackTrace();
                    Bitmap resourceImg = BitmapFactory.decodeResource(getResources(), R.drawable.ic_error);
                    Bitmap resizedImg = Bitmap.createScaledBitmap(resourceImg, ic_Size, ic_Size, true);
                    Drawable drawableImg = new BitmapDrawable(getResources(), resizedImg);

                    textExpre.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableImg, null);
                }
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

    public String validar(){
        String regla= spinnerJustificacion.getSelectedItem().toString();
        if(regla.equals("Seleccione")){
            return "Seleccione una regla";
        } else if (regla.equals("Premisa")){
            if(spinnerPremisas.getSelectedItem().equals("Seleccione")){
                return "Seleccione la premisa";
            }
        } else if (regla.equals("Sustitucion")){
            if(spinnerSustitucion.getSelectedItem().equals("Seleccione")){
                return "Seleccione la regla de sustitucion";
            } else if(spinnerPaso.getSelectedItem().equals("Seleccione")){
                return "Seleccione el paso al cual desea aplicarle la sustitucion";
            } else if(textSust1.getText().equals("")|| textSust2.getText().equals("")){
                return "Ingrese las FbFs";
            }
        } else if(regla.contains("Axioma")|| regla.equals("Supuesto")){
            if(textExpre.getText().equals("")){
                return "Ingrese la expresion";
            }
        } /*else if(regla.equals("Modus Ponems")){
            if(spinnerPaso.getSelectedItem().equals("Seleccione") || spinnerPaso1.getSelectedItem().equals("Seleccione")){
                return "Seleccione los pasos";
            }
        }*/

        return "";
    }

    public String comprobarTeorema(String teorema){
        String expresion= ""; //conexionsql.consultarDemostracion(teorema); //Hay que ver cómo hacerlo con la base de datos en firebase
        String justifacion="";
        Hipotesis hip = new Hipotesis(expresion);
        ArrayList<FBF> antecedentes= hip.getAntecedentes();
        ArrayList<FBF> consecuentes = hip.getConsecuentes();
        String consecuentes1;
        String consecuentes2;
        int paso;
        try {
            if(expresion.contains("↔")){
                ArrayList<String> bicondicionales = organizarBiCondicional(expresion);
                hip= new Hipotesis(bicondicionales.get(0));
                Hipotesis hip2 = new Hipotesis(bicondicionales.get(1));
                consecuentes1= hip.getConse();
                consecuentes2= hip2.getConse();
                paso = Integer.parseInt(spinnerPaso.getSelectedItem().toString());
                if(axiomas.equivalenciaArboles(new FBF(consecuentes1), new FBF(textExpre.getText().toString()))){
                    justifacion += " en" + paso;
                    return justifacion;
                }else if(axiomas.equivalenciaArboles(new FBF(consecuentes2), new FBF(textExpre.getText().toString()))){
                    justifacion += " en" + paso;
                    return justifacion;
                }
            }else{
                if(antecedentes.size()==2){
                    paso = Integer.parseInt(spinnerPaso.getSelectedItem().toString());
                    expresion= "";//tablaDemostracion.getValueAt(paso-1, 1).toString(); Hay que hacerlo distinto
                    if(axiomas.equivalenciaArboles(antecedentes.get(0), new FBF(expresion))){
                        justifacion= " en " + paso;
                        paso = Integer.parseInt(spinnerPaso1.getSelectedItem().toString());
                        expresion= "";//tablaDemostracion.getValueAt(paso-1, 1).toString(); Hay que hacerlo distinto
                        if(axiomas.equivalenciaArboles(antecedentes.get(1), new FBF(expresion))){
                            if(axiomas.equivalenciaArboles(consecuentes.get(0), new FBF(textExpre.getText().toString()))){
                                justifacion += " y " + paso;
                                return justifacion;
                            }
                        }
                    }
                } else if(antecedentes.size()==1){
                    paso = Integer.parseInt(spinnerPaso.getSelectedItem().toString());
                    expresion= "";//tablaDemostracion.getValueAt(paso-1, 1).toString(); Hay que hacerlo distinto
                    if(axiomas.equivalenciaArboles(antecedentes.get(0), new FBF(expresion))){
                        if(axiomas.equivalenciaArboles(consecuentes.get(0), new FBF(textExpre.getText().toString()))){
                            justifacion = " en " + paso;
                            return justifacion;
                        }
                    }
                }
            }
        } catch (Exception ex) {
            //Logger.getLogger(PantallaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public ArrayList<String> organizarBiCondicional(String expresion){
        ArrayList<String> bicondicional = new ArrayList<>();
        bicondicional.add(expresion.replace('↔','→' ));
        String [] e = expresion.split("↔");
        bicondicional.add("⊢" + e[1] + "→"  + e[0].substring(1));
        return bicondicional;
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
            tablaDemostracion.setVisibility(View.VISIBLE);
            btnComprobarDemos.setVisibility(View.VISIBLE);
        }

    }

    public void deshabilitarComponentes(int caso){
        switch (caso){
            case 0: // Al principio
                textExpre.setEnabled(false);
                spinnerPaso.setEnabled(false);
                spinnerPaso1.setEnabled(false);
                spinnerJustificacion.setEnabled(false);
                btnValidar.setEnabled(false);
                spinnerPremisas.setVisibility(View.GONE);
                spinnerSustitucion.setVisibility(View.GONE);
                spinnerPaso1.setVisibility(View.GONE);
                spinnerPaso.setVisibility(View.GONE);
                spinnerBicondicional.setVisibility(View.GONE);
                textSust1.setVisibility(View.GONE);
                textSust2.setVisibility(View.GONE);
                textViewSust1.setVisibility(View.GONE);
                textViewSust2.setVisibility(View.GONE);
                btnSusti.setVisibility(View.GONE);
                //
                btnAbrePar.setFocusable(false);
                btnCierraPar.setFocusable(false);
                btnConj.setFocusable(false);
                btnDisy.setFocusable(false);
                btnFlecha.setFocusable(false);
                btnFlechaBi.setFocusable(false);
                btnNegacion.setFocusable(false);
                btnValidar.setFocusable(false);
                break;
            case 1: //Sust
                spinnerPremisas.setVisibility(View.GONE);
                spinnerPaso.setVisibility(View.VISIBLE);
                spinnerPaso.setEnabled(true);
                spinnerPaso.setVisibility(View.VISIBLE);
                spinnerPaso1.setVisibility(View.GONE);
                spinnerSustitucion.setVisibility(View.VISIBLE);
                textExpre.setEnabled(false);
                textExpre.setVisibility(View.GONE);
                textViewExpresion.setVisibility(View.GONE);
                layoutOperadores.setVisibility(View.VISIBLE);
                btnValidar.setEnabled(false);
                btnValidar.setVisibility(View.GONE);
                textSust1.setVisibility(View.VISIBLE);
                textSust2.setVisibility(View.VISIBLE);
                textViewSust1.setVisibility(View.VISIBLE);
                textViewSust2.setVisibility(View.VISIBLE);
                btnSusti.setVisibility(View.VISIBLE);
                break;
            case 2: //Premisa
                spinnerPremisas.setVisibility(View.VISIBLE);
                spinnerPaso.setVisibility(View.GONE);
                spinnerPaso1.setVisibility(View.GONE);
                spinnerSustitucion.setVisibility(View.GONE);
                textExpre.setEnabled(false);
                textExpre.setVisibility(View.GONE);
                textViewExpresion.setVisibility(View.GONE);
                layoutOperadores.setVisibility(View.GONE);
                btnValidar.setEnabled(true);
                btnValidar.setVisibility(View.VISIBLE);
                textSust1.setVisibility(View.GONE);
                textSust2.setVisibility(View.GONE);
                textViewSust1.setVisibility(View.GONE);
                textViewSust2.setVisibility(View.GONE);
                btnSusti.setVisibility(View.GONE);
                break;
            case 3: //Axiomas
                spinnerPremisas.setVisibility(View.GONE);
                spinnerPaso.setVisibility(View.GONE);
                spinnerPaso1.setVisibility(View.GONE);
                spinnerSustitucion.setVisibility(View.GONE);
                textExpre.setEnabled(true);
                textExpre.setVisibility(View.VISIBLE);
                textViewExpresion.setVisibility(View.VISIBLE);
                layoutOperadores.setVisibility(View.VISIBLE);
                btnValidar.setEnabled(true);
                btnValidar.setVisibility(View.VISIBLE);
                textSust1.setVisibility(View.GONE);
                textSust2.setVisibility(View.GONE);
                textViewSust1.setVisibility(View.GONE);
                textViewSust2.setVisibility(View.GONE);
                btnSusti.setVisibility(View.GONE);
                break;
            case 4: //Modus ponems
                spinnerPremisas.setVisibility(View.GONE);
                spinnerPaso.setEnabled(true);
                spinnerPaso1.setEnabled(true);
                spinnerPaso.setVisibility(View.VISIBLE);
                spinnerPaso1.setVisibility(View.VISIBLE);
                spinnerSustitucion.setVisibility(View.GONE);
                textExpre.setEnabled(false);
                textExpre.setVisibility(View.GONE);
                textViewExpresion.setVisibility(View.GONE);
                layoutOperadores.setVisibility(View.GONE);
                btnValidar.setEnabled(true);
                btnValidar.setVisibility(View.VISIBLE);
                textSust1.setVisibility(View.GONE);
                textSust2.setVisibility(View.GONE);
                textViewSust1.setVisibility(View.GONE);
                textViewSust2.setVisibility(View.GONE);
                btnSusti.setVisibility(View.GONE);
                break;
            case 5: //Supuesto
                spinnerPremisas.setVisibility(View.GONE);
                spinnerPaso.setEnabled(true);
                spinnerPaso1.setEnabled(true);
                spinnerPaso.setVisibility(View.VISIBLE);
                spinnerPaso1.setVisibility(View.VISIBLE);
                spinnerSustitucion.setVisibility(View.GONE);
                textExpre.setEnabled(true);
                textExpre.setVisibility(View.VISIBLE);
                textViewExpresion.setVisibility(View.VISIBLE);
                layoutOperadores.setVisibility(View.VISIBLE);
                btnValidar.setEnabled(true);
                btnValidar.setVisibility(View.VISIBLE);
                textSust1.setVisibility(View.GONE);
                textSust2.setVisibility(View.GONE);
                textViewSust1.setVisibility(View.GONE);
                textViewSust2.setVisibility(View.GONE);
                btnSusti.setVisibility(View.GONE);
                break;
            case 6: //Comenzar Demostracion
                layoutContenedorHip.setEnabled(false);
                layoutConclusion.setEnabled(false);
                añadirButton.setEnabled(false);
                textHip.setEnabled(false);
                textConcl.setEnabled(false);
                btnFijar.setEnabled(false);
                spinnerJustificacion.setEnabled(true);
                layoutContenedorHip.setVisibility(View.GONE);
                layoutConclusion.setVisibility(View.GONE);
                añadirButton.setVisibility(View.GONE);
                textHip.setVisibility(View.GONE);
                textConcl.setVisibility(View.GONE);
                btnFijar.setVisibility(View.GONE);
                nd_layout_justificacion.setVisibility(View.VISIBLE);
                //btnValidar.setVisibility(View.VISIBLE);
                btnInfo.setVisibility(View.GONE);
                textViewExpresion.setVisibility(View.VISIBLE);
                textViewBicondicional.setVisibility(View.GONE);
                textExpre.setEnabled(true);
                textExpre.setVisibility(View.VISIBLE);
                btnValidar.setVisibility(View.VISIBLE);
                btnValidar.setEnabled(true);
                layoutOperadores.setVisibility(View.VISIBLE);
                tablaDemostracion.setVisibility(View.VISIBLE);
                btnComprobarDemos.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void cargarAntecedentes(int j) {

        ArrayList antecedentes = new ArrayList();
        if(j==1){
            antecedentes= this.getHipotesis().getAntecedentes();
        }else {
            premisasArray.clear();
            premisasArray.add("Seleccione");//
            if(spinnerBicondicional.getSelectedItemPosition()==1){
                antecedentes = hipotesis.getAntecedentes();
                //tablaDemostracion.setModel(modelo1); hacerlo distinto
                bicondicionalActual = 1;
                agregarPasos(1);
            }else if(spinnerBicondicional.getSelectedItemPosition()==2){
                antecedentes = hipotesis2.getAntecedentes();
                //tablaDemostracion.setModel(modelo2); hacerlo distinto
                bicondicionalActual = 2;
                agregarPasos(2);
            }else {
                bicondicionalActual = 0;
                return;
            }

        }
        //comboPremisas.addItem(new String("Seleccione"));
        for (int i = 0; i < antecedentes.size(); i++) {
            premisasArray.add(antecedentes.get(i).toString());
        }
    }

    private void agregarPasos(int pasos){
        spinnerPaso.removeAllViews();
        spinnerPaso1.removeAllViews();
        if(pasos==1){
            for(int i=1; i<=pasoDemostracion; i++){
                pasoArray.add(Integer.toString(i));
                paso1Array.add(Integer.toString(i));
            }
        }else {
            for(int i=1; i<=pasoDemostracion2; i++){
                pasoArray.add(Integer.toString(i));
                paso1Array.add(Integer.toString(i));
            }
        }
    }

    public int getPasoDemostracion() {
        return pasoDemostracion;
    }

    public void setPasoDemostracion(int pasoDemostracion) {
        this.pasoDemostracion = pasoDemostracion;
    }

    public Hipotesis getHipotesis() {
        return hipotesis;
    }

    public void setHipotesis(Hipotesis hipotesis) {
        this.hipotesis = hipotesis;
    }

    public void antecedentesConsecuentes(String expresion){
        String[] fbfs = expresion.split("↔");
        try {
            antecedentes.add(new FBF(fbfs[0]));
            antecedentes.add(new FBF(fbfs[1]));
            consecuentes.add(new FBF(fbfs[1] + "→" + fbfs[0]));
            consecuentes.add(new FBF(fbfs[0] + "→" + fbfs[1]));
        } catch (Exception ex) {
            //Logger.getLogger(PantallaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public boolean detectarBiCondicional(String expresion){
        if(expresion.contains("↔")){
            return true;
        }
        return false;
    }

    private void cargarDemostracionesBicondicional(){

        String demostracion;
        demostracion = "⊢" + antecedentes.get(0)+ "→" + antecedentes.get(1);
        bicondicionalArray.add(demostracion);
        hipotesis = new Hipotesis(demostracion);
        demostracion = "⊢" + antecedentes.get(1)+ "→" + antecedentes.get(0);
        bicondicionalArray.add(demostracion);
        hipotesis2 = new Hipotesis(demostracion);
    }

    private static ArrayList<View> getViewsByTag(ViewGroup root, String tag){
        ArrayList<View> views = new ArrayList<View>();
        final int childCount = root.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = root.getChildAt(i);
            if (child instanceof ViewGroup) {
                views.addAll(getViewsByTag((ViewGroup) child, tag));
            }

            final Object tagObj = child.getTag();
            if (tagObj != null && tagObj.equals(tag)) {
                views.add(child);
            }

        }
        return views;
    }
    /*
    public void agregarFBF(String expresion, String justificacion){
        DefaultTableModel model = (DefaultTableModel) tablaDemostracion.getModel();
        if(esBicondicional){
            if(bicondicionalActual==1){
                setPasoDemostracion(getPasoDemostracion()+1);
                model.addRow(new Object[]{pasoDemostracion,expresion , justificacion});
                agregarPasos(1);
            }else if(bicondicionalActual==2){
                pasoDemostracion2+=1;
                model.addRow(new Object[]{pasoDemostracion2,expresion , justificacion});
                agregarPasos(2);
            }
        }else {
            setPasoDemostracion(getPasoDemostracion()+1);
            model.addRow(new Object[]{pasoDemostracion,expresion , justificacion});
            agregarPasos(1);
        }

    }
    */

    /*//Ocultar el teclado al dar ATRÁS
    @Override
    public void onBackPressed() {
        if(customKeyboard!=null && customKeyboard.isCustomKeyboardVisible() ) customKeyboard.hideCustomKeyboard(); else super.onBackPressed();
    }
    */
}