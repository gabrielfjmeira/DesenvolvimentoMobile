package com.example.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    var usrLogin = "user"
    var pwdLogin = "1234"

    val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == RESULT_OK){
            var resultUser = it.data?.getStringExtra("usernameEdt")
            if (resultUser != null) {
                usrLogin = resultUser
            }

            var resultPwd = it.data?.getStringExtra("passwordEdt")
            if (resultPwd != null) {
                pwdLogin = resultPwd
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btnLogin = findViewById<Button>(R.id.btnLogin)
        btnLogin.isEnabled = false

        var btnCadastro = findViewById<Button>(R.id.btnCadastro)
        btnCadastro.setOnClickListener {
            var intentCadastro = Intent(this, Cadastro::class.java)
            resultLauncher.launch(intentCadastro)
        }

        var usernameInput = findViewById<TextView>(R.id.inputUser)
        var passwordInput = findViewById<TextView>(R.id.inputPassword)

        usernameInput.addTextChangedListener(textWatcher)
        passwordInput.addTextChangedListener(textWatcher)

        btnLogin.setOnClickListener {
            if (usernameInput.text.toString() == usrLogin && passwordInput.text.toString() == pwdLogin){
                var intentLogin = Intent(this, EditData::class.java)
                intent.putExtra("username", "${usernameInput.text.toString()}")
                intent.putExtra("password", "${passwordInput.text.toString()}")
                resultLauncher.launch(intentLogin)
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
            var usernameInput = findViewById<TextView>(R.id.inputUser).text.toString().trim()
            var passwordInput = findViewById<TextView>(R.id.inputPassword).text.toString().trim()
            var btnLogin = findViewById<Button>(R.id.btnLogin)

            btnLogin.isEnabled = usernameInput.isNotEmpty() && passwordInput.isNotEmpty()
        }

    }

    private fun showErrorDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Erro de login")
        builder.setMessage("Usuário ou senha inválidos.")
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }
}