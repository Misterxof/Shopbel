package com.misterioes.shopbel.ui.register

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.misterioes.shopbel.R
import com.misterioes.shopbel.databinding.FragmentLoginBinding
import com.misterioes.shopbel.databinding.FragmentRegistrationBinding
import com.misterioes.shopbel.domain.model.Status
import com.misterioes.shopbel.ui.MainActivity
import com.misterioes.shopbel.ui.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegistrationFragment : Fragment() {
    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    private val registrationViewModel: RegistrationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val loginEditText = binding.login
        val passwordEditText = binding.password
        val password2EditText = binding.password2
        val fioEditText = binding.fio
        val registrationButton = binding.registerButton

        loginEditText.setText("shopbel@gmail.com")
        passwordEditText.setText("test123")
        password2EditText.setText("test123")
        fioEditText.setText("test")

        registrationButton.setOnClickListener {
            if (passwordEditText.text.toString().isNotEmpty() && passwordEditText.text.toString().isNotBlank()
                && passwordEditText.text.toString() == password2EditText.text.toString()) {
                if (loginEditText.text.toString().isNotEmpty() && loginEditText.text.toString().isNotBlank() &&
                    fioEditText.text.toString().isNotEmpty() && fioEditText.text.toString().isNotBlank()) {
                    Log.e("REG", "reregister ${loginEditText.text.toString()}")
                    registrationViewModel.registerUser(loginEditText.text.toString(), passwordEditText.text.toString(), fioEditText.text.toString())
                }
            } else {
                Log.e("REG", "passwordEditText")
                Log.e("REG", passwordEditText.toString())
                Log.e("REG", password2EditText.toString())
                Log.e("REG", (passwordEditText.toString().equals(password2EditText.toString())).toString())
            }
        }

        initState()

        return root
    }

    fun initState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                registrationViewModel.state.collect {
                    when(it) {
                        is Status.Error -> Toast.makeText(context, "Something went wrong!", Toast.LENGTH_LONG).show()
                        is Status.Loading -> {}
                        is Status.Success -> {
                            Toast.makeText(context, "Order is created!", Toast.LENGTH_LONG).show()
                            findNavController().navigate(
                                R.id.action_navigation_registration_to_navigation_login
                            )
                        }
                    }
                }
            }
        }
    }
}