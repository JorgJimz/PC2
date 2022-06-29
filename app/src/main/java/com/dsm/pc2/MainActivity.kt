package com.dsm.pc2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.dsm.pc2.models.Persona
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val edtDocumento: EditText = findViewById(R.id.edtDni)
        val edtNombre: EditText = findViewById(R.id.edtNombre)
        val edtClave: EditText = findViewById(R.id.edtPwd)
        val edtRepetirClave: EditText = findViewById(R.id.edtRepeatPwd)
        val btnRegistrarCuenta: Button = findViewById(R.id.btnRegistrarCuenta)

        btnRegistrarCuenta.setOnClickListener {
            if (edtClave.text.toString().equals(edtRepetirClave.text.toString())) {
                val persona = Persona(
                    edtDocumento.text.toString(),
                    edtNombre.text.toString(),
                    edtClave.text.toString()
                )
                RegistrarPersona(persona)
            } else {
                Toast.makeText(this, "¡Las Claves Deben Coincidir!", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun RegistrarPersona(persona: Persona) {
        val db = Firebase.firestore
        db.collection("personas").add(persona)
            .addOnSuccessListener {
                val loginActivity = Intent(this, SessionActivity::class.java)
                startActivity(loginActivity)
            }.addOnFailureListener {
                Toast.makeText(this, "Error En RegistrarPersona", Toast.LENGTH_LONG).show()
            }
    }
}