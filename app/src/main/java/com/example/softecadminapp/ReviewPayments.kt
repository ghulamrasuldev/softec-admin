package com.example.softecadminapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.softecadminapp.DATA.PaymentsRecyclerViewAdaptor
import com.example.softecadminapp.DATA.paymentsRequestsData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_review_payments.*

class ReviewPayments : AppCompatActivity(), ObjectListiner {

    private var auth: FirebaseAuth = Firebase.auth
    val db = FirebaseFirestore.getInstance()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_payments)

        progressBar.isVisible = true
        var requests= ArrayList<paymentsRequestsData>()

        db.collection("payments")
            .whereEqualTo("paymentConfirmation", false)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents){
                    Log.d("RecyclerView: ", "loop")
                    requests.add(
                        paymentsRequestsData(
                            document.data["teamName"].toString(),
                            document.data["cnic"].toString(),
                            document.data["phoneNumber"].toString(),
                            document.data["teamMembers"].toString(),
                            document.data["amount"].toString(),
                            document.data["image"].toString(),
                            document.data["name"].toString(),
                            document.data["userId"].toString()
                        )
                    )
                    Log.d("Added Document: ", "True")
                }

                Log.d("List: ", "$requests")
                val requestsRecyclerView = payment_requests_recycler_view
                requestsRecyclerView.adapter= PaymentsRecyclerViewAdaptor(requests, this)
                requestsRecyclerView.layoutManager =  LinearLayoutManager(this)
                progressBar.isVisible = false


            }


    }

    override fun onItemClicked(paymentsRequestsData: paymentsRequestsData) {
        val addPaymentVerificationFragment = PaymentVerificationFragment()
        addPaymentVerificationFragment.show(supportFragmentManager, "...")
    }
}