package com.example.gotiket

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.FragmentTransaction
import java.text.SimpleDateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class carikereta(id:String,username:String) : Fragment() {
    private var id1:String?=id
    private var username:String?=username
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
        val view = inflater.inflate(R.layout.fragment_carikereta, container, false)
        val nxbtn : Button = view.findViewById(R.id.btncarikereta)
        val btntgl : Button = view.findViewById(R.id.btnpilihtglkrt)
        val jemputt: EditText = view.findViewById(R.id.darikrt)
        val tujuan : EditText = view.findViewById(R.id.tujuankrt)
        var formatdate = SimpleDateFormat("dd MMMM yyyy", Locale.US)

        btntgl.setOnClickListener(View.OnClickListener {
            val getDate: Calendar = Calendar.getInstance()
            val datepick = DatePickerDialog(view.context,android.R.style.Theme_Holo_Dialog_MinWidth,
                DatePickerDialog.OnDateSetListener { DatePicker, i, i2, i3->

                    val selectDate = Calendar.getInstance()
                    selectDate.set(Calendar.YEAR,i)
                    selectDate.set(Calendar.MONTH,i2)
                    selectDate.set(Calendar.DAY_OF_YEAR,i3)
                    val date = formatdate.format(selectDate.time)
                    nxbtn.setOnClickListener{
//                        println(date.toString()+" "+tujuan.text.toString()+" "+jemputt.text.toString())
                        val fragment2 = Kereta(id1.toString(),username.toString(),date.toString(),tujuan.text.toString(),jemputt.text.toString())
                        val activity : MainActivity = it.context as MainActivity
                        val fragmentTransaction: FragmentTransaction = activity.supportFragmentManager.beginTransaction()
                        fragmentTransaction.replace(R.id.frame_layout, fragment2)
                        fragmentTransaction.commit()
                    }

                },getDate.get(Calendar.YEAR),getDate.get(Calendar.MONTH),getDate.get(Calendar.DAY_OF_MONTH))
            datepick.show()
        })
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}