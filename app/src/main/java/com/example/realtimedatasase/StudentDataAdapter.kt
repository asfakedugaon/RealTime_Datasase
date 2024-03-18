package com.example.realtimedatasase

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentDataAdapter(private val userList: ArrayList<StudentDataModual>,val context: Context,
       private val onStudentClickListener: SetOnStudentClickListener): RecyclerView.Adapter<StudentDataAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.userNameText_textView)
        val email = itemView.findViewById<TextView>(R.id.userEmailText_textView)
        val update = itemView.findViewById<ImageView>(R.id.updateBtn_imageView)
        val delete = itemView.findViewById<ImageView>(R.id.deleteBtn_imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.add_student_details_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = userList.size

    override fun onBindViewHolder(holder: StudentDataAdapter.ViewHolder, position: Int) {
        val studentList = userList[position]
        holder.name.text = studentList.name
        holder.email.text = studentList.email

        holder.update.setOnClickListener {
            onStudentClickListener.onUpdateClick(position)
        }

        holder.delete.setOnClickListener {
            val dialog = AlertDialog.Builder(context)
            dialog.setTitle("Delete")
            dialog.setMessage("Aru you sure you want to delete item?")
            dialog.setPositiveButton("Yes") { dialog, which ->
                onStudentClickListener.onDeleteClick(position)
            }
            dialog.setNegativeButton("No") { dialog, which ->
                dialog.dismiss()
            }
            val alert = dialog.create()
            dialog.show()
        }

    }

    interface SetOnStudentClickListener {
        fun onUpdateClick(position: Int)
        fun onDeleteClick(position: Int)
    }
}
