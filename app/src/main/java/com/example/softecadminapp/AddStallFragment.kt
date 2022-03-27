package com.example.softecadminapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.softecadminapp.Common.BIDDERS
import com.example.softecadminapp.Common.BID_STATUS
import com.example.softecadminapp.Common.BID_TITLE
import com.example.softecadminapp.Common.DESCRIPTION_FOUR
import com.example.softecadminapp.Common.DESCRIPTION_ONE
import com.example.softecadminapp.Common.DESCRIPTION_TWO
import com.example.softecadminapp.Common.MAX_BID
import com.example.softecadminapp.Common.MIN_BID
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.add_stall_fragment.*
import java.util.HashMap
import android.util.Log

import androidx.annotation.NonNull

import com.google.android.gms.tasks.OnFailureListener

import com.google.firebase.firestore.DocumentReference

import com.google.android.gms.tasks.OnSuccessListener


class AddStallFragment : BottomSheetDialogFragment() {

    private var auth: FirebaseAuth = Firebase.auth
    val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_stall_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        create_satall.setOnClickListener{
            val STALL: MutableMap<String, Any> = HashMap()
            STALL[BID_STATUS] = true
            STALL[BID_TITLE] = stall_title.editText?.text.toString()
            STALL[BIDDERS] = 0
            STALL[DESCRIPTION_ONE] = stall_description_1.editText?.text.toString()
            STALL[DESCRIPTION_TWO] = stall_description_2.editText?.text.toString()
            STALL[DESCRIPTION_FOUR] = stall_description_4.editText?.text.toString()
            STALL[MAX_BID] = stall_base_price.editText?.text.toString().toInt()
            STALL[MIN_BID] = stall_base_price.editText?.text.toString().toInt()

            db.collection("stallBids")
                .add(STALL)
                .addOnSuccessListener { documentReference ->
                    Log.d(
                        "Stall",
                        "DocumentSnapshot added with ID: " + documentReference.id
                    )
                    dismiss()
                }
                .addOnFailureListener { e -> Log.w("Stall", "Error adding document", e) }
        }

    }
}