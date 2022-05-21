package com.listfilm.andika.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.listfilm.andika.R
import com.listfilm.andika.model.movie.GetMovie
import kotlinx.android.synthetic.main.item_film.view.*

class AdapterHome (private val onclick : (GetMovie)->Unit) : RecyclerView.Adapter<AdapterHome.ViewHolder>() {
    class ViewHolder(view : View) : RecyclerView.ViewHolder(view)
    var datafilm : List<GetMovie>? = null


    fun setDataFilm(film  : List<GetMovie>){
        this.datafilm = film
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(R.layout.item_film, parent,false)
        return ViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView.context).load(datafilm!![position].image).into(holder.itemView.gambar
        )

        holder.itemView.text1.text = datafilm!![position].title
////        holder.itemView.text2.text = datafilm!![position].director
//        holder.itemView.text3.text = datafilm!![position].releaseDate

        holder.itemView.card.setOnClickListener {
            onclick(datafilm!![position])

        }
    }

    override fun getItemCount(): Int {
        if (datafilm == null){
            return 0
        }else{
            return datafilm!!.size
        }
    }

}