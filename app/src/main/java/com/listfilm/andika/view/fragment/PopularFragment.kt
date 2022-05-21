package com.listfilm.andika.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.listfilm.andika.R
import com.listfilm.andika.view.adapter.AdapterPopular
import com.listfilm.andika.viewmodel.ViewModelMovie
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_popular.view.*


class PopularFragment : Fragment() {
    lateinit var adapterfilm : AdapterPopular
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_popular, container, false)
        view.listpopular.layoutManager = GridLayoutManager(requireContext(),3)
        adapterfilm = AdapterPopular(){
            val bund = Bundle()
            bund.putParcelable("detailfilm", it)
            view.findNavController().navigate(R.id.action_popularFragment_to_detailFragment,bund)

        }
        view.listpopular.adapter = adapterfilm

        getPopularMovie()

        return view
    }

    fun getPopularMovie(){

        val viewModel = ViewModelProvider(requireActivity()).get(ViewModelMovie::class.java)
        viewModel.getLiveFilmObserver().observe(requireActivity()) {
            if(it != null){
                adapterfilm.setDataFilm(it)
                adapterfilm.notifyDataSetChanged()
            }

        }
        viewModel.PopularMovieApi()


    }

}

