package com.quispe.angelo.laboratoriocalificado03

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.quispe.angelo.laboratoriocalificado03.databinding.ItemTeacherBinding

class TeacherAdapter(
    private val teachers: List<Teacher>,
    private val onClick: (Teacher) -> Unit,
    private val onLongClick: (Teacher) -> Unit
) : RecyclerView.Adapter<TeacherAdapter.TeacherViewHolder>() {

    inner class TeacherViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemTeacherBinding.bind(view)
        fun bind(teacher: Teacher) {
            binding.tvName.text = teacher.name
            binding.tvLastName.text = teacher.last_name
            binding.tvPhone.text = teacher.phone_number
            binding.tvEmail.text = teacher.email

            Glide.with(itemView).load(teacher.image_url).into(binding.imgTeacher)

            itemView.setOnClickListener { onClick(teacher) }
            itemView.setOnLongClickListener {
                onLongClick(teacher)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeacherViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_teacher, parent, false)
        return TeacherViewHolder(view)
    }

    override fun onBindViewHolder(holder: TeacherViewHolder, position: Int) {
        holder.bind(teachers[position])
    }

    override fun getItemCount(): Int = teachers.size
}
