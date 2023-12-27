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
import com.example.challangechapter7.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding

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
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btDaftar.setOnClickListener {
            val valueUsername = binding.etUsername.text.toString()
            val valueEmail = binding.etEmail.text.toString()
            val valuePassword = binding.etPassword.text.toString()
            val valueKonfirmasi = binding.etKonfirmasi.text.toString()

            if (valueEmail.isNotEmpty() && valueUsername.isNotEmpty() && valuePassword.isNotEmpty() && valueKonfirmasi.isNotEmpty()) {
                if (valuePassword == valueKonfirmasi) {
                    viewModel.setUserCredentials(valueUsername, valueEmail, valuePassword)
                    navigateToLoginFragment()
                } else {
                    Toast.makeText(requireContext(), "Password dan konfirmasi password harus sama", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Email, Username, Password, dan Konfirmasi harus diisi", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToLoginFragment() {
        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
    }
}
