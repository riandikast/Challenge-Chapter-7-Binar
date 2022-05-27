package com.listfilm.andika.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.listfilm.andika.R
import com.listfilm.andika.room.FavoriteFilm
import kotlinx.android.synthetic.main.item_fav.view.*
import kotlinx.android.synthetic.main.item_favorite.view.*


class AdapterFavorite (var onclick : (FavoriteFilm)-> Unit) : RecyclerView.Adapter<AdapterFavorite.ViewHolder>() {
    class ViewHolder(view : View) : RecyclerView.ViewHolder(view)
    var datafav : List<FavoriteFilm>? = null
    fun setDataFav(fav  : List<FavoriteFilm>){
        this.datafav = fav
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(R.layout.item_favorite, parent,false)
        return ViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView.context).load(datafav!![position].image).into(holder.itemView.gambarfav)
        holder.itemView.text1fav.text = datafav!![position].title

        holder.itemView.cardfav.setOnClickListener {
            onclick(datafav!![position])
        }
    }

    override fun getItemCount(): Int {
        if (datafav == null){
            return 0
        }else{
            return datafav!!.size
        }
    }
}