package com.quispe.angelo.laboratoriocalificado03

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.quispe.angelo.laboratoriocalificado03.databinding.ActivityEjercicio01Binding

class Ejercicio01 : AppCompatActivity() {

    private lateinit var binding: ActivityEjercicio01Binding
    private lateinit var adapter: TeacherAdapter
    private val teachers = mutableListOf<Teacher>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEjercicio01Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar RecyclerView
        binding.rvTeachers.layoutManager = LinearLayoutManager(this)
        adapter = TeacherAdapter(teachers, { teacher ->
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:${teacher.phone_number}") // Cambiado a phone_number
            }
            startActivity(intent)
        }, { teacher ->
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:${teacher.email}")
            }
            startActivity(intent)
        })
        binding.rvTeachers.adapter = adapter

        // Llamada al endpoint
        fetchTeachers()
    }

    private fun fetchTeachers() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val call = getRetrofit().create(TeacherApi::class.java).getTeachers()
                if (call.isSuccessful) {
                    call.body()?.let { response ->
                        val teacherList = response.teachers
                        teachers.addAll(teacherList)
                        runOnUiThread { adapter.notifyDataSetChanged() }
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(this@Ejercicio01, "Error al cargar datos", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this@Ejercicio01, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://private-effe28-tecsup1.apiary-mock.com/") // Base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
