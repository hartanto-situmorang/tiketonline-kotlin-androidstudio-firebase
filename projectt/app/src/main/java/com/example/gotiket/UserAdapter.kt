package com.example.belajarfirebase

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.gotiket.R
import com.example.gotiket.user
import com.google.firebase.database.FirebaseDatabase

class UserAdapter (
    val userContext: Context,
    val layoutResId: Int,
    val userList: List<user>
) : ArrayAdapter<user>(userContext, layoutResId, userList){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(userContext)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val o_nama: TextView = view.findViewById(R.id.ou_age)
        val o_kota: TextView = view.findViewById(R.id.ou_fisrname)
        val o_usia: TextView = view.findViewById(R.id.ou_lastname)
        val imgEdit: ImageView = view.findViewById(R.id.icn_edit)

        val user = userList[position]

        imgEdit.setOnClickListener{
            updateDialog(user)
        }

        o_nama.text = "Nama : " + user.nama
        o_kota.text = "Kota Lahir : " + user.notelp
        o_usia.text = "Usia : " + user.email

        return view
    }

    private fun updateDialog(user: user){
        val builder = AlertDialog.Builder(userContext)
        builder.setTitle("Update Data")
        val inflater = LayoutInflater.from(userContext)
        val view = inflater.inflate(R.layout.update, null)

        val edtKota = view.findViewById<EditText>(R.id.Upage)
        val edtUsia = view.findViewById<EditText>(R.id.Upfirstname)
        val edtNoHp = view.findViewById<EditText>(R.id.Uplastname)

        edtKota.setText(user.password)
        edtUsia.setText(user.notelp)
        edtNoHp.setText(user.email)

        builder.setView(view)

        builder.setPositiveButton("Ubah"){p0, p1 ->
            val dbuser = FirebaseDatabase.getInstance().getReference("user")
            val nama = edtKota.text.toString().trim()
            val alamat = edtUsia.text.toString().trim()
            val usia = edtNoHp.text.toString()
            if (nama.isEmpty() or alamat.isEmpty() or usia.isEmpty() or alamat.isEmpty()){
                Toast.makeText(userContext, "Isi data secara lengkap tidak boleh kosong",
                    Toast.LENGTH_SHORT)
                    .show()
                return@setPositiveButton
            }
            val user = user("dfsdfs","dfs",nama, alamat, usia)

            dbuser.child(user.nama).setValue(user)
            Toast.makeText(userContext, "Data berhasil di update", Toast.LENGTH_SHORT)
                .show()
        }

        builder.setNeutralButton("Batal") {p0, p1 -> }

        builder.setNegativeButton("Hapus") {p0, p1 ->
            val dbuser = FirebaseDatabase.getInstance().getReference("user")
                .child(user.nama)
            val dbDetil = FirebaseDatabase.getInstance().getReference("detil user")
                .child(user.nama)

            dbuser.removeValue()
            dbDetil.removeValue()

            Toast.makeText(userContext, "Data berhasil di hapus", Toast.LENGTH_SHORT)
                .show()
        }

        val alert = builder.create()
        alert.show()
    }
}