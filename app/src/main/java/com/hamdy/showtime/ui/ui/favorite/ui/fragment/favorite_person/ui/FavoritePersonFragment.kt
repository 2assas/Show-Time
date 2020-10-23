package com.hamdy.showtime.ui.ui.favorite.ui.fragment.favorite_person.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.hamdy.showtime.R
import com.hamdy.showtime.databinding.FavoritePersonFragmentBinding
import com.hamdy.showtime.ui.ui.favorite.adapter.FavoriteAdapter
import com.hamdy.showtime.ui.ui.favorite.model.FavoriteItem
import com.hamdy.showtime.ui.util.CenterZoomLayoutManager
import com.hamdy.showtime.ui.util.ImageUrlBase

class FavoritePersonFragment : Fragment() {

    private lateinit var binding: FavoritePersonFragmentBinding
    private lateinit var snapHelper: LinearSnapHelper
    private lateinit var arr: List<FavoriteItem>
    private lateinit var viewModel: FavoritePersonViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding =FavoritePersonFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FavoritePersonViewModel::class.java)
        viewModel.getFavorite()
        val layoutManger= CenterZoomLayoutManager(context, RecyclerView.HORIZONTAL, false)
        binding.favoriteRecyclerView.layoutManager=layoutManger
        binding.favoriteRecyclerView.adapter= FavoriteAdapter()
        snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.favoriteRecyclerView)
        val favoriteAdapter= FavoriteAdapter()
        binding.favoriteRecyclerView.adapter = favoriteAdapter
        viewModel.listFavoriteMovie.observe(viewLifecycleOwner, {
            arr=it
            binding.backGroundFavorite.load(ImageUrlBase+arr[0].poster){
                crossfade(true)
                crossfade(1000)
            }

            favoriteAdapter.setFavorite(it, R.id.action_favoritePersonFragment_to_navigation_person)
        })
        binding.favoriteRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val centerView = snapHelper.findSnapView(layoutManger)
                    val pos: Int = layoutManger.getPosition(centerView!!)
                    binding.backGroundFavorite.load(ImageUrlBase+arr[pos].poster){
                        crossfade(true)
                        crossfade(1000)
                    }
                    Log.e("Snapped Item Position:", "" + pos)
                }
            }
        })

    }

}