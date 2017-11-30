package com.example.johnjairo.demostrapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private Button botonConfirmar;
    private EditText etPassword;
    private EditText etConfirmacionPassword;
    private EditText etUsuario;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        botonConfirmar=(Button)findViewById(R.id.botonRegistro);
        etPassword=(EditText)findViewById(R.id.etext_password);
        etConfirmacionPassword=(EditText)findViewById(R.id.etext_confirmacionPassword);
        etUsuario=(EditText)findViewById(R.id.etext_usuario);
        botonConfirmar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                crearCuenta();
            }
        });
    }
    public void crearCuenta(){
        String correo=etUsuario.getText().toString();
        String clave=etPassword.getText().toString();
        String confirmacionClave=etConfirmacionPassword.getText().toString();
        mAuth = FirebaseAuth.getInstance();
        if(correo.equals("") || clave.equals("")){
            Toast.makeText(RegisterActivity.this, "error al crear cuenta faltan campos", Toast.LENGTH_SHORT).show();
        }
        else{
            Log.e("intento creación " ,""+"Quiero crear usuario");
            if(clave.equals(confirmacionClave)&&clave.length()>6){

                   if(isEmailValid(correo)){
                       mAuth.createUserWithEmailAndPassword(correo, clave)
                               .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                   @Override
                                   public void onComplete(@NonNull Task<AuthResult> task) {
                                       //Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                                       Toast.makeText(RegisterActivity.this, "Registro realizado correctamente", Toast.LENGTH_SHORT).show();
                                       Log.e("cuentacreada " ,""+"Entre a la creación");
                                       Intent intent=new Intent(RegisterActivity.this, LoginActivity.class);
                                       intent.putExtra("CuentaCreada","Creada");
                                       Log.e("cuentacreada " ,""+"Terminé la creación");
                                       startActivity(intent);
                                       finish();


                                       // Si falla el registro, mostrar mensaje al usuario.
                                       // Si es correcto, el listener para el estado de la autenticación será notificado
                                       // y la lógica para manejar el usuario se puede realizar mediante el listener.
                                       if (!task.isSuccessful()) {
                                           Log.e("errorCreación " ,""+"No se pudo crear la cuenta");
                                           Toast.makeText(RegisterActivity.this, "hubo un error al crear la cuenta", Toast.LENGTH_SHORT).show();
                                       }

                                   }
                               });
                   }
                   else{
                       Toast.makeText(RegisterActivity.this, "El correo no contiene @", Toast.LENGTH_SHORT).show();
                   }
            }
            else{
                Toast.makeText(RegisterActivity.this, "Las contraseñas no coinciden o es muy corta la contraseña", Toast.LENGTH_SHORT).show();
            }

        }
    }
    private boolean isEmailValid(String email) {
        return email.contains("@");
    }
}
