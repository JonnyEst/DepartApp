package com.example.departapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ReciHome : AppCompatActivity() {

    private var cerBtn: Button?=null;
    private var pagBtn: Button?=null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reci_home);
        var bienTxt:TextView = findViewById(R.id.txtRec);
        var aparTxt:TextView = findViewById(R.id.textApa);
        var montoTxt:TextView = findViewById(R.id.textMon);
        var fechaTxt:TextView = findViewById(R.id.textFec);
        var fechaTxt2:TextView = findViewById(R.id.textFec2);
        var montoTxt2:TextView = findViewById(R.id.textMon2);
        cerBtn=findViewById(R.id.cerrBtn);
        pagBtn=findViewById(R.id.pagarBtn);
        val bundle:Bundle? = intent.extras;
        val id:String = bundle?.getString("id").toString();
        val apart:String = bundle?.getString("apar").toString();
        val inFire= FirebaseFirestore.getInstance();
        aparTxt.setText(apart);
        inFire.collection("Usuario").whereEqualTo("id_usu",id).get().addOnSuccessListener {
            it.documents.forEach{
                bienTxt.text = "Bienvenid@ " + it?.data?.get("nom_usu").toString() + " " + it?.data?.get("ape_usu");
            }
        }
        inFire.collection("Pagos").whereEqualTo("apa_pag",apart).whereEqualTo("estado","P").get().addOnSuccessListener {
            it.documents.forEach{
                montoTxt.visibility = View.VISIBLE;
                montoTxt2.text = "Monto"
                montoTxt.text = "$ " + it?.data?.get("monto_pag").toString();
                fechaTxt2.visibility = View.VISIBLE;
                fechaTxt.visibility = View.VISIBLE;
                fechaTxt.text = it?.data?.get("fecha_vencimiento").toString();
            }
        }.addOnFailureListener{
            montoTxt.visibility = View.INVISIBLE;
            fechaTxt2.visibility = View.INVISIBLE;
            fechaTxt.visibility = View.INVISIBLE;
        }

        pagBtn?.setOnClickListener {
            inFire.collection("Pagos").whereEqualTo("apa_pag",apart).whereEqualTo("estado","P").get().addOnSuccessListener {
                it.documents.forEach{
                    inFire.collection("Pagos").document(it.id).update(mapOf("estado" to "F","fecha_pago" to "Hoy"))
                    Toast.makeText(this,"Se pago factura pendiente", Toast.LENGTH_SHORT).show();
                    actualizar();
                }
            }.addOnFailureListener{
                Toast.makeText(this,it.message, Toast.LENGTH_SHORT).show();
            }
        }

        cerBtn?.setOnClickListener {
            FirebaseAuth.getInstance().signOut();
            onBackPressed();
        }
    }

    private fun actualizar(){
        var montoTxt:TextView = findViewById(R.id.textMon);
        var fechaTxt:TextView = findViewById(R.id.textFec);
        var fechaTxt2:TextView = findViewById(R.id.textFec2);
        var montoTxt2:TextView = findViewById(R.id.textMon2);
        montoTxt.visibility = View.INVISIBLE;
        montoTxt2.text = "No tienes pagos pendientes";
        fechaTxt2.visibility = View.INVISIBLE;
        fechaTxt.visibility = View.INVISIBLE;
    }

}