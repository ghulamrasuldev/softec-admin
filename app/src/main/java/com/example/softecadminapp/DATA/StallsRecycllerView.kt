package com.example.softecadminapp.DATA

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.softecadminapp.Common
import com.example.softecadminapp.ObjectListiner
import com.example.softecadminapp.databinding.PaymentRequestCardBinding
import com.example.softecadminapp.databinding.StallsCardBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase

class StallsRecycllerView(val requests: ArrayList<StallsData>): RecyclerView.Adapter<StallsRecycllerView.stallViewHolder>(){

    private var auth: FirebaseAuth = Firebase.auth
    val db = FirebaseFirestore.getInstance()


    class stallViewHolder (val binding: StallsCardBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StallsRecycllerView.stallViewHolder {
        return stallViewHolder(
            StallsCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: StallsRecycllerView.stallViewHolder, position: Int) {
        val product = requests[position]


        holder.binding.apply {
            stallType.text = product.bidTitle
            noOfBidders.text = product.bidders.toString()
            bidRange.text = "Bid Range: ${product.minBid} - ${product.maxBid}"
            bidDescription.text = "${product.description_1}\n${product.description_4}\n${product.description_4}"
        }

        holder.binding.bidStatus.setOnClickListener{

            db.collection("stallBids").whereEqualTo("bidTitle", product.bidTitle)
                .get().addOnSuccessListener { task->
                    for (document in task){
                        val update: MutableMap<String, Any> = HashMap()
                        if (document.data["bidStatus"] == true){
                            update["bidStatus"] = false
                        }else{
                            update["bidStatus"] = true
                        }
                        db.collection("Stallbids").document(document.id).set(update, SetOptions.merge())
                    }
                }

        }
        holder.binding.bidDelete.setOnClickListener{

        }

    }

    override fun getItemCount(): Int {
        return requests.size
    }

}