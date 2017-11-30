package com.example.johnjairo.demostrapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.johnjairo.demostrapp.modelo.Demostration;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedList;

public class ConfirmarNombreTeorema extends AppCompatActivity {

    EditText eTextNombreTeorema;
    Button btnCancelar;
    Button btnGuardar;
    String hipotesis;
    String hipotesis2;
    ArrayList<ArrayList>demostracion=new ArrayList<>();
    ArrayList<ArrayList>demostracion2=new ArrayList<>();
    boolean esTerminado=false;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user=mAuth.getCurrentUser();
    DatabaseReference mSistemaReference=mDatabase.child("usuarios").child(user.getUid()).child("Sistemas").child("Sistema Formal Proposicional");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_nombre_teorema);
        Log.e("Estoy","Estoy en confirmar nombre");
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            //demostracion=(LinkedList)extras.getSerializable("Demostracion");
            if(extras.getString("Terminada").equals("true")){
                esTerminado=true;
            }
            if(extras.getString("EsBicondicional").equals("true")){
                Log.e("confirmar", "estoy en confirmar bicond");
                hipotesis=extras.getString("Hip 1");
                hipotesis2=extras.getString("Hip 2");
                demostracion2=(ArrayList)getIntent().getSerializableExtra("Demostracion 2");
            }
        }
        demostracion=(ArrayList)getIntent().getSerializableExtra("Demostracion 1");
        eTextNombreTeorema=(EditText)findViewById(R.id.eTextNombreTeor);
        btnGuardar=(Button)findViewById(R.id.btn_Guardar);
        btnCancelar=(Button)findViewById(R.id.btn_Cancelar);

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSistemaReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String nombreTeorema=eTextNombreTeorema.getText().toString();
                        if(nombreTeorema.isEmpty()){
                            Toast.makeText(ConfirmarNombreTeorema.this,"Por favor ingresa un nombre", Toast.LENGTH_LONG).show();
                        }
                        else{
                            if(dataSnapshot.hasChild(nombreTeorema)){
                                Toast.makeText(ConfirmarNombreTeorema.this,"El nombre de teorema ya está en uso, por favor selecciona otro", Toast.LENGTH_LONG).show();
                            }
                            else {
                                if(!getIntent().getExtras().getString("EsBicondicional").equals("true")) {
                                    mSistemaReference.child(eTextNombreTeorema.getText().toString()).setValue(new Demostration(demostracion, esTerminado));
                                    Log.e("Añadí el teorema", "añadido");
                                    Toast.makeText(ConfirmarNombreTeorema.this, "Se ha ingresado el nuevo teorema con éxito", Toast.LENGTH_LONG).show();
                                    finish();
                                }
                                else{
                                    mSistemaReference.child(eTextNombreTeorema.getText().toString()).setValue(new Demostration(demostracion,demostracion2,hipotesis,hipotesis2, esTerminado));
                                    Log.e("Añadí el teorema", "añadido");
                                    Toast.makeText(ConfirmarNombreTeorema.this, "Se ha ingresado el nuevo teorema con éxito", Toast.LENGTH_LONG).show();
                                    finish();
                                }

                            }

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });

    }
}
