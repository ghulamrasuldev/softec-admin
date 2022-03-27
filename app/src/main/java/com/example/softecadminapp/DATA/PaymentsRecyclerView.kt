package com.example.softecadminapp.DATA

import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.softecadminapp.Common
import com.example.softecadminapp.PaymentVerificationFragment
import com.example.softecadminapp.databinding.PaymentRequestCardBinding
import androidx.fragment.app.FragmentActivity
import com.example.softecadminapp.ObjectListiner


class PaymentsRecyclerViewAdaptor(val requests: ArrayList<paymentsRequestsData>, val listiner: ObjectListiner): RecyclerView.Adapter<PaymentsRecyclerViewAdaptor.paymentsViewHolder>(){


    class paymentsViewHolder (val binding: PaymentRequestCardBinding, val listiner: ObjectListiner): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentsRecyclerViewAdaptor.paymentsViewHolder {
        return paymentsViewHolder(
            PaymentRequestCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), listiner
        )
    }

    override fun onBindViewHolder(holder: PaymentsRecyclerViewAdaptor.paymentsViewHolder, position: Int) {
        val product = requests[position]
        val description_string = "${product.teamName} sent you PKR ${product.amount} for the ${product.competition}. Please verify using screenshot..."


        holder.binding.apply {
            paymentRequestDescription.text = description_string
//            var url = "${product.img}"
//            url = "https://www.lays.com/sites/lays.com/files/2021-07/XL%20Lay%27s%20Flamin%27%20Hot.png"
//            Log.d("Box","$url")
//            Picasso.get().load(url).fit().centerCrop().into(cartProductImage)
        }


        holder.binding.seeDetails.setOnClickListener{
            listiner.onItemClicked(requests[position])
            Common.teamName = product.teamName
        }
    }

    override fun getItemCount(): Int {
        return requests.size
    }

}