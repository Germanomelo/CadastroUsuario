package com.gmsp.desafiocadastro.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.gmsp.desafiocadastro.R
import com.gmsp.desafiocadastro.databinding.FragmentSelectServiceBinding
import com.gmsp.desafiocadastro.domain.model.Forward
import com.gmsp.desafiocadastro.domain.model.ServiceType
import com.gmsp.desafiocadastro.domain.model.User
import com.gmsp.desafiocadastro.ui.ForwardViewModel
import com.google.gson.Gson


class SelectServiceFragment : Fragment() {

    private lateinit var binding: FragmentSelectServiceBinding
    private val sharedViewModel: ForwardViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSelectServiceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModelEvents()
        setListeners()
    }

    fun setListeners(){
        binding.buttonContinue.setOnClickListener {
            findNavController().navigate(R.id.action_selectServiceFragment_to_dispatchFragment)
        }

        binding.radioForward.setOnClickListener {
            sharedViewModel.selectService(ServiceType.FORWARD)
        }

        binding.radioAccompany.setOnClickListener {
            sharedViewModel.selectService(ServiceType.ACCOMPANY)
        }

        binding.radioApproachSocial.setOnClickListener {
            sharedViewModel.selectService(ServiceType.APPROACH_SOCIAL)
        }

        binding.radioShelter.setOnClickListener {
            sharedViewModel.selectService(ServiceType.SHELTER)
        }

        binding.radioScfv.setOnClickListener {
            sharedViewModel.selectService(ServiceType.SCFV)
        }

        binding.buttonContinue.setOnClickListener {
            sharedViewModel.nextScreenDispatch()
        }
    }

    private fun observeViewModelEvents() {
        sharedViewModel.goToScreenDispatch.observe(viewLifecycleOwner){goTo ->
            if (goTo == true)
                findNavController().navigate(R.id.action_selectServiceFragment_to_dispatchFragment)
            else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.massage_error_not_select_service),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

}