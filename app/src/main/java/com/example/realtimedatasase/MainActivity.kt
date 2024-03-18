package com.example.realtimedatasase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.UUID

class MainActivity : AppCompatActivity(), StudentDataAdapter.SetOnStudentClickListener {

        lateinit var addBtn: FloatingActionButton
        lateinit var recyclerView: RecyclerView
        private lateinit var studentList: ArrayList<StudentDataModual>
    private lateinit var databaseRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addBtn = findViewById(R.id.addBtn_floatingActionButton)
        recyclerView = findViewById(R.id.recy_recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        studentList = ArrayList<StudentDataModual>()
        getStudentData()

        addBtn.setOnClickListener {
            startActivity(Intent(this,AddDetailsActivity::class.java))
            finish()
        }
    }

        private fun getStudentData(){
            databaseRef = FirebaseDatabase.getInstance().getReference("userData")

            databaseRef.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    studentList.clear()

                    if (snapshot.exists()){
                        for (studentSnap in snapshot.children){
                            val studentData = studentSnap.getValue(StudentDataModual::class.java)
                            studentList.add(studentData!!)
                        }

                        val studentAdapter = StudentDataAdapter(studentList,this@MainActivity,  this@MainActivity)
                        recyclerView.adapter = studentAdapter
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@MainActivity, error.message, Toast.LENGTH_SHORT).show()
                }
            })
        }

    override fun onUpdateClick(position: Int) {
        val intent = Intent(this,UpdateActivity::class.java)
        intent.putExtra("key_id",studentList[position].id)
        intent.putExtra("key_name",studentList[position].name)
        intent.putExtra("key_email",studentList[position].email)
        startActivity(intent)
    }

    override fun onDeleteClick(position: Int) {
        val studentId = studentList[position].id
        databaseRef.child(studentId!!).removeValue()
            .addOnSuccessListener {
                Toast.makeText(this, "Student data deleted", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Deleted failed", Toast.LENGTH_SHORT).show()
            }
    }
}