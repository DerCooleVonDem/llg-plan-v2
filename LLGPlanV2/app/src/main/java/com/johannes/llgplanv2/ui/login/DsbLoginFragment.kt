package com.johannes.llgplanv2.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import com.johannes.llgplanv2.ConstValues
import com.johannes.llgplanv2.R
import com.johannes.llgplanv2.databinding.FragmentDsbLoginBinding
import com.johannes.llgplanv2.settings.PrefKeys
import java.security.MessageDigest

class DsbLoginFragment(val loginActivity: LoginActivity) : Fragment() {

    companion object {
        fun newInstance(loginActivity: LoginActivity) = DsbLoginFragment(loginActivity)
    }

    lateinit var binding: FragmentDsbLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_dsb_login, container, false)
        binding = FragmentDsbLoginBinding.bind(view)
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
        val dsbUserInputBinding = binding.usernameEditText.text.toString().trim();
        val dsbPassInputBinding = binding.passwordEditText.text.toString();

        val dsbUserInputCorrect = generateSha256(dsbUserInputBinding) == ConstValues.DSB_USER_HASH;
        val dsbPassInputCorrect = generateSha256(dsbPassInputBinding) == ConstValues.DSB_PASSWORD_HASH;
        val dsbCredentialsCorrect = dsbUserInputCorrect && dsbPassInputCorrect;

        if (dsbCredentialsCorrect) { // here you would check if they are correct
            loginActivity.sharedPref.edit().also {
                it.putBoolean(PrefKeys.dsbLoginDone, true)
                it.putString(PrefKeys.dsbLoginUser, binding.usernameEditText.text.toString().trim())
                it.putString(PrefKeys.dsbLoginPassword, binding.passwordEditText.text.toString())
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