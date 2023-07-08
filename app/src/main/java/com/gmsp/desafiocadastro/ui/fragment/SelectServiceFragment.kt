package com.gmsp.desafiocadastro.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.gmsp.desafiocadastro.R
import com.gmsp.desafiocadastro.databinding.FragmentSelectServiceBinding
import com.gmsp.desafiocadastro.ui.ForwardViewModel


class SelectServiceFragment : Fragment() {

    private var binding: FragmentSelectServiceBinding? = null
    private val sharedViewModel: ForwardViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val fragmentBinding = FragmentSelectServiceBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            viewModel = sharedViewModel
            lifecycleOwner = viewLifecycleOwner
            selectServiceFragment = this@SelectServiceFragment
        }
    }

    fun onClickButtonContinue() {
        if (sharedViewModel.isValidationSelectService()) {
            findNavController().navigate(R.id.action_selectServiceFragment_to_dispatchFragment)
        } else {
            Toast.makeText(
                requireContext(),
                getString(R.string.massage_error_not_select_service),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}