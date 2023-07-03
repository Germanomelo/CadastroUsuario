package com.gmsp.desafiocadastro.ui.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.gmsp.desafiocadastro.R
import com.gmsp.desafiocadastro.databinding.FragmentDispatchBinding
import com.gmsp.desafiocadastro.domain.model.AddresseeEnum
import com.gmsp.desafiocadastro.ui.ForwardViewModel
import com.gmsp.desafiocadastro.ui.dialog.ConfirmDialogFragment
import com.gmsp.desafiocadastro.ui.dialog.ConfirmDialogListener
import com.gmsp.desafiocadastro.ui.dialog.ForwardDialogFragment
import com.gmsp.desafiocadastro.ui.dialog.ForwardDialogListener
import com.gmsp.desafiocadastro.ui.dialog.MotiveDialogFragment
import com.gmsp.desafiocadastro.ui.dialog.MotiveDialogListener
import com.gmsp.desafiocadastro.ui.dialog.SuccessDialogFragment
import com.gmsp.desafiocadastro.ui.dialog.SuccessDialogListener
import com.gmsp.desafiocadastro.util.DateManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DispatchFragment : Fragment(), ForwardDialogListener, MotiveDialogListener,
    ConfirmDialogListener, SuccessDialogListener {

    private var binding: FragmentDispatchBinding? = null
    private val sharedViewModel: ForwardViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val fragmentBinding  = FragmentDispatchBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            viewModel = sharedViewModel
            dispatchFragment = this@DispatchFragment
        }

        observeViewModelEvents()
        setData()
    }


    private fun setData() {
        binding?.textName?.text = sharedViewModel.forward?.from?.name
        binding?.textCpfValue?.text = sharedViewModel.forward?.from?.cpf
        sharedViewModel.forward?.from?.dateBirth?.let { dateOfBirth ->
            binding?.textDataBirthValue?.text = DateManager.dateToString(dateOfBirth)
            binding?.textAgeValue?.text = DateManager.calculateUserAgetoString(dateOfBirth)
        }
    }

    fun onClickButtonForwarding(){
        sharedViewModel.validateDataToSend()
    }
    fun onClickCancel(){
        findNavController().navigate(R.id.action_dispatchFragment_to_registerUserFragment)
    }

    fun onClickViewFoward(){
        val dialogFragment = ForwardDialogFragment()
        dialogFragment.setDialogListener(this, sharedViewModel.forward?.to)
        dialogFragment.show(parentFragmentManager, "TagForward")
    }

    fun onClickViewMotive(){
        val dialogFragment = MotiveDialogFragment()
        dialogFragment.setDialogListener(this, sharedViewModel.forward?.motive)
        dialogFragment.show(parentFragmentManager, "TagMotive")
    }

    private fun observeViewModelEvents() {
        sharedViewModel.serviceErrorResId.observe(viewLifecycleOwner) { stringResId ->
            binding?.textAddresseeValue?.text = getString(stringResId)
            binding?.textAddresseeValue?.visibility = View.VISIBLE
            binding?.textAddresseeValue?.setTextColor(
                (ContextCompat.getColor(
                    requireContext(),
                    R.color.red_error
                ))
            )
        }

        sharedViewModel.motiveErrorresId.observe(viewLifecycleOwner) { stringResId ->
            binding?.textMotiveValue?.text = getString(stringResId)
            binding?.textMotiveValue?.visibility = View.VISIBLE
            binding?.textMotiveValue?.setTextColor(
                (ContextCompat.getColor(
                    requireContext(),
                    R.color.red_error
                ))
            )
        }

        sharedViewModel.confirmDataValidate.observe(viewLifecycleOwner) { result ->
            if(result){
                val dialogFragment = ConfirmDialogFragment()
                dialogFragment.setDialogListener(this)
                dialogFragment.show(parentFragmentManager, "TagConfirm")
            }
        }

        sharedViewModel.sendData.observe(viewLifecycleOwner) {result ->
            if (!result) {
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
            sharedViewModel.selectAddressee(result)
            binding?.textAddresseeValue?.text = result.value
            binding?.textAddresseeValue?.visibility = View.VISIBLE
            binding?.textAddresseeValue?.setTextColor(
                (ContextCompat.getColor(
                    requireContext(),
                    R.color.gray
                ))
            )
        }
    }

    override fun onMotiveDialogResult(result: String) {
        if(result.isNotBlank()) {
            sharedViewModel.selectMotive(result)
            binding?.textMotiveValue?.text = getString(R.string.ok)
            binding?.textMotiveValue?.visibility = View.VISIBLE
            binding?.textMotiveValue?.setTextColor(
                (ContextCompat.getColor(
                    requireContext(),
                    R.color.gray
                ))
            )
        }
    }

    override fun onConfirmDialogResult() {
        sharedViewModel.sendFoward()
    }

    override fun onSuccessDialogResult() {
        findNavController().navigate(R.id.action_dispatchFragment_to_registerUserFragment)
    }

}