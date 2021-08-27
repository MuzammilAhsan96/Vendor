package com.application.vendor.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.application.vendor.R
import com.application.vendor.callback.RootCallback
import com.application.vendor.model.food.Food
import com.application.vendor.utils.AppUtil
import com.application.vendor.utils.GlideUtils
import kotlinx.android.synthetic.main.item_view.view.*

class DashboardAdapter() :

    RecyclerView.Adapter<DashboardAdapter.ViewHolder?>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {


        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_view, parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val data = products?.get(position)
        holder.bind(data, position)
    }

    override fun getItemCount(): Int {
        return products?.size ?: 0
    }

    private var rootCallback: RootCallback<Any>? = null

    fun setRootCallback(rootCallback: RootCallback<Any>) {
        this.rootCallback = rootCallback
    }

    private var products: MutableList<Food>? = null
    fun setData(products: MutableList<Food>?) {
        this.products = products
        notifyDataSetChanged()
    }

    inner class ViewHolder(var binding: View) :
        RecyclerView.ViewHolder(binding.rootView) {
        fun bind(data: Food?, position: Int) {


            if (data?.images != null && data.images?.size ?: 0 > 0) {
                GlideUtils.loadImageFullWidth(binding.img1, data.images?.get(0))
            }

            if (data != null) {
                binding.tv2.text = AppUtil.getRupee(data.price)
            }
            binding.tv1.text = data?.name




        }
    }
}
