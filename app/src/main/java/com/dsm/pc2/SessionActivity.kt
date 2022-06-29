package com.dsm.pc2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dsm.pc2.models.Persona
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase


class SessionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_session)
        var edtIniDni: EditText = findViewById(R.id.edtIniDni)
        val edtIniPwd: EditText = findViewById(R.id.edtIniPwd)
        val btnIngresar: Button = findViewById(R.id.btnIngresar)
        btnIngresar.setOnClickListener {
            val persona = Persona(
                edtIniDni.text.toString(),
                "",
                edtIniPwd.text.toString()
            )
            IniciarSesion(persona)
        }
        val btnCrearCuenta: Button = findViewById(R.id.btnCrearCuenta)
        btnCrearCuenta.setOnClickListener {
            val registroActivity = Intent(this, MainActivity::class.java)
            startActivity(registroActivity)
        }
    }

    private fun IniciarSesion(persona: Persona) {
        val db = Firebase.firestore
        db.collection("personas")
            .whereEqualTo("documento", persona.documento)
            .whereEqualTo("clave", persona.clave)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    Toast.makeText(
                        this,
                        "El Usuario Y/O Clave NO Existe En El Sistema.",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    for (document in documents) {
                        val p = document.toObject<Persona>()
                        Toast.makeText(this, "¡Acceso Permitido! : " + p.nombre, Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error En IniciarSesion", Toast.LENGTH_LONG).show()
            }
    }
}