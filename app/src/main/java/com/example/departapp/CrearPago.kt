package com.example.departapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CrearPago : AppCompatActivity() {

    private var volverBnt: Button?=null;
    private var creBtn: Button?=null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_pago)
        volverBnt=findViewById(R.id.buttonV);
        creBtn=findViewById(R.id.buttonC);
        val inFire= FirebaseFirestore.getInstance();
        var apaTxt: TextView = findViewById(R.id.TextApa);
        var monTxt: TextView = findViewById(R.id.TextMont);
        var fecvTxt: TextView = findViewById(R.id.TextFecV);


        creBtn?.setOnClickListener {
            if (apaTxt.text.isNotEmpty() && monTxt.text.isNotEmpty() && fecvTxt.text.isNotEmpty()){
                inFire.collection("Pagos").add(mapOf("apa_pag" to apaTxt.text.toString(),"fecha_vencimiento" to fecvTxt.text.toString()
                    , "estado" to "P", "monto_pag" to monTxt.text.toString())).addOnSuccessListener {
                    inFire.collection("Pagos").document(it.id).update(mapOf("id_pag" to it.id));
                    Toast.makeText(this,"El pago se creo correctamente", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }.addOnFailureListener{
                    Toast.makeText(this,it.message, Toast.LENGTH_SHORT).show();
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