package com.example.crud.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.crud.R
import com.example.crud.UpdateProduct
import com.example.crud.model.ProductModel

class ProductAdapter(var context:Context,var data:ArrayList<ProductModel>):RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(view: View):RecyclerView.ViewHolder(view) {
        var productName:TextView=view.findViewById(R.id.labelName)
        var productPrice:TextView=view.findViewById(R.id.labelPrice)
        var productDesc:TextView=view.findViewById(R.id.labelDescription)
        var buttonEdit:Button=view.findViewById(R.id.editBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        var view=LayoutInflater.from(parent.context).inflate(R.layout.sample_product,parent,false)

        return ProductViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.productName.text=data[position].productName
        holder.productPrice.text=data[position].productPrice.toString()
        holder.productDesc.text=data[position].productDesc
        holder.buttonEdit.setOnClickListener {
            var intent= Intent(context,UpdateProduct::class.java)
            intent.putExtra("products",data[position])
            context.startActivity(intent)
        }
    }
    fun getProductID(position: Int):String{
        return data[position].id
    }
}