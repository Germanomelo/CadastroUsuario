package com.gmsp.desafiocadastro.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.gmsp.desafiocadastro.R
import com.gmsp.desafiocadastro.databinding.FragmentRegisterUserBinding
import com.gmsp.desafiocadastro.domain.model.Forward
import com.gmsp.desafiocadastro.ui.ForwardViewModel
import com.gmsp.desafiocadastro.util.CPFFormatter
import com.gmsp.desafiocadastro.util.DateFormatter
import com.gmsp.desafiocadastro.util.PhoneFormatter
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterUserFragment : Fragment() {

    private lateinit var binding: FragmentRegisterUserBinding
    private val sharedViewModel: ForwardViewModel by activityViewModels()

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
            sharedViewModel.createUser(name, cpf, dateBirth, phone)
        }
    }

    private fun observeViewModelEvents() {
        sharedViewModel.nameErrorResId.observe(viewLifecycleOwner) { stringResId ->
            binding.editName.error = getString(stringResId)
        }

        sharedViewModel.cpfErrorResId.observe(viewLifecycleOwner) { stringResId ->
            binding.editCpf.error = getString(stringResId)
        }

        sharedViewModel.dateBirthErrorResId.observe(viewLifecycleOwner) { stringResId ->
            binding.editDateBirth.error = getString(stringResId)
        }

        sharedViewModel.phoneErrorResId.observe(viewLifecycleOwner) { stringResId ->
            binding.editPhone.error = getString(stringResId)
        }

        sharedViewModel.goToScreenSelectService.observe(viewLifecycleOwner) { goTo ->
            if (goTo == true)
                findNavController().navigate(R.id.action_registerUserFragment_to_selectServiceFragment)
        }
    }
}