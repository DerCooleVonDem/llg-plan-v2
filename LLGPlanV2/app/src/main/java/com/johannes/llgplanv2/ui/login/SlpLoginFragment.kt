package com.johannes.llgplanv2.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import com.johannes.llgplanv2.R
import com.johannes.llgplanv2.databinding.FragmentSlpLoginBinding
import com.johannes.llgplanv2.settings.PrefKeys

class SlpLoginFragment(val loginActivity: LoginActivity) : Fragment() {

    companion object {
        fun newInstance(loginActivity: LoginActivity) = SlpLoginFragment(loginActivity)
    }

    lateinit var binding: FragmentSlpLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_slp_login, container, false)
        binding = FragmentSlpLoginBinding.bind(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.confirmButton.setOnClickListener {
            confirmCredentials()
        }

        binding.passwordEditText.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    confirmCredentials()
                    false
                }
                else -> false
            }
        }
    }

    private fun confirmCredentials() {
        if (
            binding.usernameEditText.text.toString().trim() == "LLG" &&
            binding.passwordEditText.text.toString() == "llg") {
                loginActivity.sharedPref.edit().putBoolean(PrefKeys.slpLoginDone, true).apply()
                loginActivity.nextFragment()
        } else {
            binding.credentialsWrongCardView.visibility = View.VISIBLE
        }
    }

}