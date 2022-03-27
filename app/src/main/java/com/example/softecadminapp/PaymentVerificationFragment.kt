package com.example.softecadminapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.payment_varification_fragment.*
import android.graphics.BitmapFactory
import android.util.Base64
import android.widget.Toast
import com.google.firebase.firestore.SetOptions

class PaymentVerificationFragment : BottomSheetDialogFragment() {

    private var auth: FirebaseAuth = Firebase.auth
    val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.payment_varification_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //Listeners
        payment_approved.setOnClickListener{
            db.collection("payments")
                .whereEqualTo("teamName", Common.teamName).get().addOnSuccessListener { task->
                    for (document in task){
                        val update: MutableMap<String, Any> = HashMap()
                        update["paymentConfirmation"] = true
                        db.collection("payments").document(document.id).set(update, SetOptions.merge())
                    }
                }
            Toast.makeText(activity, "Approved", Toast.LENGTH_SHORT).show()
            activity?.finish()
        }

        payment_declined.setOnClickListener{
            db.collection("payments")
                .whereEqualTo("teamName", Common.teamName)
                .get()
                .addOnSuccessListener {task->
                    for(document in task){
                        db.collection("payments")
                            .document(document.id)
                            .delete()
                            .addOnSuccessListener {
                                Toast.makeText(activity, "Declined and Deleted", Toast.LENGTH_SHORT).show()
                                activity?.finish()
                        }
                    }
                }
        }

        db.collection("payments").whereEqualTo("teamName", Common.teamName).get()
            .addOnSuccessListener { documents->
                for (document in documents){
                    teamName.text = teamName.text.toString() + "${document.data["teamName"]}"
                    cnic.text = cnic.text.toString() + "${document.data["cnic"]}"
                    phoneNumber.text = phoneNumber.text.toString() + "${document.data["phoneNumber"]}"
                    teamMembers.text = teamName.text.toString() + "${document.data["teamMembers"]}"
                    amount.text = amount.text.toString() + "${document.data["amount"]}"
                    competition.text = competition.text.toString() + "${document.data["name"]}"

                    val bytes =
                        Base64.decode(document.data["image"].toString(), Base64.DEFAULT)


                    BitmapFactory.decodeByteArray(bytes, 0, bytes.size).also {
                        payment_image

                            .setImageBitmap(it)
                    }
                }

                progress_bar_payment_verification_sheet.isVisible = false
            }

    }
}