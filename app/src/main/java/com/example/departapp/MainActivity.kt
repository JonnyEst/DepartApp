package com.example.departapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    private var ingresarBtn:Button?=null;
    private var mAuth:FirebaseAuth?=null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAuth = FirebaseAuth.getInstance();
        ingresarBtn = findViewById(R.id.buttonI);
        var userText:EditText = findViewById(R.id.TextUser);
        var passText:EditText = findViewById(R.id.TextPassword);
        val inFire=FirebaseFirestore.getInstance();
        ingresarBtn?.setOnClickListener{
            if(userText.text.isNotEmpty() && passText.text.isNotEmpty()){
                mAuth!!.signInWithEmailAndPassword(userText.text.toString(),passText.text.toString()).addOnCompleteListener(
                    this
                ){task->
                    if(task.isSuccessful){
                        val user = mAuth!!.currentUser;
                        inFire.collection("Usuario").whereEqualTo("correo_usu",userText.text.toString()).get().addOnSuccessListener {
                            it.documents.forEach {
                                if (it?.data?.get("per_usu")=="xisi5bhpW1doYKn8qobb"){
                                    startActivity(Intent(this,AdminHome::class.java).putExtra("id",it?.data?.get("id_usu").toString())
                                        .putExtra("clave",passText.text.toString()));
                                    Toast.makeText(this,"Ingreso exitoso", Toast.LENGTH_SHORT).show();
                                    //userText.setText("");
                                    //passText.setText("");
                                }else if(it?.data?.get("per_usu")=="nteskvfRHoZYdlKO3PJm"){
                                    startActivity(Intent(this,ReciHome::class.java).putExtra("id",it?.data?.get("id_usu").toString())
                                        .putExtra("apar",it?.data?.get("apr_usu").toString()));
                                    Toast.makeText(this,"Ingreso exitoso", Toast.LENGTH_SHORT).show();
                                    //userText.setText("");
                                    //passText.setText("");
                                }else{
                                    Toast.makeText(this,"No tienes perfil asociado", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }else{
                        Toast.makeText(this,"Ingreso fallido", Toast.LENGTH_SHORT).show();
                    }

                }
            }else{
                Toast.makeText(this,"Ingresa Correo/Contraseña", Toast.LENGTH_SHORT).show();
            }
        }
    }
}