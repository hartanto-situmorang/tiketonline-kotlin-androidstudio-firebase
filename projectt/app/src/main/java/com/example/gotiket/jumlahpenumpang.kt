package com.example.gotiket

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class jumlahpenumpang(id:String,username:String,var data: Array<String>) : Fragment() {
    private lateinit var ref: DatabaseReference
    var id2: String? = id
    var username2: String? = username

    private var datakereta = data
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_jumlahpenumpang, container, false)
        val textView: TextView = view.findViewById(R.id.txtharga)
        textView.text = datakereta[0].toString()
        val jumlah: EditText = view.findViewById(R.id.edtjumlah)

        val nxbtn: Button = view.findViewById(R.id.btnbelitiket)
        nxbtn.setOnClickListener {
            val anggotabeli = Beli(
                datakereta[1].toString(),
                id2.toString(),
                datakereta[4].toString(),
                jumlah.text.toString(),
                "Belum Ada",
                "Menunggu",
                datakereta[6].toString()
            )
            try {
                ref = FirebaseDatabase.getInstance().getReference("beli")
                ref.child(id2.toString()+datakereta[1].toString()).setValue(anggotabeli)
                    .addOnCompleteListener {
                        //                Toast.makeText(context, "Pembelian Berhasil", Toast.LENGTH_SHORT).show()
                        println(id2.toString() + " " + username2.toString())
                    }

                val fragment2 = Belanja(id2.toString(), username2.toString())
                val activity: MainActivity = it.context as MainActivity
                val fragmentTransaction: FragmentTransaction =
                    activity.supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.frame_layout, fragment2)
                fragmentTransaction.commit()
                Toast.makeText(context, "Pembelian Berhasil", Toast.LENGTH_SHORT).show()

            } catch (e: Exception) {
                println(e)
            }
        }
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
