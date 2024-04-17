package com.example.recycler

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.recycler.databinding.ActivityCityDetailsBinding

class CityDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil
            .setContentView<ActivityCityDetailsBinding>(this,
                R.layout.activity_city_details)

        Singleton.citySelected?.apply {
                binding.nameEditText.setText(name)
                binding.populationEditText.setText(population.toString())
                binding.capitalCheckBox.isChecked = isCapital
        }

        binding.saveButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val population = binding.populationEditText.text
                .toString().toInt()
            val isCapital = binding.capitalCheckBox.isChecked
            if (Singleton.citySelected == null) {
                Singleton.add(City(0, name, population, isCapital))
            }else{
                Singleton.citySelected?.apply{
                    this.name = name
                    this.population = population
                    this.isCapital = isCapital
                    Singleton.update(this)
                }
            }
            finish()
        }

        binding.backButton.setOnClickListener{
            val alerta = AlertDialog.Builder(this);

            alerta.setTitle("Voltar sem salvar?");
            alerta.setMessage("Voce deseja volter a página anterior sem salvar os dados?");

            alerta.setPositiveButton("Sim") { dialog,_  ->
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }

            alerta.setNegativeButton("Não") { dialog,_  ->
                dialog.dismiss()
            }

            alerta.show();
        }
    }
}



