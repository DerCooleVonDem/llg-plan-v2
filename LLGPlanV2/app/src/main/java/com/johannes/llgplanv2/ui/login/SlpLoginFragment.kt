package com.johannes.llgplanv2.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import com.johannes.llgplanv2.ConstValues
import com.johannes.llgplanv2.R
import com.johannes.llgplanv2.databinding.FragmentSlpLoginBinding
import com.johannes.llgplanv2.settings.PrefKeys
import java.security.MessageDigest

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
        val splUserInputBinding = binding.usernameEditText.text.toString().trim();
        val splPassInputBinding = binding.passwordEditText.text.toString();

        val splUserInputCorrect = generateSha256(splUserInputBinding) == ConstValues.SPL_USER_HASH;
        val splPassInputCorrect = generateSha256(splPassInputBinding) == ConstValues.SPL_PASSWORD_HASH;
        val splCredentialsCorrect = splUserInputCorrect && splPassInputCorrect;

        if (splCredentialsCorrect) { // here you would check if they are correct
                loginActivity.sharedPref.edit().also {
                    it.putBoolean(PrefKeys.slpLoginDone, true)
                    it.putString(PrefKeys.slpLoginUser, splUserInputBinding)
                    it.putString(PrefKeys.slpLoginPassword, splPassInputBinding)
                }.apply()
                loginActivity.nextFragment()
        } else {
            binding.credentialsWrongCardView.visibility = View.VISIBLE
        }
    }

    private fun generateSha256(input: String): String {
        val bytes = input.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("", { str, it -> str + "%02x".format(it) })
    }
}