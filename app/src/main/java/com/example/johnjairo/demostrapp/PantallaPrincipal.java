package com.example.johnjairo.demostrapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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

public class PantallaPrincipal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private TextView nombreSistema;
    private Spinner spinnerTeoremasTerminados;
    private Spinner spinnerTeoremasPorTerminar;
    private Button btnRevisarTeorema;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user=mAuth.getCurrentUser();
    String keyTeorema;
    DatabaseReference mSistemasReference=mDatabase.child("usuarios").child(user.getUid()).child("Sistemas");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        nombreSistema=(TextView)findViewById(R.id.text_sistema_actual);
        nombreSistema.setText("Sistema Formal Proposicional");
        spinnerTeoremasTerminados=(Spinner)findViewById(R.id.spn_revisar_teorema);
        spinnerTeoremasPorTerminar=(Spinner)findViewById(R.id.spn_continuar_teorema);
        btnRevisarTeorema=(Button)findViewById(R.id.btn_revisar_teorema);

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Button comenzarButton = (Button) findViewById(R.id.btn_comenzar);
        comenzarButton.setOnClickListener(new View.OnClickListener() {
            /*
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
            */
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PantallaPrincipal.this, NuevoTeorema.class);
                startActivity(intent);

            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        final ArrayList<String> misReglasDeValidezTerminadas=new ArrayList<>();
        final ArrayList<String> misReglasDeValidezPorTerminar=new ArrayList<>();

        misReglasDeValidezTerminadas.add("Seleccione");
        mSistemasReference.child("Sistema Formal Proposicional").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.e("hijos: ",snapshot.getKey()+"...."+String.valueOf(snapshot.getChildrenCount()));
                for (DataSnapshot snap : snapshot.getChildren()) {
                    boolean  isTerminado=snap.child("terminado").getValue(Boolean.class);
                    Log.e("boolean",String.valueOf(isTerminado));
                    if(isTerminado) {
                        misReglasDeValidezTerminadas.add(snap.getKey());
                    }
                    else{
                        misReglasDeValidezPorTerminar.add(snap.getKey());
                    }

                }
                for (String s:misReglasDeValidezTerminadas){
                    Log.e("Llave en loop",s);
                }
                Log.e("Tamano","Tamano validez terminadas..."+misReglasDeValidezTerminadas.size());
                if(misReglasDeValidezTerminadas.size()>=1) {
                    ArrayAdapter<String> terminadas_Adapter = new ArrayAdapter<String>(PantallaPrincipal.this, android.R.layout.simple_spinner_dropdown_item, misReglasDeValidezTerminadas);
                    terminadas_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerTeoremasTerminados.setAdapter(terminadas_Adapter);
                    spinnerTeoremasTerminados.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            ((TextView) view).setTextColor(Color.BLACK);
                            keyTeorema=spinnerTeoremasTerminados.getSelectedItem().toString();

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    btnRevisarTeorema.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (spinnerTeoremasTerminados.getSelectedItem().equals("Seleccione")){
                                Toast.makeText(PantallaPrincipal.this, "Seleccione un teorema", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Intent intent= new Intent(PantallaPrincipal.this,MostrarTeoremaTerminado.class);
                                intent.putExtra("KeyTeorema",keyTeorema);
                                startActivity(intent);
                            }
                        }
                    });
                    /*

                    */

                }
                if(misReglasDeValidezPorTerminar.size()>=1) {
                    ArrayAdapter<String> porTerminar_Adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, misReglasDeValidezPorTerminar);
                    porTerminar_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerTeoremasPorTerminar.setAdapter(porTerminar_Adapter);
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pantalla_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_nuevo_sistema) {
            Intent intent=new Intent(PantallaPrincipal.this,NuevoTeorema.class);
            startActivity(intent);
            // Handle the camera action
        } else if (id == R.id.nav_sistemas) {

        } else if (id == R.id.nav_ajustes) {

        } else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(PantallaPrincipal.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 250);


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.nav_view);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void nuevademosbttn(){
        Intent intent = new Intent(getApplicationContext(), NuevoTeorema.class);
        startActivity(intent);
    }



}
