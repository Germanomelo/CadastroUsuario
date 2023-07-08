package com.gmsp.desafiocadastro.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.gmsp.desafiocadastro.R
import com.gmsp.desafiocadastro.databinding.FragmentRegisterUserBinding
import com.gmsp.desafiocadastro.ui.ForwardViewModel
import com.gmsp.desafiocadastro.util.CPFFormatter
import com.gmsp.desafiocadastro.util.DateFormatter
import com.gmsp.desafiocadastro.util.PhoneFormatter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterUserFragment : Fragment() {

    private var binding: FragmentRegisterUserBinding? = null
    private val sharedViewModel: ForwardViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val fragmentBinding = FragmentRegisterUserBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            viewModel = sharedViewModel
            lifecycleOwner = viewLifecycleOwner
            registerUserFragment = this@RegisterUserFragment
        }
        observeViewModelEvents()
        setListeners()
    }

    private fun setListeners() {
        binding?.editCpf?.addTextChangedListener(CPFFormatter(binding?.editCpf!!))

        binding?.editDateBirth?.addTextChangedListener(DateFormatter(binding?.editDateBirth!!))

        binding?.editPhone?.addTextChangedListener(PhoneFormatter(binding?.editPhone!!))

    }

    private fun observeViewModelEvents() {
        sharedViewModel.nameErrorResId.observe(viewLifecycleOwner) { stringResId ->
            binding?.editName?.error = getString(stringResId)
        }

        sharedViewModel.cpfErrorResId.observe(viewLifecycleOwner) { stringResId ->
            binding?.editCpf?.error = getString(stringResId)
        }

        sharedViewModel.dateBirthErrorResId.observe(viewLifecycleOwner) { stringResId ->
            binding?.editDateBirth?.error = getString(stringResId)
        }

        sharedViewModel.phoneErrorResId.observe(viewLifecycleOwner) { stringResId ->
            binding?.editPhone?.error = getString(stringResId)
        }
    }

    fun onClickButtonRegister() {
        sharedViewModel.setUserName(binding?.editName?.text.toString())
        sharedViewModel.setUserCPF(binding?.editCpf?.text.toString())
        sharedViewModel.setUserDateBirth(binding?.editDateBirth?.text.toString())
        sharedViewModel.setUserPhone(binding?.editPhone?.text.toString())
        if(sharedViewModel.isValidationRegisterUser()){
            findNavController().navigate(R.id.action_registerUserFragment_to_selectServiceFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}