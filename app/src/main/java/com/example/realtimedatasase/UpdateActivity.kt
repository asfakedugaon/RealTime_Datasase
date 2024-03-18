package com.example.realtimedatasase

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UpdateActivity : AppCompatActivity() {

    lateinit var userNameUpdate: EditText
    lateinit var userEmailUpdate: EditText
    lateinit var updateDataBtn: AppCompatButton

    private var studentId: String? = null
    private lateinit var database: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        userNameUpdate = findViewById(R.id.userNameUpdate_textView)
        userEmailUpdate = findViewById(R.id.userEmailUpdate_textView)
        updateDataBtn = findViewById(R.id.updateDataBtn_button)

        database = FirebaseDatabase.getInstance().getReference("userData")

        studentId = intent.getStringExtra("key_id")
        val name = intent.getStringExtra("key_name")
        val email = intent.getStringExtra("key_email")

        userNameUpdate.setText(name)
        userEmailUpdate.setText(email)

        updateDataBtn.setOnClickListener {
            updateData()
        }
    }

    fun updateData(){
            val updateName = userNameUpdate.text.toString()
            val updateEmail = userEmailUpdate.text.toString()
        if (studentId != null){
            val updateStudent = StudentDataModual(studentId!!, updateName, updateEmail)

            database.child(studentId!!).setValue(updateStudent)
                .addOnSuccessListener {
                    Toast.makeText(this, "Data update successfully", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to update data", Toast.LENGTH_SHORT).show()
                }
        }
    }
}