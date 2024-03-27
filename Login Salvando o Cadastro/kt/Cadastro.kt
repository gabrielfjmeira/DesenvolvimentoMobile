package com.example.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException

class Cadastro : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        var username        = findViewById<EditText>(R.id.inputUsernameCadastro)
        var senha           = findViewById<EditText>(R.id.inputSenhaCadastro)
        var confirmarSenha  = findViewById<EditText>(R.id.inputConfirmarSenha)
        var spinner         = findViewById<Spinner>(R.id.spinnerTipoUsuario)
        var buttonCadastrar = findViewById<Button>(R.id.buttonCadastrar)
        var tipoUser = ""

        buttonCadastrar.isEnabled = false;

        val usuarios = listOf("user", "adm", "mkt")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, usuarios).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                tipoUser = usuarios[position]
                Toast.makeText(this@Cadastro, "Tipo de usuário selecionado: ${usuarios[position]}", Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        username.addTextChangedListener{
            habilitaBotaoCadastrar()
        }

        senha.addTextChangedListener{
            habilitaBotaoCadastrar()
        }

        confirmarSenha.addTextChangedListener{
            habilitaBotaoCadastrar()
        }

        buttonCadastrar.setOnClickListener{
            val usernameTxt = username.text.toString()
            val senhaTxt = senha.text.toString()

            if(cadastroExiste(this, "cadastros.txt", usernameTxt)){
                exibirErro()
            }else {
                val textUsername = "$usernameTxt\n"
                val textSenha = "$senhaTxt\n"
                val textTipoUser = "$tipoUser\n"
                saveContentToFile(this, textUsername, "cadastros.txt")
                saveContentToFile(this, textSenha, "cadastros.txt")
                saveContentToFile(this, textTipoUser, "cadastros.txt")
                Toast
                    .makeText(this, "Saved", Toast.LENGTH_LONG)
                    .show()
                showSaveDialog()
                var delayMillis = 5000
                Handler(Looper.getMainLooper()).postDelayed({
                    var intent = Intent()
                    setResult(RESULT_OK, intent)
                    finish()
                }, delayMillis.toLong())
            }
        }
    }

    private fun habilitaBotaoCadastrar (){
        var username        = findViewById<EditText>(R.id.inputUsernameCadastro)
        var senha           = findViewById<EditText>(R.id.inputSenhaCadastro)
        var confirmarSenha  = findViewById<EditText>(R.id.inputConfirmarSenha)
        var buttonCadastrar = findViewById<Button>(R.id.buttonCadastrar)

        if (((username.text.isNotEmpty()) && (senha.text.isNotEmpty()) && (confirmarSenha.text.isNotEmpty())) && (senha.text.toString() == confirmarSenha.text.toString())){
            buttonCadastrar.isEnabled = true;
        }
    }

    private fun saveContentToFile(context: Context, content: String, filename: String){
        context.openFileOutput(filename, Context.MODE_APPEND).use {
            it.write(content.toByteArray())
            it.flush()
        }
    }

    fun cadastroExiste(context: Context, filename: String, conteudo: String) : Boolean {
        try {
            context.openFileInput(filename).use { inputStream ->
                inputStream.bufferedReader().use { reader ->
                    var linha: String? = reader.readLine()

                    while (linha != null) {
                        if (linha == conteudo){
                            return true
                        }
                        linha = reader.readLine()
                    }

                    return false
                }
            }
        } catch (e: IOException){
            return false
        }
    }

    private fun exibirErro(){
        val erro = AlertDialog.Builder(this);

        erro.setTitle("Cadastro Já Existe!");
        erro.setMessage("O nome de usuário já está cadastrado no sistema!");

        erro.setPositiveButton("OK") {mensagem, which ->
            mensagem.dismiss()
        }

        erro.show();
    }

    private fun showSaveDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Dados Salvos!")
        builder.setMessage("Os dados de cadastro foram salvos!")
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }
}