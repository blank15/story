package com.blank.home.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.blank.domain.model.Resource
import com.blank.home.databinding.FragmentDashboardBinding
import com.blank.model.story.StoryResult
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment : Fragment(), RecyclerViewStory.StoriesClicked {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding
    private lateinit var adapterStories: RecyclerViewStory

    private val viewModelDashBoard: DashboardViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelDashBoard.getStories()
        initObserver()
        initView()
    }

    private fun initView() {
        binding?.apply {
            tvLogout.setOnClickListener {
                viewModelDashBoard.logout()
                findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToLoginFragment())
            }
            btnAddStory.setOnClickListener {
                findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToAddStoryFragment())
            }

        }
    }

    private fun initObserver() {
        viewModelDashBoard.stories.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding?.loading?.visibility = View.VISIBLE
                }
                Resource.Status.SUCCESS -> {
                    binding?.loading?.visibility = View.GONE
                    it.data?.let { data ->
                        adapterStories = RecyclerViewStory(data, this)
                        binding?.rvStory?.adapter = adapterStories
                    }
                }
                Resource.Status.ERROR -> {
                    binding?.loading?.visibility = View.GONE
                    Toast.makeText(context, it.error?.message ?: "", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    override fun onPause() {
        super.onPause()
        viewModelDashBoard.cancelJob()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onItemClicked(item: StoryResult, img: AppCompatImageView) {

        findNavController().navigate(
            DashboardFragmentDirections.actionDashboardFragmentToDetailStoryFragment(item),
            FragmentNavigatorExtras(
                img to item.photoUrl
            )
        )
    }


}