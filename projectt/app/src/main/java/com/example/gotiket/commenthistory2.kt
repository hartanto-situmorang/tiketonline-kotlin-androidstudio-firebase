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

class commenthistory2(id:String,username:String,var data: Array<String>) : Fragment() {

    private lateinit var ref: DatabaseReference
    private lateinit var reference:DatabaseReference
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
        val view = inflater.inflate(R.layout.fragment_commenthistory2, container, false)
        val nama: TextView = view.findViewById(R.id.edtnamahyst)
        val jml: TextView = view.findViewById(R.id.edtjumlahhyst)
        val harga: TextView = view.findViewById(R.id.statushyst)
        val tgl: TextView = view.findViewById(R.id.edttanggalhyst)
        val komentar: EditText = view.findViewById(R.id.edtkomentar)

        nama.text = datakereta[2]
        jml.text = datakereta[3]
        harga.text = datakereta[4]
        tgl.text = datakereta[5]

        val btnsubmit: Button = view.findViewById(R.id.btnsubmit)
        val btnhapusdata: Button = view.findViewById(R.id.btnhapusdata)


        btnhapusdata.setOnClickListener{
            try {
                reference = FirebaseDatabase.getInstance().getReference("beli")
                reference.child(datakereta[1]+datakereta[0]).removeValue().addOnCompleteListener{
                    println(datakereta[1]+datakereta[0])
                    println("Berhasil Dihapus")
                }
                val fragment2 = History(id2.toString(), username2.toString())
                val activity: MainActivity = it.context as MainActivity
                val fragmentTransaction: FragmentTransaction =
                    activity.supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.frame_layout, fragment2)
                fragmentTransaction.commit()
            }catch (e:Exception){
                println(e.toString())
            }


        }


        btnsubmit.setOnClickListener {

            val databell = Beli(
                datakereta[0],
                datakereta[1],
                datakereta[2],
                datakereta[3],
                komentar.text.toString(),
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
                val fragment2 = History(id2.toString(), username2.toString())
                val activity: MainActivity = it.context as MainActivity
                val fragmentTransaction: FragmentTransaction =
                    activity.supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.frame_layout, fragment2)
                fragmentTransaction.commit()
                Toast.makeText(context, "Pembelian Berhasil", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                println(e.toString() + "\n\n\n\nEror Komentarrrrr\n------------------------------------------")
            }
        }

        return view
    }
}
