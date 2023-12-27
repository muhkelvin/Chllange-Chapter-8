package com.example.challangechapter7.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.challangechapter7.R
import com.example.challangechapter7.auth.AuthManajer
import com.example.challangechapter7.auth.AuthRepository
import com.example.challangechapter7.auth.AuthViewModel
import com.example.challangechapter7.auth.datastore
import com.example.challangechapter7.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    private val authManager: AuthManajer by lazy {
        AuthManajer.getInstance(requireContext().datastore)
    }

    private val repository: AuthRepository by lazy {
        AuthRepository(authManager)
    }

    private val viewModel: AuthViewModel by viewModels {
        AuthViewModel.Factory(repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btLogin.setOnClickListener {
            val valueEmail = binding.etEmail.text.toString()
            val valuePassword = binding.etPassword.text.toString()

            if (valueEmail.isNotEmpty() && valuePassword.isNotEmpty()) {
                viewModel.login(valueEmail, valuePassword).observe(viewLifecycleOwner) { isSuccess ->
                    if (isSuccess) {
                        navigateToHomeFragment()
                    } else {
                        Toast.makeText(requireContext(), "Login Gagal. Cek Email dan Password", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Email dan Password harus diisi", Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvRegister.setOnClickListener {
            navigateToRegisterFragment()
        }
    }

    private fun navigateToHomeFragment() {
        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
    }

    private fun navigateToRegisterFragment() {
        findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
    }
}