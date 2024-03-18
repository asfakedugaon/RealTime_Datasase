package com.example.realtimedatasase

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.UUID

class AddDetailsActivity : AppCompatActivity() {

    lateinit var enterName: EditText
    lateinit var enterEmail: EditText
    lateinit var addData: AppCompatButton

    private lateinit var databaseRef: DatabaseReference

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_details)

        enterName = findViewById(R.id.enterName_editText)
        enterEmail = findViewById(R.id.enterEmail_editText)
        addData = findViewById(R.id.addDataBtn_button)
        
        databaseRef = FirebaseDatabase.getInstance().getReference("userData")

        addData.setOnClickListener {
            addStudent()
        }
    }

    private fun addStudent(){
       val username = enterName.text.toString()
        val useremail = enterEmail.text.toString()

        val studentId = databaseRef.push().key!!
        
        val studentModal = StudentDataModual(studentId,username,useremail)
        
        databaseRef.child(studentId).setValue(studentModal)
            .addOnSuccessListener {
                Toast.makeText(this, "Student data added", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Student data added failed ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }
}