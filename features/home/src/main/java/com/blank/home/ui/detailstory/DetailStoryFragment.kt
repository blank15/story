package com.blank.home.ui.detailstory

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blank.home.R

class DetailStoryFragment : Fragment() {

    companion object {
        fun newInstance() = DetailStoryFragment()
    }

    private lateinit var viewModel: DetailStoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_story, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailStoryViewModel::class.java)
        // TODO: Use the ViewModel
    }

}