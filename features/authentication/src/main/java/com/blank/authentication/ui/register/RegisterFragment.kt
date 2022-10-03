package com.blank.authentication.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.blank.authentication.databinding.FragmentRegisterBinding
import com.blank.domain.model.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding

    private val viewModelRegister: RegisterViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleBackEvent()

        initView()
        initObserver()

        binding?.apply {
            toolbar.apply {
                setNavigationOnClickListener {
                    if (!findNavController().navigateUp())
                        requireActivity().finish()
                }
                setNavigationIcon(com.blank.ui.R.drawable.ic_baseline_arrow_back_ios_new_24)
            }
        }
    }

    private fun initObserver() {
        viewModelRegister.loginResponse.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding?.apply {
                        loading.visibility = View.VISIBLE
                    }
                }
                Resource.Status.SUCCESS -> {

                    findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToNavGraphMain())
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(context, it.error?.message ?: "", Toast.LENGTH_SHORT).show()
                    binding?.apply {
                        loading.visibility = View.GONE
                    }
                }
            }
        }
        viewModelRegister.registerResponse.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding?.apply {
                        btnRegister.visibility = View.INVISIBLE
                        loading.visibility = View.VISIBLE
                    }
                }
                Resource.Status.SUCCESS -> {

                }
                Resource.Status.ERROR -> {
                    Toast.makeText(context, it.error?.message ?: "", Toast.LENGTH_SHORT).show()
                    binding?.apply {
                        btnRegister.visibility = View.VISIBLE
                        loading.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun initView() {
        binding?.apply {

            btnRegister.setOnClickListener {
                viewModelRegister.register(
                    edEmail.text.toString(),
                    edPassword.text.toString(),
                    edName.text.toString()
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun handleBackEvent() {
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    isEnabled = false
                    activity?.onBackPressed()
                }
            })
    }

}