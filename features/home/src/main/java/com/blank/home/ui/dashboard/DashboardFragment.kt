package com.blank.home.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.blank.home.databinding.FragmentDashboardBinding
import com.blank.home.ui.adapter.LoadingStateAdapter
import com.blank.home.ui.adapter.RecyclerViewStory
import com.blank.model.database.StoryModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class DashboardFragment : Fragment(), RecyclerViewStory.StoriesClicked {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding
    private lateinit var adapterStories: RecyclerViewStory

    private val viewModelDashBoard: DashboardViewModel by sharedViewModel()

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

            btnAddStory.setOnClickListener {
                findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToAddStoryFragment())
            }
            adapterStories = RecyclerViewStory(this@DashboardFragment)
            rvStory.adapter = adapterStories.withLoadStateFooter(
                footer = LoadingStateAdapter {
                    adapterStories.retry()
                }
            )
        }
    }

    private fun initObserver() {
        viewModelDashBoard.stories.observe(viewLifecycleOwner) {
            adapterStories.submitData(lifecycle, it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onItemClicked(item: StoryModel, img: AppCompatImageView) {

        item.photoUrl?.let {
            findNavController().navigate(
                DashboardFragmentDirections.actionDashboardFragmentToDetailStoryFragment(item),
                FragmentNavigatorExtras(
                    img to it
                )
            )
        }

    }


}