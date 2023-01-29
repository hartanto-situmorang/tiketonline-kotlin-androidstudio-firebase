package com.example.gotiket

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Profile(id:String,username:String) : Fragment() {
    private lateinit var ref: DatabaseReference
    var id2: String? = id
    var username: String? = username
    var emailuser: String? = null
    var nohpuser: String? = null
    var namauser: String? = null

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        val nama: TextView = view.findViewById(R.id.edtnamaprofil)
        val nohp: TextView= view.findViewById(R.id.edtnohpprofil)
        val email : TextView = view.findViewById(R.id.edtemailprofil)

        val btnlogout : Button = view.findViewById(R.id.btnkeluar)

        ref = FirebaseDatabase.getInstance().getReference("user")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (a in snapshot.children) {
                        if (a.child("id").value.toString() == id2.toString()) {
                            namauser = a.child("nama").value.toString()
                            nohpuser = a.child("notelp").value.toString()
                            emailuser = a.child("email").value.toString()
                            adddata(a.child("nama").value.toString(),a.child("notelp").value.toString(),a.child("email").value.toString())
                            println("Tes Di dalam ========="+namauser.toString()+emailuser.toString()+namauser.toString())
                            nama.text=namauser.toString()
                            email.text=emailuser.toString()
                            nohp.text=nohpuser.toString()

                        } else {
                        }
                    }
                }
                println("Tes Di luar ========="+namauser.toString()+emailuser.toString()+namauser.toString())
            }
            override fun onCancelled(error: DatabaseError) {
                println("Eror Database")
            }
        })

        btnlogout.setOnClickListener{
            val intent = Intent(this@Profile.requireContext(),LoginActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    public fun adddata(a:String,b:String,c:String){
        namauser=a
        nohpuser=b
        emailuser=c
    }
}