package com.blank.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.blank.home.databinding.FragmentMainBinding
import com.blank.home.ui.adapter.ViewPagerAdapter
import com.blank.home.ui.dashboard.DashboardViewModel
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding
    private val viewModelDashBoard: DashboardViewModel by sharedViewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding?.apply {
            tvLogout.setOnClickListener {
                viewModelDashBoard.logout()
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToLoginFragment())
            }
            viewPager2.adapter = ViewPagerAdapter(requireActivity())
            TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()

        }
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            com.blank.ui.R.string.list,
            com.blank.ui.R.string.map
        )
    }
}