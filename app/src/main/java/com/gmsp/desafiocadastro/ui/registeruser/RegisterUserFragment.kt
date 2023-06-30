package com.gmsp.desafiocadastro.ui.registeruser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.gmsp.desafiocadastro.R
import com.gmsp.desafiocadastro.databinding.FragmentRegisterUserBinding
import com.gmsp.desafiocadastro.util.CPFFormatter
import com.gmsp.desafiocadastro.util.DateFormatter
import com.gmsp.desafiocadastro.util.PhoneFormatter
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterUserFragment : Fragment() {

    private val viewModel: RegisterUserViewModel by viewModels()
    private lateinit var binding: FragmentRegisterUserBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModelEvents()
        setListeners()
    }

    private fun setListeners() {
        binding.editCpf.addTextChangedListener(CPFFormatter(binding.editCpf))

        binding.editDateBirth.addTextChangedListener(DateFormatter(binding.editDateBirth))

        binding.editPhone.addTextChangedListener(PhoneFormatter(binding.editPhone))

        binding.buttonRegister.setOnClickListener {
            val name = binding.editName.text.toString()
            val cpf = binding.editCpf.text.toString()
            val dateBirth = binding.editDateBirth.text.toString()
            val phone = binding.editPhone.text.toString()
            viewModel.createUser(name, cpf, dateBirth, phone)
        }
    }

    private fun observeViewModelEvents() {
        viewModel.nameErrorresId.observe(viewLifecycleOwner) { stringResId ->
            binding.editName.error = getString(stringResId)
        }

        viewModel.cpfErrorResId.observe(viewLifecycleOwner) { stringResId ->
            binding.editCpf.error = getString(stringResId)
        }

        viewModel.dateBirthErrorResId.observe(viewLifecycleOwner) { stringResId ->
            binding.editDateBirth.error = getString(stringResId)
        }

        viewModel.phoneErrorResId.observe(viewLifecycleOwner) { stringResId ->
            binding.editPhone.error = getString(stringResId)
        }

        viewModel.userCreated.observe(viewLifecycleOwner) { user ->
            if (user == null) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.massage_error_create_user),
                    Toast.LENGTH_LONG
                ).show()
            } else {
                val userJson = Gson().toJson(user)
                val bundle = bundleOf("User" to userJson)
                findNavController().navigate(R.id.action_registerUserFragment_to_selectServiceFragment, bundle)
            }
        }
    }
}