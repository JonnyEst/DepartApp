package com.example.departapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AdminHome : AppCompatActivity() {
    private var buscBtn:Button?=null;
    private var aniaBtn:Button?=null;
    private var paBtn:Button?=null;
    private var cerBtn:Button?=null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_home)
        var bienTxt:TextView=findViewById(R.id.textAd);
        val bundle:Bundle? = intent.extras;
        val id:String = bundle?.getString("id").toString();
        val clave:String = bundle?.getString("clave").toString();
        val inFire= FirebaseFirestore.getInstance();
        aniaBtn=findViewById(R.id.aniaBtn);
        paBtn=findViewById(R.id.pagoBtn);
        cerBtn=findViewById(R.id.cerrBtn);
        inFire.collection("Usuario").whereEqualTo("id_usu",id).get().addOnSuccessListener {
            it.documents.forEach{
                bienTxt.text = "Bienvenid@ " + it?.data?.get("nom_usu").toString() + " " + it?.data?.get("ape_usu");
            }
        }

        aniaBtn?.setOnClickListener {
            inFire.collection("Usuario").whereEqualTo("id_usu",id).get().addOnSuccessListener {
                it.documents.forEach{
                    startActivity(Intent(this, aniadirUsu::class.java).putExtra("email",it?.data?.get("correo_usu").toString())
                        .putExtra("clave",clave))
                }
            }
        }

        paBtn?.setOnClickListener {

        }

        cerBtn?.setOnClickListener {
            FirebaseAuth.getInstance().signOut();
            onBackPressed();
        }
    }
}