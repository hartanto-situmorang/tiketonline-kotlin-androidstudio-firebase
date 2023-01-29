package com.example.gotiket

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
import java.text.SimpleDateFormat
import java.util.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class commenthistory(id:String,username:String,var data: Array<String>) : Fragment() {

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_commenthistory, container, false)
        val nama: TextView = view.findViewById(R.id.edtnamabeli)
        val jml: TextView = view.findViewById(R.id.edtjumlahbeli)
        val harga: TextView = view.findViewById(R.id.edtstatusbeli)
        val tgl: TextView = view.findViewById(R.id.edttanggalbeli)

        nama.text = datakereta[2]
        jml.text = datakereta[3]
        harga.text = datakereta[5]
        tgl.text = datakereta[6]

        val btncancel: Button = view.findViewById(R.id.btncancel)
        val btnselesai: Button = view.findViewById(R.id.btnselesai)

        btncancel.setOnClickListener {
            val sdf = SimpleDateFormat("dd MMMM yyyy")
            val currentDate = sdf.format(Date())

            val firstDate: Date = sdf.parse(currentDate)
            val secondDate: Date = sdf.parse(datakereta[6])

            when {
                firstDate.after(secondDate) -> {
                    Toast.makeText(context, "Maaf Tiket Tidak Dapat Dibatalkan !!!", Toast.LENGTH_SHORT).show()
                }
                firstDate.before(secondDate) -> {
                    val databell = Beli(
                        datakereta[0],
                        datakereta[1],
                        datakereta[2],
                        datakereta[3],
                        datakereta[4],
                        "Dibatalkan",
                        datakereta[6]
                    )
                    try {
                        ref = FirebaseDatabase.getInstance().getReference("beli")
                        ref.child(datakereta[1].toString() + datakereta[0].toString()).setValue(databell)
                            .addOnCompleteListener {
                                //                Toast.makeText(context, "Pembelian Berhasil", Toast.LENGTH_SHORT).show()
                                println(id2.toString() + " " + username2.toString())
                            }
                        val fragment2 = caripesawat(id2.toString(), username2.toString())
                        val activity: MainActivity = it.context as MainActivity
                        val fragmentTransaction: FragmentTransaction =
                            activity.supportFragmentManager.beginTransaction()
                        fragmentTransaction.replace(R.id.frame_layout, fragment2)
                        fragmentTransaction.commit()
                        Toast.makeText(context, "Pembelian Berhasil", Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        println(e.toString()+"\n\n\n\nEror\n------------------------------------------")
                    }
                }
                firstDate == secondDate -> {
                    Toast.makeText(context, "Maaf Tiket Tidak Dapat Dibatalkan !!!", Toast.LENGTH_SHORT).show()
                }
            }
        }
            btnselesai.setOnClickListener {
                val databell = Beli(
                    datakereta[0],
                    datakereta[1],
                    datakereta[2],
                    datakereta[3],
                    "belum ada",
                    "Berhasil",
                    datakereta[6]
                )
                try {

                    ref = FirebaseDatabase.getInstance().getReference("beli")
                    ref.child(datakereta[1].toString() + datakereta[0].toString())
                        .setValue(databell)
                        .addOnCompleteListener {
                            //                Toast.makeText(context, "Pembelian Berhasil", Toast.LENGTH_SHORT).show()
                            println(id2.toString() + " " + username2.toString())
                        }
                    val fragment2 = History(id2.toString(), username2.toString())
                    val activity: MainActivity = it.context as MainActivity
                    val fragmentTransaction: FragmentTransaction =
                        activity.supportFragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.frame_layout, fragment2)
                    fragmentTransaction.commit()
                    Toast.makeText(context, "Pembelian Berhasil", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    println(e.toString()+"\n\n\n\nErooooooooooooooooooooooonyaaaaaaaaaaaaaaa\n------------------------------------------")
                }
            }
        return view
    }
}