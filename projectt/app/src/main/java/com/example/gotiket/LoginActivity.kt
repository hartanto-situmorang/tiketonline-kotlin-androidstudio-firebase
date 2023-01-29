package com.example.gotiket

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.gotiket.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var authlog: FirebaseAuth
    private lateinit var ref: DatabaseReference
    var id2: String? = null
    var username2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        authlog = FirebaseAuth.getInstance()

        binding.btnkeregister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }


        binding.btnlogin.setOnClickListener {
            val email = binding.editEmail.text.toString()
            val password = binding.editPassword.text.toString()

            if (email.isEmpty()) {
                binding.editEmail.error = "Email Harus Diisi"
                binding.editEmail.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.editEmail.error = "Email Tidak Valid"
                binding.editEmail.requestFocus()
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                binding.editPassword.error = "Password Tidak boleh kosong"
                binding.editPassword.requestFocus()
                return@setOnClickListener
            }
            loginfirebase(email, password)
        }
    }

    private fun loginfirebase(em: String, pass: String) {

        authlog.signInWithEmailAndPassword(em, pass)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    ref = FirebaseDatabase.getInstance().getReference("user")
                    ref.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (snapshot.exists()) {
                                for (a in snapshot.children) {
                                    if (a.child("email").value.toString().equals(em)) {
                                        id2 = a.child("id").value.toString()
                                        username2 = a.child("nama").value.toString()
                                    }
                                }
                            }
                        }
                        override fun onCancelled(error: DatabaseError) {
                        }
                    })
                    println(id2.toString() + "===================" + username2.toString())
                    if (id2.toString() != "null") {
                        Toast.makeText(this, "Berhasil Login", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java).also {
                            it.putExtra("ids", id2.toString())
                            it.putExtra("usernames", username2.toString())
                            startActivity(it)
                        }
                    } else {
                    }
                }else{
                    Toast.makeText(this, "Gagal Login", Toast.LENGTH_SHORT).show()
                }
            }
    }
}