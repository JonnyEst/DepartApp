package com.example.departapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class aniadirUsu : AppCompatActivity() {

    private var volverBnt:Button?=null;
    private var creBtn:Button?=null;
    private var mAuth: FirebaseAuth?=null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aniadir_usu);
        volverBnt=findViewById(R.id.buttonV);
        creBtn=findViewById(R.id.buttonC);
        val inFire= FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        var userTxt:TextView = findViewById(R.id.TextUser);
        var passTxt:TextView = findViewById(R.id.TextPassword);
        var nomTxt:TextView = findViewById(R.id.TextNomb);
        var apeTxt:TextView = findViewById(R.id.TextApell);
        var idenTxt:TextView = findViewById(R.id.TextIden);
        var apaTxt:TextView = findViewById(R.id.TextApa);
        creBtn?.setOnClickListener {
            if (userTxt.text.isNotEmpty() && passTxt.text.isNotEmpty()){
                mAuth!!.createUserWithEmailAndPassword(userTxt.text.toString(),passTxt.text.toString()).addOnCompleteListener(
                    this
                ){task->
                    if(task.isSuccessful){
                        inFire.collection("Usuario").add(mapOf("nom_usu" to nomTxt.text.toString(),"ape_usu" to apeTxt.text.toString()
                            , "numdoc_usu" to idenTxt.text.toString(), "correo_usu" to userTxt.text.toString(), "per_usu" to "nteskvfRHoZYdlKO3PJm"
                            , "apr_usu" to apaTxt.text.toString())).addOnSuccessListener {
                            inFire.collection("Usuario").document(it.id).update(mapOf("id_usu" to it.id));
                            Toast.makeText(this,"El usuario se creo exitosamente", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }.addOnFailureListener{
                            Toast.makeText(this,it.message, Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(this,"El usuario ya existe", Toast.LENGTH_SHORT).show();
                    }
                }
            }else{
                Toast.makeText(this,"Algun dato te falta llenar", Toast.LENGTH_SHORT).show();
            }
        }

        volverBnt?.setOnClickListener {
            onBackPressed();
        }
    }
}