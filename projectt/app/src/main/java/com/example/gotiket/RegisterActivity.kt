package com.example.gotiket

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.gotiket.databinding.ActivityLoginBinding
import com.example.gotiket.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding :  ActivityRegisterBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var ref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnkelogin.setOnClickListener {
            val intent = Intent(this,ActivityLoginBinding::class.java)
            startActivity(intent)
        }
        auth = FirebaseAuth.getInstance()

        binding.btnregister.setOnClickListener{
            val email= binding.reseditEmail.text.toString()
            val password=binding.reseditPassword.text.toString()
            val nama=binding.editusername.text.toString()
            val notelp=binding.editnohp.text.toString()

            if (email.isEmpty()){
                binding.reseditEmail.error = "Email Harus Diisi"
                binding.reseditEmail.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.reseditEmail.error = "Email Tidak Valid"
                binding.reseditEmail.requestFocus()
                return@setOnClickListener
            }
            if (password.isEmpty()){
                binding.reseditPassword.error = "Password Tidak boleh kosong"
                binding.reseditPassword.requestFocus()
                return@setOnClickListener
            }
            registerfirebase(email,password,notelp,nama)
        }
    }
    private fun  registerfirebase(email:String,password:String,notelp:String,nama:String){

        val userdata = user(
            nama.toString()+1,
            email.toString(),
            nama.toString(),
            notelp.toString(),
            password.toString()
        )

        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){
                if (it.isSuccessful){


                    ref = FirebaseDatabase.getInstance().getReference("user")
                    ref.child(nama.toString()+1).setValue(userdata)
                        .addOnCompleteListener {
                            Toast.makeText(this, "Berhasil Register", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this,ActivityLoginBinding::class.java)
                                startActivity(intent)
                        }
                }else{
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}