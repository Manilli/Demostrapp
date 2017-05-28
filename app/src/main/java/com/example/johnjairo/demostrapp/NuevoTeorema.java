package com.example.johnjairo.demostrapp;

import android.content.DialogInterface;
import android.database.MatrixCursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
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
import android.widget.GridView;
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
import com.example.johnjairo.demostrapp.vista.CustomKeyboard;
import com.example.johnjairo.demostrapp.vista.NothingSelectedSpinnerAdapter;


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
    private Button btnAñadirHip, btnEliminarHip, btnNegacion, btnAbrePar, btnCierraPar, btnFlecha,
            btnFlechaBi, btnDisy, btnConj, btnValidar, btnComprobarDemos, btnFijar, btnSusti, btnAñadirPaso,
            btnEliminarPaso, btnCancelarPaso;
    private ImageButton btnInfo;
    private Spinner spinnerPremisaConclusion, spinnerJustificacion, spinnerBicondicional, spinnerPremisas, spinnerSustitucion,
            spinnerPaso, spinnerPaso1;
    private static final String KEY_TEXT_VALUE = "booleana";
    private String[] columns = new String[] { "num", "expresion", "justificacion" };
    private LinearLayout ndMainLayout, nd_layout_justificacion, layoutContenedorHip, layoutConclusion, layoutOperadores,
            layoutOperadoresJustificacion, layoutInferencia;
    private RelativeLayout layoutHip;
    private TextView textViewNuevaDemosTitle, textViewExpresion, textViewSust1, textViewSust2, textViewBicondicional, textViewJustificacion;
    private TableLayout tablaDemostracion;
    CustomKeyboard customKeyboard;
    private Integer ic_Size, supuesto, bicondicionalActual=0, pasoDemostracion=0, pasoDemostracion2=0, banderaPremisas = 1, banderaConclusion = -1;
    private Axiomas axiomas;
    private Reglas reglas;
    private Hipotesis hipotesis, hipotesis2;
    private boolean esBicondicional;
    private ArrayList<FBF> antecedentes = new ArrayList<FBF>();
    private ArrayList<FBF> consecuentes = new ArrayList<FBF>();
    ArrayList<String> premisasArray = new ArrayList<String>();
    ArrayList<String> pasoArray = new ArrayList<String>();
    ArrayList<String> paso1Array = new ArrayList<String>();
    ArrayList<String> bicondicionalArray = new ArrayList<String>();
    ArrayList<String> reglaSustiArray = new ArrayList<String>();
    ArrayList<String> justificacionArray = new ArrayList<String>();
    ArrayList<Integer> banderaHipotesis = new ArrayList<>();
    ArrayList<String> pasosGridArray = new ArrayList<>();
    ArrayList<String> pasos2GridArray = new ArrayList<>();
    ArrayAdapter<String> pasos_Grid_Adapter;
    ArrayAdapter<String> pasos2_Grid_Adapter;
    MatrixCursor mcr = new MatrixCursor(columns);
    MatrixCursor mcrBC = new MatrixCursor(columns);
    MatrixCursor mcrBC2 = new MatrixCursor(columns);
    GridView gvDemostracion;

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
        btnAñadirHip = (Button) findViewById(R.id.btn_añadir);
        btnEliminarHip = (Button) findViewById(R.id.btn_eliminarHip);
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
        btnAñadirPaso = (Button) findViewById(R.id.btn_añadirPaso);
        btnEliminarPaso = (Button) findViewById(R.id.btn_eliminarPaso);
        btnCancelarPaso = (Button) findViewById(R.id.btn_cancelarPaso);
        textViewNuevaDemosTitle = (TextView) findViewById(R.id.nuevaDemosTitle);
        textViewNuevaDemosTitle.setSelected(true);
        textViewExpresion = (TextView) findViewById(R.id.textView_expresion);
        textViewSust1 = (TextView) findViewById(R.id.textView_sust1);
        textViewSust2 = (TextView) findViewById(R.id.textView_sust2);
        textViewBicondicional = (TextView) findViewById(R.id.textView_bicondicional);
        textViewJustificacion = (TextView) findViewById(R.id.textView_justificacion);
        gvDemostracion = (GridView)findViewById(R.id.gv_demos);
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
        //tablaDemostracion = (TableLayout) findViewById(R.id.Tabla);
        //premisasArray = (ArrayList) getResources().getStringArray(R.array.premisas_array);
        //final MatrixCursor mcr = new MatrixCursor(columns);
        startManagingCursor(mcr);

        // initialize the instance variable customKeyboard
        //customKeyboard = new CustomKeyboard(this, R.id.keyboardview, R.layout.keyboard);
        // register the edittext
        //customKeyboard.registerEditText(R.id.editText_hipotesis);

        //Ocultamos los elementos a usaremos luego de establecer premisa y conclusión
        btnEliminarHip.setVisibility(View.GONE);
        nd_layout_justificacion.setVisibility(View.GONE);
        btnValidar.setVisibility(View.GONE);
        textViewExpresion.setVisibility(View.GONE);
        textExpre.setVisibility(View.GONE);
        //tablaDemostracion.setVisibility(View.GONE);
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

        pasoArray.add("Paso");
        // Esto es para el spinnerPaso
        ArrayAdapter<String> paso_Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, pasoArray);
        paso_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPaso.setAdapter(new NothingSelectedSpinnerAdapter(
                paso_Adapter,
                R.layout.contact_spinner_row_nothing_selected,
                // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                this));
        pasoArray.clear();

        paso1Array.add("Paso");
        // Esto es para el spinnerPaso1
        ArrayAdapter<String> paso1_Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, paso1Array);
        paso1_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPaso1.setAdapter(new NothingSelectedSpinnerAdapter(
                paso1_Adapter,
                R.layout.contact_spinner_row_nothing_selected,
                // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                this));
        paso1Array.clear();

        bicondicionalArray.add("Seleccione");
        // Esto es para el spinnerBicondicional
        ArrayAdapter<String> bicond_Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, bicondicionalArray);
        bicond_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBicondicional.setAdapter(bicond_Adapter);

        reglaSustiArray.add("Seleccione");
        reglaSustiArray.add("RFP5");
        reglaSustiArray.add("RFP6");
        reglaSustiArray.add("RFP7");
        // Esto es para el spinner de la regla de sustitución
        ArrayAdapter<String> sustitu_Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, reglaSustiArray);
        sustitu_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSustitucion.setAdapter(sustitu_Adapter);

        pasos_Grid_Adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,pasosGridArray);
        pasos_Grid_Adapter.setDropDownViewResource(R.layout.activity_nuevoteorema);

        pasos2_Grid_Adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,pasos2GridArray);
        pasos2_Grid_Adapter.setDropDownViewResource(R.layout.activity_nuevoteorema);

        //Bloqueamos entrada de texto en la inferencia
        textInf.setKeyListener(null);
        deshabilitarComponentes(0);

        final Integer buttonId = new Integer(0);


        btnAñadirHip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("OnClick", "OnClickxd");
                totalEditTexts++;

                final RelativeLayout layoutHipCopy = new RelativeLayout(layoutHip.getContext());
                final EditText textHipCopy = new AppCompatEditText(NuevoTeorema.this);
                final Button buttonElimHip = new AppCompatButton(NuevoTeorema.this);

                btnEliminarHip.setVisibility(View.GONE);
                layoutHipCopy.setLayoutParams(layoutHip.getLayoutParams());
                textHipCopy.setLayoutParams(textHip.getLayoutParams());
                buttonElimHip.setLayoutParams(btnEliminarHip.getLayoutParams());

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

                buttonElimHip.setWidth(btnEliminarHip.getWidth());
                buttonElimHip.setHeight(btnEliminarHip.getHeight());
                buttonElimHip.setText("-");
                buttonElimHip.setTextColor(btnEliminarHip.getTextColors());
                buttonElimHip.setTextSize(30);
                buttonElimHip.setGravity(btnEliminarHip.getGravity());
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
                            btnAñadirHip.setEnabled(false);
                            textHip.setEnabled(false);
                            nd_layout_justificacion.setVisibility(View.VISIBLE);
                            btnValidar.setVisibility(View.VISIBLE);
                            textViewExpresion.setVisibility(View.VISIBLE);
                            textExpre.setVisibility(View.VISIBLE);
                            //tablaDemostracion.setVisibility(View.VISIBLE);
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
                    if (esBicondicional){
                        Toast.makeText(NuevoTeorema.this, "Recuerde que no hay premisas", Toast.LENGTH_SHORT).show();
                        spinnerJustificacion.setSelection(0);
                    }
                    else {
                        deshabilitarComponentes(2);
                        btnInfo.setVisibility(View.GONE);
                    }
                } else if(spinnerJustificacion.getSelectedItem().toString().contains("Axioma")){
                    deshabilitarComponentes(3);
                    btnInfo.setVisibility(View.VISIBLE);
                    textExpre.setText("");
                }else if(spinnerJustificacion.getSelectedItem().toString().contains("Sustitución")){
                    deshabilitarComponentes(1);
                    btnInfo.setVisibility(View.VISIBLE);
                    textExpre.setText("");
                }else if (spinnerJustificacion.getSelectedItem().equals("Modus Ponems")){
                    deshabilitarComponentes(4);
                    btnInfo.setVisibility(View.VISIBLE);
                    textExpre.setText("");
                    /*spinnerPaso1.removeAllViews();
                    for(int i=0; i<spinnerPaso.getCount(); i++){
                        spinnerPaso1.addView(spinnerPaso.getChildAt(i));
                    }*/
                }
                else if(spinnerJustificacion.getSelectedItem().toString().contains("Supuesto")){
                    deshabilitarComponentes(5);
                    btnInfo.setVisibility(View.GONE);
                    textExpre.setText("");
                }else if (spinnerJustificacion.getSelectedItem().equals("Seleccione")){
                    deshabilitarComponentes(3);
                    btnInfo.setVisibility(View.GONE);
                    textExpre.setText("");
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

        spinnerPremisas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView <?> parent, View view, int position,long id) {
                textExpre.setText(spinnerPremisas.getSelectedItem().toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        spinnerBicondicional.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView <?> parent, View view, int position,long id) {
                cargarAntecedentes(2);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        btnFijar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String expresion = "";//textConcl.getText().toString();
                ArrayList<View> viewsPremisas = new ArrayList<View>();
                viewsPremisas = getViewsByTag(layoutInferencia,"premisa");

                try {
                    /*if (expresion.charAt(0)=='⊢'){
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
                        //hipotesis = new Hipotesis(expresion);
                        //cargarAntecedentes(1);
                    }*/
                    //FBF f  = new FBF(expresion);
                    //text1.setBackground(Color.GREEN);
                    //text1.setEnabled(false);


                    if (viewsPremisas.size() == 0){
                        if(detectarBiCondicional(textConcl.getText().toString().substring(1))){
                            textViewNuevaDemosTitle.setText(" |- " + textConcl.getText().toString());
                            expresion = "⊢" + textConcl.getText().toString();
                        } else {
                            Toast.makeText(NuevoTeorema.this, "Hay errores en la expresión.", LENGTH_SHORT).show();
                            return;
                        }
                    }
                    else {
                        for (int i=0; i<=viewsPremisas.size(); i++){
                            EditText e = (EditText)viewsPremisas.get(i);
                            String textopremisa = e.getText().toString();
                            try{
                                FBF f = new FBF(textopremisa);
                                //ArrayList<View> viewsPremisas = new ArrayList<View>();
                                //viewsPremisas = getViewsByTag(layoutInferencia,"premisa");
                                //Agregamos las premisas
                                //premisasArray.add(e.getText().toString());
                                //int j = viewsPremisas.size();
                                //Toast.makeText(NuevoTeorema.this, Integer.toString(viewsPremisas.size()), LENGTH_SHORT).show();
                                banderaPremisas = 1;

                                if (i == viewsPremisas.size()-1){
                                    textViewNuevaDemosTitle.setText(textViewNuevaDemosTitle.getText().toString() + e.getText().toString() + " |- " + textConcl.getText().toString());
                                    expresion = expresion +  e.getText().toString() + "⊢" + textConcl.getText().toString();

                                } else {
                                    textViewNuevaDemosTitle.setText(textViewNuevaDemosTitle.getText().toString() + e.getText().toString() + ", ");
                                    expresion = expresion +  e.getText().toString() + ",";
                                }

                            }catch(Exception exc){
                                Toast.makeText(NuevoTeorema.this, "Hay errores de sintaxis.\n" + "Corríjalos para poder continuar.", LENGTH_SHORT).show();
                                banderaPremisas = -1;
                                premisasArray.clear();
                                premisasArray.add("Seleccione");
                                textViewNuevaDemosTitle.setText("");
                                break;
                            }
                        }
                    }
                } catch (Exception e) {
                    //text1.setBackground(Color.red); ERROR
                }
                try {
                    FBF f = new FBF(textConcl.getText().toString());
                    banderaConclusion = 1;
                }
                catch (Exception ex){
                    Toast.makeText(NuevoTeorema.this, "Hay errores de sintaxis.\n" + "Corríjalos para poder continuar.", LENGTH_SHORT).show();
                    banderaConclusion = -1;
                    premisasArray.clear();
                    premisasArray.add("Seleccione");
                    textViewNuevaDemosTitle.setText("");
                }
                if ((banderaPremisas != -1) && (banderaConclusion != -1)){
                    deshabilitarComponentes(7);
                    ndMainLayout.removeView(layoutOperadores);
                    layoutOperadoresJustificacion.addView(layoutOperadores);
                    if (viewsPremisas.size() == 0){
                        if(detectarBiCondicional(expresion.substring(1))){
                            esBicondicional= true;
                            antecedentesConsecuentes(expresion.substring(1));
                            cargarDemostracionesBicondicional();
                            cargarAntecedentes(2);
                            spinnerBicondicional.setVisibility(View.VISIBLE);
                            textViewBicondicional.setVisibility(View.VISIBLE);
                        } else {
                            //Toast.makeText(NuevoTeorema.this, "Hay errores en la expresión.", LENGTH_SHORT).show();
                            return;
                        }
                    }
                    else {
                        hipotesis = new Hipotesis(expresion);
                        cargarAntecedentes(1);
                    }
                    //Toast.makeText(NuevoTeorema.this, expresion, LENGTH_SHORT).show();
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
                                + "considera bien formada y se define como: ¬r∨s";
                        mostrar=true;
                    }else if(spinnerSustitucion.getSelectedItem().equals("RFP7")){
                        mensaje ="Definición de formas proposicionales bicondicionales \n "
                                + "Sean r y s fbfs, entonces la fórmula r↔s se \n"
                                + "considera bien formada y se define como: (r→s)∧(s→r)";
                        mostrar=true;
                    }
                        break;
                    case "Modus Ponems": mensaje="p, p→q |- q";
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

                    final AlertDialog alertDialog = builder.create();
                    alertDialog.setOnShowListener( new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(DialogInterface arg0) {
                            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(NuevoTeorema.this, R.color.primary_text));
                        }
                    });
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
                //Toast.makeText(NuevoTeorema.this, expresion + " " + justificacion, LENGTH_SHORT).show();
                if(!stringValidar.equals("")){
                    Toast.makeText(NuevoTeorema.this, stringValidar, LENGTH_SHORT).show();
                    //JOptionPane.showMessageDialog(this, stringValidar);
                    return;
                }
                try {
                    FBF f = new FBF(expresion);
                    boolean v = true;
                    if(spinnerJustificacion.getSelectedItem().toString().contains("Premisa")){
                        error=false;
                        expresion = spinnerPremisas.getSelectedItem().toString();
                        justificacion = "Premisa";
                        //Toast.makeText(NuevoTeorema.this, expresion + " " + justificacion, LENGTH_SHORT).show();
                    }else if(spinnerJustificacion.getSelectedItem().toString().contains("Supuesto")){
                        error=false;
                        supuesto=1;
                        justificacion = "Supuesto";
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
                    }else if(spinnerJustificacion.getSelectedItem().toString().contains("Modus Ponems")){
                        //Toast.makeText(NuevoTeorema.this, "Modus Ponems Debug ENTRÓ", LENGTH_SHORT).show();
                        int paso1= Integer.parseInt(spinnerPaso.getSelectedItem().toString());
                        int paso2= Integer.parseInt(spinnerPaso1.getSelectedItem().toString());
                        int i = 1, j = 1;
                        String expresion1= "";//tablaDemostracion.getValueAt(paso1-1, 1).toString(); Hay que hacerlo de modo distinto
                        String expresion2= "";//tablaDemostracion.getValueAt(paso2-1, 1).toString(); Hay que hacerlo de modo distinto
                        mcr.moveToFirst();
                        if (mcr != null && mcr.moveToFirst()){
                            do {
                                if (paso1 == i){
                                    expresion1= mcr.getString(1);
                                }
                                if (paso2 == j){
                                    expresion2= mcr.getString(1);
                                }
                                i++;
                                j++;
                            } while (mcr.moveToNext());
                        }
                        //Toast.makeText(NuevoTeorema.this, "Modus Ponems Debug " + expresion1 + " " + expresion2, LENGTH_SHORT).show();
                        String ponem= reglas.modusPonems(expresion1, expresion2);
                        if(ponem!=null && ponem.equals(expresion)){
                            expresion = ponem;
                            justificacion= "Modus Ponems entre "+ paso1 + " y " + paso2;
                        }else {
                            error=true;
                            //JOptionPane.showMessageDialog(this, "No es posible realizar modus ponems entre los pasos seleccionados");
                            Toast.makeText(NuevoTeorema.this, "Error al usar Modus Ponems. Revise los pasos o la expresión ingresada", LENGTH_SHORT).show();
                        }
                    } else {
                        String s = comprobarTeorema(spinnerJustificacion.getSelectedItem().toString());
                        if(s.equals("")){
                            error=true;
                        } else {
                            justificacion += s;
                        }
                    }

                    if(!error){
                        //Toast.makeText(NuevoTeorema.this, ":)", LENGTH_SHORT).show();
                        if(v){


                            //mcr.moveToFirst();

                            /*try {
                                if(mcr!=null) {
                                    if (mcr.moveToFirst()) {
                                        do {

                                        }while (mcr.moveToNext());
                                    }
                                }
                            }catch (Exception e){

                            }*/

                            agregarFBF(mcr, expresion, justificacion);  //Hay que migrar a una solución diferente para manejar la tabla
                            deshabilitarComponentes(7);
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

        btnSusti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String expresion="";
                int paso= Integer.parseInt(spinnerPaso.getSelectedItem().toString());
                int i = 1;
                mcr.moveToFirst();
                if (mcr != null && mcr.moveToFirst()){
                    do {
                        if (paso == i){
                            expresion= mcr.getString(1);
                        }
                        i++;
                    } while (mcr.moveToNext());
                }
                //Toast.makeText(NuevoTeorema.this, spinnerPaso.getSelectedItem().toString(), LENGTH_SHORT).show();

                boolean v;
                v= false;
                String stringValidar="";
                stringValidar= validar();
                if(!stringValidar.equals("")){
                    Toast.makeText(NuevoTeorema.this, stringValidar, LENGTH_SHORT).show();
                    //JOptionPane.showMessageDialog(this, stringValidar);
                    return;
                }
                //int paso= Integer.parseInt(spinnerPaso.getSelectedItem().toString());
                //Toast.makeText(NuevoTeorema.this, expresion, LENGTH_SHORT).show();
                //String expresion= tablaDemostracion.getValueAt(paso-1, 1).toString();
                String expresion2 = textSust1.getText().toString();
                String expresion3 = textSust2.getText().toString();
                if(!expresion.contains(expresion2)){
                    Toast.makeText(NuevoTeorema.this, "La expresion no esta contenida en el paso seleccionado", LENGTH_SHORT).show();
                    return;
                }
                try {
                    String item = spinnerSustitucion.getSelectedItem().toString();
                    FBF g = new FBF(expresion3);
                    FBF f= new FBF(expresion2);

                    switch (item) {
                        case "RFP5":
                            v = reglas.validar(5, f, g);
                            break;
                        case "RFP6":
                            v = reglas.validar(6, f, g);
                            break;
                        case "RFP7":
                            v = reglas.validar(7, f, g);
                            break;
                        case "Conmutatividad":
                            v= reglas.validar(8, f, g);
                            break;
                    }
                } catch (Exception ex) {
                    //Logger.getLogger(PantallaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
                if(v){
                    expresion= expresion.replace(expresion2, expresion3);
                    //Toast.makeText(NuevoTeorema.this, "Bien!", LENGTH_SHORT).show();
                    agregarFBF(mcr, expresion,"Sustitucion en el paso: " + paso +  " \n con la regla" + spinnerSustitucion.getSelectedItem());
                    //textSust1.setBackground(Color.GREEN);
                } else {
                    //textSust1.setBackground(Color.RED);
                }
            }
        });

        btnComprobarDemos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mensaje = "";
                if(esBicondicional){
                    //tablaDemostracion.setModel(modelo1);
                    mcr = mcrBC;
                    if(comprobarDemostracion(mcr, hipotesis.getConse())){
                        //ablaDemostracion.setModel(modelo2);
                        mcr = mcrBC2;
                        if(comprobarDemostracion(mcr, hipotesis2.getConse())){
                            if(supuesto==1){
                                //JOptionPane.showMessageDialog(this, "La demostracion se realizo correctamente pero recuerde \n"
                                //        + " que hizo uso de supuestos durante el proceso demostrativo");
                                mensaje = "La demostracion se realizo correctamente pero recuerde \n"
                                        + " que hizo uso de supuestos durante el proceso demostrativo";
                            }else {
                                //JOptionPane.showMessageDialog(this, "La demostracion se realizo correctamente");
                                mensaje = "La demostracion se realizo correctamente";
                            }

                            //deshabilitarComponentes(0);
                            //guardarDemostracionBicondicional(hipotesis.getConse(), hipotesis2.getConse(), text1.getText());
                        }else {
                            //JOptionPane.showMessageDialog(this, "La demostracion esta inconclusa");
                            Toast.makeText(NuevoTeorema.this, "La demostracion esta inconclusa", LENGTH_SHORT).show();
                            return;
                        }
                    }else {
                        //JOptionPane.showMessageDialog(this, "La demostracion esta inconclusa");
                        Toast.makeText(NuevoTeorema.this, "La demostracion esta inconclusa", LENGTH_SHORT).show();
                        return;
                    }
                }else {
                    if(comprobarDemostracion(mcr, hipotesis.getConse())){
                        if(supuesto==1){
                            mensaje = "La demostracion se realizo correctamente pero recuerde \n"
                                    + " que hizo uso de supuestos durante el proceso demostrativo";
                            //JOptionPane.showMessageDialog(this, "La demostracion se realizo correctamente pero recuerde \n"
                            //        + " que hizo uso de supuestos durante el proceso demostrativo");
                        }else {
                            //JOptionPane.showMessageDialog(this, "La demostracion se realizo correctamente");
                            mensaje = "La demostracion se realizo correctamente";
                        }

                        //deshabilitarComponentes(0);
                        //guardarDemostracion(hipotesis.getAnte(), hipotesis.getConse(), text1.getText());
                    }else {
                        //JOptionPane.showMessageDialog(this, "La demostracion esta inconclusa");
                        Toast.makeText(NuevoTeorema.this, "La demostracion esta inconclusa", LENGTH_SHORT).show();
                        return;
                    }
                }
                AlertDialog.Builder builderTeoremaComprobado = new AlertDialog.Builder(NuevoTeorema.this);

                builderTeoremaComprobado.setTitle("FELICIDADES");

                builderTeoremaComprobado.setMessage(mensaje)
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

                final AlertDialog alertDialogTeoremaComprobado = builderTeoremaComprobado.create();
                alertDialogTeoremaComprobado.setOnShowListener( new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface arg0) {
                        alertDialogTeoremaComprobado.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(NuevoTeorema.this, R.color.primary_text));
                    }
                });
                alertDialogTeoremaComprobado.show();
            }
        });
        btnAñadirPaso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deshabilitarComponentes(6);
                spinnerJustificacion.setSelection(0);
            }
        });
        btnCancelarPaso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deshabilitarComponentes(7);
                textExpre.setText("");
                spinnerJustificacion.setSelection(0);
            }
        });
        /*btnEliminarPaso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pasoArray.clear();
                paso1Array.clear();
                MatrixCursor newmcr = new MatrixCursor(columns);
                if (mcr.moveToFirst()) {
                    do {
                        // skip the copy of this one ....
                        if (mcr.getString(0).equals(Integer.toString(pasoDemostracion)))
                            continue;
                        newmcr.addRow(new Object[]{mcr.getString(0), mcr.getString(1), mcr.getString(2)});
                        agregarPasos(1);
                    } while (mcr.moveToNext());
                }
            }
        });*/
    }

    public boolean comprobarDemostracion(MatrixCursor mcr, String conse){
        try {
            //int i =tablaDemostracion.getRowCount()-1;
            String expresion = "";
            String tipoExpresion = "";
            int k = mcr.getCount()-1;
            int i = 0;
            mcr.moveToFirst();
            if (mcr != null && mcr.moveToFirst()){
                do {
                    if (k == i){
                        expresion = mcr.getString(1);
                        tipoExpresion = mcr.getString(2);
                    }
                    i++;
                } while (mcr.moveToNext());
            }
            //String expresion = tablaDemostracion.getValueAt(i, 1).toString();
            //String tipoExpresion = tablaDemostracion.getValueAt(i, 2).toString();
            if(expresion.equals(conse) || expresion.equals("("+conse+")")){
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            Toast.makeText(NuevoTeorema.this, "La demostracion esta inconclusa", LENGTH_SHORT).show();
            //JOptionPane.showMessageDialog(this, "La demostracion esta inconclusa");
        }
        return false;
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
                return "Seleccione una premisa";
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
            if(textExpre.getText().toString().equals("")){
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

    public void deshabilitarComponentes(int caso){
        switch (caso){
            case 0: // Al principio
                textExpre.setEnabled(false);
                textHip.setEnabled(false);
                textHip.setVisibility(View.GONE);
                spinnerPaso.setEnabled(false);
                spinnerPaso1.setEnabled(false);
                spinnerJustificacion.setEnabled(false);
                btnValidar.setEnabled(false);
                btnValidar.setVisibility(View.GONE);
                btnCancelarPaso.setVisibility(View.GONE);
                btnInfo.setVisibility(View.GONE);
                btnSusti.setVisibility(View.GONE);
                btnComprobarDemos.setVisibility(View.GONE);
                btnAñadirPaso.setVisibility(View.GONE);
                btnEliminarPaso.setVisibility(View.GONE);
                spinnerPremisas.setVisibility(View.GONE);
                spinnerJustificacion.setVisibility(View.GONE);
                spinnerSustitucion.setVisibility(View.GONE);
                spinnerPaso1.setVisibility(View.GONE);
                spinnerPaso.setVisibility(View.GONE);
                spinnerBicondicional.setVisibility(View.GONE);
                textViewBicondicional.setVisibility(View.GONE);
                textSust1.setVisibility(View.GONE);
                textSust2.setVisibility(View.GONE);
                textViewJustificacion.setVisibility(View.GONE);
                textViewSust1.setVisibility(View.GONE);
                textViewSust2.setVisibility(View.GONE);
                gvDemostracion.setVisibility(View.GONE);

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
                btnCancelarPaso.setVisibility(View.VISIBLE);
                btnSusti.setVisibility(View.VISIBLE);
                btnAñadirPaso.setVisibility(View.GONE);
                btnEliminarPaso.setVisibility(View.GONE);
                textSust1.setVisibility(View.VISIBLE);
                textSust2.setVisibility(View.VISIBLE);
                textViewSust1.setVisibility(View.VISIBLE);
                textViewSust2.setVisibility(View.VISIBLE);
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
                btnCancelarPaso.setVisibility(View.VISIBLE);
                btnSusti.setVisibility(View.GONE);
                btnAñadirPaso.setVisibility(View.GONE);
                btnEliminarPaso.setVisibility(View.GONE);
                textSust1.setVisibility(View.GONE);
                textSust2.setVisibility(View.GONE);
                textViewSust1.setVisibility(View.GONE);
                textViewSust2.setVisibility(View.GONE);
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
                btnCancelarPaso.setVisibility(View.VISIBLE);
                btnSusti.setVisibility(View.GONE);
                btnAñadirPaso.setVisibility(View.GONE);
                btnEliminarPaso.setVisibility(View.GONE);
                textSust1.setVisibility(View.GONE);
                textSust2.setVisibility(View.GONE);
                textViewSust1.setVisibility(View.GONE);
                textViewSust2.setVisibility(View.GONE);
                break;
            case 4: //Modus ponems
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
                btnCancelarPaso.setVisibility(View.VISIBLE);
                btnSusti.setVisibility(View.GONE);
                btnAñadirPaso.setVisibility(View.GONE);
                btnEliminarPaso.setVisibility(View.GONE);
                textSust1.setVisibility(View.GONE);
                textSust2.setVisibility(View.GONE);
                textViewSust1.setVisibility(View.GONE);
                textViewSust2.setVisibility(View.GONE);
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
                btnCancelarPaso.setVisibility(View.VISIBLE);
                btnSusti.setVisibility(View.GONE);
                btnAñadirPaso.setVisibility(View.GONE);
                btnEliminarPaso.setVisibility(View.GONE);
                textSust1.setVisibility(View.GONE);
                textSust2.setVisibility(View.GONE);
                textViewSust1.setVisibility(View.GONE);
                textViewSust2.setVisibility(View.GONE);
                break;
            case 6: //Agregar Paso
                layoutContenedorHip.setEnabled(false);
                layoutConclusion.setEnabled(false);
                btnAñadirHip.setEnabled(false);
                textHip.setEnabled(false);
                textConcl.setEnabled(false);
                btnFijar.setEnabled(false);
                btnFijar.setVisibility(View.GONE);
                btnAñadirHip.setVisibility(View.GONE);
                btnValidar.setEnabled(true);
                btnValidar.setVisibility(View.VISIBLE);
                btnCancelarPaso.setVisibility(View.VISIBLE);
                btnInfo.setVisibility(View.GONE);
                btnComprobarDemos.setVisibility(View.VISIBLE);
                btnAñadirPaso.setVisibility(View.GONE);
                btnEliminarPaso.setVisibility(View.GONE);
                spinnerJustificacion.setEnabled(true);
                layoutContenedorHip.setVisibility(View.GONE);
                layoutConclusion.setVisibility(View.GONE);
                textHip.setVisibility(View.GONE);
                textConcl.setVisibility(View.GONE);
                nd_layout_justificacion.setVisibility(View.VISIBLE);
                //btnValidar.setVisibility(View.VISIBLE);
                textViewExpresion.setVisibility(View.VISIBLE);
                textViewJustificacion.setVisibility(View.VISIBLE);
                textExpre.setEnabled(true);
                textExpre.setVisibility(View.VISIBLE);
                spinnerJustificacion.setVisibility(View.VISIBLE);
                layoutOperadores.setVisibility(View.VISIBLE);
                //tablaDemostracion.setVisibility(View.VISIBLE);
                gvDemostracion.setVisibility(View.VISIBLE);
                break;
            case 7: //Comenzar Demostracion
                layoutContenedorHip.setEnabled(false);
                layoutConclusion.setEnabled(false);
                btnAñadirHip.setEnabled(false);
                textConcl.setEnabled(false);
                btnFijar.setEnabled(false);
                spinnerJustificacion.setEnabled(true);
                layoutContenedorHip.setVisibility(View.GONE);
                layoutConclusion.setVisibility(View.GONE);
                btnAñadirHip.setVisibility(View.GONE);
                textConcl.setVisibility(View.GONE);
                btnFijar.setVisibility(View.GONE);
                nd_layout_justificacion.setVisibility(View.GONE);
                //btnValidar.setVisibility(View.VISIBLE);
                textViewExpresion.setVisibility(View.GONE);
                textViewJustificacion.setVisibility(View.GONE);
                textExpre.setEnabled(true);
                textExpre.setVisibility(View.GONE);
                btnValidar.setVisibility(View.GONE);
                btnInfo.setVisibility(View.GONE);
                spinnerJustificacion.setVisibility(View.GONE);
                btnValidar.setEnabled(true);
                layoutOperadores.setVisibility(View.GONE);
                //tablaDemostracion.setVisibility(View.VISIBLE);
                gvDemostracion.setVisibility(View.VISIBLE);
                btnComprobarDemos.setVisibility(View.VISIBLE);
                btnCancelarPaso.setVisibility(View.GONE);
                btnAñadirPaso.setVisibility(View.VISIBLE);
                btnEliminarPaso.setVisibility(View.VISIBLE);
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
                mcr = mcrBC;
                bicondicionalActual = 1;
                gvDemostracion.setAdapter(pasos_Grid_Adapter);
                agregarPasos(1);
            }else if(spinnerBicondicional.getSelectedItemPosition()==2){
                antecedentes = hipotesis2.getAntecedentes();
                //tablaDemostracion.setModel(modelo2); hacerlo distinto
                mcr = mcrBC2;
                bicondicionalActual = 2;
                gvDemostracion.setAdapter(pasos2_Grid_Adapter);
                agregarPasos(2);
            }else {
                bicondicionalActual = 0;
                return;
            }

        }
        //comboPremisas.addItem(new String("Seleccione"));
        for (int i = 0; i < antecedentes.size(); i++) {
            premisasArray.add(antecedentes.get(i).toString());
            Log.i("Premisa", antecedentes.get(i).toString());
        }
    }

    private void agregarPasos(int pasos){
        //spinnerPaso.removeAllViews();
        //spinnerPaso1.removeAllViews();
        pasoArray.clear();
        paso1Array.clear();
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
        bicondicionalArray.add("|- " + antecedentes.get(0)+ "→" + antecedentes.get(1));
        hipotesis = new Hipotesis(demostracion);
        demostracion = "⊢" + antecedentes.get(1)+ "→" + antecedentes.get(0);
        bicondicionalArray.add("|- " + antecedentes.get(1)+ "→" + antecedentes.get(0));
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

    public void agregarFBF(MatrixCursor mcr, String expresion, String justificacion){
        //DefaultTableModel model = (DefaultTableModel) tablaDemostracion.getModel();
        if(esBicondicional){
            if(bicondicionalActual==1){
                textExpre.setText("");
                setPasoDemostracion(getPasoDemostracion()+1);
                mcr.addRow(new Object[]{pasoDemostracion,expresion , justificacion});
                pasosGridArray.add(Integer.toString(pasoDemostracion));
                pasosGridArray.add(expresion);
                pasosGridArray.add(justificacion);
                gvDemostracion.setAdapter(pasos_Grid_Adapter);
                agregarPasos(1);
            }else if(bicondicionalActual==2){
                textExpre.setText("");
                pasoDemostracion2+=1;
                mcr.addRow(new Object[]{pasoDemostracion2,expresion , justificacion});
                pasos2GridArray.add(Integer.toString(pasoDemostracion));
                pasos2GridArray.add(expresion);
                pasos2GridArray.add(justificacion);
                gvDemostracion.setAdapter(pasos2_Grid_Adapter);
                agregarPasos(2);
            }
        }else {
            //setPasoDemostracion(getPasoDemostracion()+1);
            //mcr.addRow(new Object[]{pasoDemostracion,expresion , justificacion});
            //agregarPasos(1);

            textExpre.setText("");
            setPasoDemostracion(getPasoDemostracion()+1);
            mcr.addRow(new Object[] { pasoDemostracion , expresion, justificacion });
            pasosGridArray.add(Integer.toString(pasoDemostracion));
            pasosGridArray.add(expresion);
            pasosGridArray.add(justificacion);
            gvDemostracion.setAdapter(pasos_Grid_Adapter);
            agregarPasos(1);
        }

    }


    /*//Ocultar el teclado al dar ATRÁS
    @Override
    public void onBackPressed() {
        if(customKeyboard!=null && customKeyboard.isCustomKeyboardVisible() ) customKeyboard.hideCustomKeyboard(); else super.onBackPressed();
    }
    */
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
        btnAñadirHip.setEnabled(a);
        if (a){
        }
        else {
            nd_layout_justificacion.setVisibility(View.VISIBLE);
            btnValidar.setVisibility(View.VISIBLE);
            textViewExpresion.setVisibility(View.VISIBLE);
            textExpre.setVisibility(View.VISIBLE);
            //tablaDemostracion.setVisibility(View.VISIBLE);
            btnComprobarDemos.setVisibility(View.VISIBLE);
        }

    }
}