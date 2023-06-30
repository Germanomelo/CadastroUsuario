package com.gmsp.desafiocadastro.ui.selectservice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gmsp.desafiocadastro.R
import com.gmsp.desafiocadastro.databinding.FragmentSelectServiceBinding
import com.gmsp.desafiocadastro.domain.model.Forward
import com.gmsp.desafiocadastro.domain.model.ServiceType
import com.gmsp.desafiocadastro.domain.model.User
import com.google.gson.Gson


class SelectServiceFragment : Fragment() {

    private lateinit var binding: FragmentSelectServiceBinding
    private var serviceType: ServiceType? = null
    private var user: User? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSelectServiceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    override fun onResume() {
        super.onResume()
        val jsonUser = arguments?.getString("User")
        user = Gson().fromJson(jsonUser, User::class.java)
    }

    private fun setListeners() {

        binding.radioForward.setOnClickListener {
            serviceType = ServiceType.FORWARD
        }

        binding.radioAccompany.setOnClickListener {
            serviceType = ServiceType.ACCOMPANY
        }

        binding.radioApproachSocial.setOnClickListener {
            serviceType = ServiceType.APPROACH_SOCIAL
        }

        binding.radioShelter.setOnClickListener {
            serviceType = ServiceType.SHELTER
        }

        binding.radioScfv.setOnClickListener {
            serviceType = ServiceType.SCFV
        }

        binding.buttonContinue.setOnClickListener {
            if (serviceType != null) {
                val forward = Forward(from = user, service = serviceType, motive = null, to = null)
                val forwardJson = Gson().toJson(forward)
                val bundle = bundleOf("Forward" to forwardJson)
                findNavController().navigate(
                    R.id.action_selectServiceFragment_to_dispatchFragment,
                    bundle
                )
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.massage_error_not_select_service),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}