package com.gmsp.desafiocadastro.ui.dispatch


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.gmsp.desafiocadastro.R
import com.gmsp.desafiocadastro.databinding.FragmentDispatchBinding
import com.gmsp.desafiocadastro.domain.model.AddresseeEnum
import com.gmsp.desafiocadastro.domain.model.Forward
import com.gmsp.desafiocadastro.ui.dialog.ConfirmDialogFragment
import com.gmsp.desafiocadastro.ui.dialog.ConfirmDialogListener
import com.gmsp.desafiocadastro.ui.dialog.ForwardDialogFragment
import com.gmsp.desafiocadastro.ui.dialog.ForwardDialogListener
import com.gmsp.desafiocadastro.ui.dialog.MotiveDialogFragment
import com.gmsp.desafiocadastro.ui.dialog.MotiveDialogListener
import com.gmsp.desafiocadastro.ui.dialog.SuccessDialogFragment
import com.gmsp.desafiocadastro.ui.dialog.SuccessDialogListener
import com.gmsp.desafiocadastro.util.DateManager
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DispatchFragment : Fragment(), ForwardDialogListener, MotiveDialogListener, ConfirmDialogListener, SuccessDialogListener {

    private val viewModel: DispatchViewModel by viewModels()
    private lateinit var binding: FragmentDispatchBinding
    private var forward: Forward? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        binding = FragmentDispatchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModelEvents()
        setListeners()
    }

    override fun onResume() {
        super.onResume()
        val jsonForward = arguments?.getString("Forward")
        forward = Gson().fromJson(jsonForward, Forward::class.java)

        setData()
    }

    private fun setData() {
        binding.textName.text = forward?.from?.name
        binding.textCpfValue.text = forward?.from?.cpf
        forward?.from?.dateBirth?.let { dateOfBirth ->
            binding.textDataBirthValue.text = DateManager.dateToString(dateOfBirth)
            binding.textAgeValue.text = DateManager.calculateUserAgetoString(dateOfBirth)
        }
    }

    private fun setListeners() {

        binding.viewMotive.setOnClickListener {
            val dialogFragment = MotiveDialogFragment()
            dialogFragment.setDialogListener(this, forward?.motive)
            dialogFragment.show(parentFragmentManager, "TagMotive")
        }

        binding.viewForwardTo.setOnClickListener {
            val dialogFragment = ForwardDialogFragment()
            dialogFragment.setDialogListener(this, forward?.to)
            dialogFragment.show(parentFragmentManager, "TagForward")
        }

        binding.buttonCancel.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.buttonForward.setOnClickListener {
            if (forward != null) {
                viewModel.validateData(
                    from = forward?.from!!,
                    to = forward?.to,
                    motive = forward?.motive,
                    serviceType = forward?.service!!
                )
            }
        }
    }

    private fun observeViewModelEvents() {

        viewModel.serviceErrorResId.observe(viewLifecycleOwner) { stringResId ->
            binding.textAddresseeValue.text = getString(stringResId)
            binding.textAddresseeValue.visibility = View.VISIBLE
            binding.textAddresseeValue.setTextColor(
                (ContextCompat.getColor(
                    requireContext(),
                    R.color.red_error
                ))
            )
        }

        viewModel.motiveErrorresId.observe(viewLifecycleOwner) { stringResId ->
            binding.textMotiveValue.text = getString(stringResId)
            binding.textMotiveValue.visibility = View.VISIBLE
            binding.textMotiveValue.setTextColor(
                (ContextCompat.getColor(
                    requireContext(),
                    R.color.red_error
                ))
            )
        }

        viewModel.confirmDataValidateLiveData.observe(viewLifecycleOwner) { result ->
            if(result){
                val dialogFragment = ConfirmDialogFragment()
                dialogFragment.setDialogListener(this)
                dialogFragment.show(parentFragmentManager, "TagConfirm")
            }
        }

        viewModel.sendDataLiveData.observe(viewLifecycleOwner) {result ->
            if (result == null) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.massage_error_send_forward),
                    Toast.LENGTH_LONG
                ).show()
            } else {
                val dialogFragment = SuccessDialogFragment()
                dialogFragment.setDialogListener(this)
                dialogFragment.show(parentFragmentManager, "TagSuccess")
            }
        }
    }

    override fun onForwardDialogResult(result: AddresseeEnum?) {
        if(result != null) {
            forward?.to = result
            binding.textAddresseeValue.text = result?.value.toString()
            binding.textAddresseeValue.visibility = View.VISIBLE
            binding.textAddresseeValue.setTextColor(
                (ContextCompat.getColor(
                    requireContext(),
                    R.color.gray
                ))
            )
        }
    }

    override fun onMotiveDialogResult(result: String) {
        if(result.isNotBlank()) {
            forward?.motive = result
            binding.textMotiveValue.text = getString(R.string.ok)
            binding.textMotiveValue.visibility = View.VISIBLE
            binding.textMotiveValue.setTextColor(
                (ContextCompat.getColor(
                    requireContext(),
                    R.color.gray
                ))
            )
        }
    }

    override fun onConfirmDialogResult() {
        viewModel.sendData()
    }

    override fun onSuccessDialogResult() {
        findNavController().navigate(R.id.action_dispatchFragment_to_addUserFragment )
        onDestroy()
    }



}