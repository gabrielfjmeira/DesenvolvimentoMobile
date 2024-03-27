package com.example.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class EditData : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_data)

        var btnVoltar = findViewById<Button>(R.id.btnVoltar)

        var usernameInputEdt = findViewById<TextView>(R.id.inputUsernameEdt)
        usernameInputEdt.text = intent.getStringExtra("username")

        var passwordInputEdt = findViewById<TextView>(R.id.inputPasswordEdt)
        passwordInputEdt.text = intent.getStringExtra("password")

        usernameInputEdt.addTextChangedListener(textWatcher)
        passwordInputEdt.addTextChangedListener(textWatcher)

        btnVoltar.setOnClickListener {
            var usernameInput = usernameInputEdt.text.toString().trim()
            var passwordInput = passwordInputEdt.text.toString().trim()

            if (usernameInput.isNotEmpty() && passwordInput.isNotEmpty()){
                showSaveDialog()
                var delayMillis = 5000
                Handler(Looper.getMainLooper()).postDelayed({
                    var intent = Intent()
                    intent.putExtra("usernameEdt", usernameInputEdt.text.toString())
                    intent.putExtra("passwordEdt", passwordInputEdt.text.toString())
                    setResult(RESULT_OK, intent)
                    finish()
                }, delayMillis.toLong())
            }else{
                showErrorDialog()
            }
        }
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            var usernameInput = findViewById<TextView>(R.id.inputUsernameEdt).text.toString().trim()
            var passwordInput = findViewById<TextView>(R.id.inputPasswordEdt).text.toString().trim()
            var btnVoltar = findViewById<Button>(R.id.btnVoltar)

            btnVoltar.isEnabled = usernameInput.isNotEmpty() && passwordInput.isNotEmpty()
        }

    }

    private fun showSaveDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Dados Salvos!")
        builder.setMessage("Os dados de login foram alterados!")
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun showErrorDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Erro de editor")
        builder.setMessage("Usuário ou senha nulos.")
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }
}