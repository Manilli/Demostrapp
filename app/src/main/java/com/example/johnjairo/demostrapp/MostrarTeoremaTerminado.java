package com.example.johnjairo.demostrapp;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.johnjairo.demostrapp.modelo.AdapterPaso;
import com.example.johnjairo.demostrapp.modelo.Paso;
import com.example.johnjairo.demostrapp.vista.Item_paso;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MostrarTeoremaTerminado extends AppCompatActivity {
    ListView lv;
    TextView tvNombreTeorema;
    Spinner bicondicionalSpinner;
    ArrayList bicondicionalArray=new ArrayList<>();
    ArrayList<Item_paso> paso_lv_Array = new ArrayList<Item_paso>();
    ArrayList<Item_paso> paso2_lv_Array = new ArrayList<Item_paso>();
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user=mAuth.getCurrentUser();
    String hipotesis;
    String hipotesis2;
    Boolean banderaHipotesis;
    DatabaseReference mSistemaReference=mDatabase.child("usuarios").child(user.getUid()).child("Sistemas").child("Sistema Formal Proposicional");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_teorema_terminado);
        /*
        if(!getIntent().getExtras().getString("Hip 2").isEmpty()){
            hipotesis=getIntent().getExtras().getString("Hip 1");
            hipotesis2=getIntent().getExtras().getString("Hip 2");
            bicondicionalSpinner=(Spinner)findViewById(R.id.spn_seleccionarBicondicional);
            bicondicionalArray.add("Seleccione");
            bicondicionalArray.add("⊢" + hipotesis);
            bicondicionalArray.add("⊢" + hipotesis2);
            ArrayAdapter<String> bicond_Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, bicondicionalArray);
            bicond_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            bicondicionalSpinner.setAdapter(bicond_Adapter);
            bicondicionalSpinner.setVisibility(View.VISIBLE);
        }
        */
        tvNombreTeorema=(TextView)findViewById(R.id.tview_nombre_teorema);
        String nombreTeorema=getIntent().getExtras().getString("KeyTeorema");
        tvNombreTeorema.setText(nombreTeorema);
        lv = (ListView) findViewById(R.id.lv_demos);
        //pasosTeorema = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,pasosTeoremaArray);
        //pasosTeorema.setDropDownViewResource(R.layout.activity_nuevoteorema);
        DatabaseReference mTeoremaReference=mSistemaReference.child(nombreTeorema);
        Log.e("Key Teorema en mostrar",mTeoremaReference.getKey());

        mTeoremaReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Boolean esBicondicional=dataSnapshot.child("bicondicional").getValue(Boolean.class);
                if(esBicondicional){
                    hipotesis=dataSnapshot.child("hipotesis1").getValue(String.class);
                    hipotesis2=dataSnapshot.child("hipotesis2").getValue(String.class);
                    bicondicionalSpinner=(Spinner)findViewById(R.id.spn_seleccionarBicondicional);
                    bicondicionalArray.add(hipotesis);
                    bicondicionalArray.add(hipotesis2);
                    ArrayAdapter<String> bicond_Adapter = new ArrayAdapter<String>(MostrarTeoremaTerminado.this, android.R.layout.simple_spinner_dropdown_item, bicondicionalArray);
                    bicond_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    bicondicionalSpinner.setAdapter(bicond_Adapter);
                    bicondicionalSpinner.setVisibility(View.VISIBLE);
                    for(DataSnapshot snap:dataSnapshot.child("demostracion1").getChildren()){
                        GenericTypeIndicator<ArrayList<Paso>> t = new GenericTypeIndicator<ArrayList<Paso>>() {};
                        ArrayList<Paso> paso = snap.getValue(t);
                        Log.e("Demo en mostrar paso...",paso.get(0).getExpresion());

                        Item_paso itemPasoAux = new Item_paso("paso" + paso.get(0).getNumeroPaso().toString(), paso.get(0).getNumeroPaso().toString(), paso.get(0).getExpresion(), paso.get(0).getJustificacion());

                        paso_lv_Array.add(itemPasoAux);

                    }
                    for(DataSnapshot snap:dataSnapshot.child("demostracion2").getChildren()){
                        GenericTypeIndicator<ArrayList<Paso>> t = new GenericTypeIndicator<ArrayList<Paso>>() {};
                        ArrayList<Paso> paso = snap.getValue(t);
                        Log.e("Demo en mostrar paso...",paso.get(0).getExpresion());

                        Item_paso itemPasoAux = new Item_paso("paso" + paso.get(0).getNumeroPaso().toString(), paso.get(0).getNumeroPaso().toString(), paso.get(0).getExpresion(), paso.get(0).getJustificacion());

                        paso2_lv_Array.add(itemPasoAux);

                    }
                    bicondicionalSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            ((TextView) view).setTextColor(Color.BLACK);
                            if(bicondicionalSpinner.getSelectedItem().toString().equals(hipotesis)){
                                AdapterPaso adapter = new AdapterPaso(MostrarTeoremaTerminado.this, paso_lv_Array);
                                lv.setAdapter(adapter);

                            }
                            else{
                                AdapterPaso adapter = new AdapterPaso(MostrarTeoremaTerminado.this, paso2_lv_Array);
                                lv.setAdapter(adapter);
                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                }
                else{
                    for(DataSnapshot snap:dataSnapshot.child("demostracion1").getChildren()){
                        GenericTypeIndicator<ArrayList<Paso>> t = new GenericTypeIndicator<ArrayList<Paso>>() {};
                        ArrayList<Paso> paso = snap.getValue(t);
                        Log.e("Demo en mostrar paso...",paso.get(0).getExpresion());

                        Item_paso itemPasoAux = new Item_paso("paso" + paso.get(0).getNumeroPaso().toString(), paso.get(0).getNumeroPaso().toString(), paso.get(0).getExpresion(), paso.get(0).getJustificacion());

                        paso_lv_Array.add(itemPasoAux);
                    }
                    AdapterPaso adapter = new AdapterPaso(MostrarTeoremaTerminado.this, paso_lv_Array);
                    lv.setAdapter(adapter);

                }
                /*
                for(DataSnapshot snap:dataSnapshot.getChildren()){
                    GenericTypeIndicator<ArrayList<Paso>> t = new GenericTypeIndicator<ArrayList<Paso>>() {};
                    ArrayList<Paso> paso = snap.getValue(t);
                    Log.e("Demo en mostrar paso...",paso.get(0).getExpresion());
                    pasosTeoremaArray.add(paso.get(0).getNumeroPaso().toString());
                    pasosTeoremaArray.add(paso.get(0).getExpresion());
                    pasosTeoremaArray.add(paso.get(0).getJustificacion());

                }
                pasosTeoremaAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item,pasosTeoremaArray);
                pasosTeoremaAdapter.setDropDownViewResource(R.layout.activity_mostrar_teorema_terminado);
                gvTerminado.setAdapter(pasosTeoremaAdapter);
                */
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mSistemaReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // get total available quest
                Long size = dataSnapshot.getChildrenCount();
                Log.e("Cuantas demostraciones ", size.toString());
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void cargarDemostracionesBicondicional(){
        String demostracion;
        demostracion = "⊢" + hipotesis.toString();
        //bicondicionalArray.add("|- " + antecedentes.get(0)+ "→" + antecedentes.get(1));
        demostracion = "⊢" + hipotesis2.toString();
        //bicondicionalArray.add("|- " + antecedentes.get(1)+ "→" + antecedentes.get(0));
    }
}
