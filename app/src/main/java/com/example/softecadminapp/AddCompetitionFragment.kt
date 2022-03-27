package com.example.softecadminapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.softecadminapp.DATA.PaymentsRecyclerViewAdaptor
import com.example.softecadminapp.DATA.StallsData
import com.example.softecadminapp.DATA.StallsRecycllerView
import com.example.softecadminapp.DATA.paymentsRequestsData
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_review_payments.*
import kotlinx.android.synthetic.main.activity_review_payments.payment_requests_recycler_view
import kotlinx.android.synthetic.main.add_competition_fragment.*

class AddCompetitionFragment: BottomSheetDialogFragment() {

    private var auth: FirebaseAuth = Firebase.auth
    val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_competition_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var requests= ArrayList<StallsData>()

        db.collection("stallBids")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents){
                    Log.d("RecyclerView: ", "loop")
                    requests.add(
                        StallsData(
                            document.data["bidTitle"].toString(),
                            document.data["bidders"].toString().toDouble(),
                            document.data["description_1"].toString(),
                            document.data["description_4"].toString(),
                            document.data["maxBid"].toString().toDouble(),
                            document.data["minBid"].toString().toDouble()
                        )
                    )
                    Log.d("Added Document: ", "True")
                }

                Log.d("List: ", "$requests")
                val requestsRecyclerView = stalls_recycler_view
                requestsRecyclerView.adapter= StallsRecycllerView(requests)
                requestsRecyclerView.layoutManager =  LinearLayoutManager(context)
                progressBar_stalls.isVisible = false
            }
    }
}