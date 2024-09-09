package com.example.practica1

import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class RegistroActivity : AppCompatActivity() {

    private lateinit var checkBoxPlayeras: CheckBox
    private lateinit var checkBoxBlusas: CheckBox
    private lateinit var checkBoxJeans: CheckBox
    private lateinit var checkBoxShorts: CheckBox
    private lateinit var checkBoxPantalones: CheckBox
    private lateinit var checkBoxFaldas: CheckBox
    private lateinit var radioGroup: RadioGroup
    private lateinit var btnRegistrar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        val toolbar: Toolbar = findViewById(R.id.toolbar2)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Registro"

        checkBoxPlayeras = findViewById(R.id.checkBox)
        checkBoxBlusas = findViewById(R.id.checkBox2)
        checkBoxJeans = findViewById(R.id.checkBox3)
        checkBoxShorts = findViewById(R.id.checkBox4)
        checkBoxPantalones = findViewById(R.id.checkBox5)
        checkBoxFaldas = findViewById(R.id.checkBox6)
        radioGroup = findViewById(R.id.radioGroup)
        btnRegistrar = findViewById(R.id.button3)

        btnRegistrar.setOnClickListener {
            mostrarDialogoRegistro()
        }
    }


    private fun mostrarDialogoRegistro() {
        val seleccionado = StringBuilder()

        if (checkBoxPlayeras.isChecked) seleccionado.append("Playeras\n")
        if (checkBoxBlusas.isChecked) seleccionado.append("Blusas\n")
        if (checkBoxJeans.isChecked) seleccionado.append("Jeans\n")
        if (checkBoxShorts.isChecked) seleccionado.append("Shorts\n")
        if (checkBoxPantalones.isChecked) seleccionado.append("Pantalones\n")
        if (checkBoxFaldas.isChecked) seleccionado.append("Faldas\n")

        val idSeleccionado = radioGroup.checkedRadioButtonId
        val radioButton = findViewById<RadioButton>(idSeleccionado)
        val talla = radioButton?.text.toString()

        val algunaSeleccionada = seleccionado.isNotEmpty()
        val tallaSeleccionada = idSeleccionado != -1

        if (algunaSeleccionada && tallaSeleccionada) {
            AlertDialog.Builder(this)
                .setTitle("Resumen del Registro")
                .setMessage("Ropa de interés:\n$seleccionado\nTalla seleccionada: $talla")
                .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                .show()
        } else if (!algunaSeleccionada && !tallaSeleccionada) {
            Toast.makeText(this, "Por favor, seleccione al menos una opción de ropa y una talla.", Toast.LENGTH_SHORT).show()
        } else if (!algunaSeleccionada) {
            Toast.makeText(this, "Por favor, seleccione al menos una opción de ropa.", Toast.LENGTH_SHORT).show()
        } else if (!tallaSeleccionada) {
            Toast.makeText(this, "Por favor, seleccione una talla.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}