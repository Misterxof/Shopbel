package com.misterioes.shopbel.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.misterioes.shopbel.R
import com.misterioes.shopbel.databinding.FragmentLoginBinding
import com.misterioes.shopbel.domain.model.Status
import com.misterioes.shopbel.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val loginEditText = binding.login
        val passwordEditText = binding.password
        val loginButton = binding.loginButton
        val registrationButton = binding.registerButton

        loginButton.setOnClickListener {
            if (passwordEditText.text.toString().isNotEmpty() && passwordEditText.text.toString()
                    .isNotBlank()
                && loginEditText.text.toString().isNotEmpty() && loginEditText.text.toString()
                    .isNotBlank()
            ) {
                loginViewModel.loginUser(loginEditText.text.toString(), passwordEditText.text.toString())
            }
        }

        registrationButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_navigation_login_to_navigation_registration
            )
        }

        initState()

        return root
    }

    fun initState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.state.collect {
                    when (it) {
                        is Status.Error -> Toast.makeText(
                            context,
                            "Something went wrong!",
                            Toast.LENGTH_LONG
                        ).show()

                        is Status.Loading -> {}
                        is Status.Success -> {
                            Toast.makeText(context, "Loged in!", Toast.LENGTH_LONG).show()
                            val intent = Intent(context, MainActivity::class.java)
                            startActivity(intent)
                        }
                    }
                }
            }
        }
    }
}