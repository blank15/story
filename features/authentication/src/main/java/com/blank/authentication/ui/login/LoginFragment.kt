package com.blank.authentication.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.blank.authentication.databinding.FragmentLoginBinding
import com.blank.domain.model.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding
    private val viewModelLogin : LoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()
    }

    private fun initObserver() {
        viewModelLogin.loginResponse.observe(viewLifecycleOwner){
            when(it.status) {
                Resource.Status.LOADING -> {
                    binding?.apply {
                        btnLogin.visibility = View.INVISIBLE
                        loading.visibility = View.VISIBLE
                    }
                }
                Resource.Status.SUCCESS -> {
                    binding?.apply {
                        btnLogin.visibility = View.VISIBLE
                        loading.visibility = View.GONE
                    }

                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToMainFragment())
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(context, it.error?.message ?: "", Toast.LENGTH_SHORT).show()
                    binding?.apply {
                        btnLogin.visibility = View.VISIBLE
                        loading.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun initView() {
        binding?.apply {
            tvRegister.setOnClickListener {

                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
            }

            btnLogin.setOnClickListener {
                viewModelLogin.login(edLoginEmail.text.toString(), edLoginPassword.text.toString())
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}