package com.gmsp.desafiocadastro.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.gmsp.desafiocadastro.R
import com.gmsp.desafiocadastro.databinding.FragmentForwardDialogBinding
import com.gmsp.desafiocadastro.domain.model.AddresseeEnum

class ForwardDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentForwardDialogBinding
    private var to: AddresseeEnum? = null
    private var dialogListener: ForwardDialogListener? = null

    fun setDialogListener(listener: ForwardDialogListener, to: AddresseeEnum?) {
        this.dialogListener = listener
        this.to = to
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForwardDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        setListeners()
        setInitDate(to)
    }

    private fun setListeners() {
        binding.textCras.setOnClickListener {
            setRemoveSelection(to)
            setSelectItem(binding.textCras)
            to = AddresseeEnum.CRAS
        }

        binding.textCreas.setOnClickListener {
            setRemoveSelection(to)
            setSelectItem(binding.textCreas)
            to = AddresseeEnum.CREAS
        }

        binding.textDefPublic.setOnClickListener {
            setRemoveSelection(to)
            setSelectItem(binding.textDefPublic)
            to = AddresseeEnum.PUBLIC_DEFENSE
        }

        binding.textJudPower.setOnClickListener {
            setRemoveSelection(to)
            setSelectItem(binding.textJudPower)
            to = AddresseeEnum.JUDICIAL_POWER
        }

        binding.textMinPublic.setOnClickListener {
            setRemoveSelection(to)
            setSelectItem(binding.textMinPublic)
            to = AddresseeEnum.PUBLIC_MINISTRY
        }


        binding.buttonSave.setOnClickListener {
            dialogListener?.onForwardDialogResult(to)
            dismiss()
        }
    }

    private fun setInitDate(to: AddresseeEnum?) {
        when (to) {
            AddresseeEnum.CRAS -> setSelectItem(binding.textCras)
            AddresseeEnum.CREAS -> setSelectItem(binding.textCreas)
            AddresseeEnum.JUDICIAL_POWER -> setSelectItem(binding.textJudPower)
            AddresseeEnum.PUBLIC_MINISTRY -> setSelectItem(binding.textMinPublic)
            AddresseeEnum.PUBLIC_DEFENSE -> setSelectItem(binding.textDefPublic)
            else -> {}
        }
    }

    private fun setRemoveSelection(to: AddresseeEnum?) {
        when (to) {
            AddresseeEnum.CRAS -> setDefaultItem(binding.textCras)
            AddresseeEnum.CREAS -> setDefaultItem(binding.textCreas)
            AddresseeEnum.JUDICIAL_POWER -> setDefaultItem(binding.textJudPower)
            AddresseeEnum.PUBLIC_MINISTRY -> setDefaultItem(binding.textMinPublic)
            AddresseeEnum.PUBLIC_DEFENSE -> setDefaultItem(binding.textDefPublic)
            else -> {}
        }
    }

    private fun setDefaultItem(text: TextView){
        text.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
        text.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
    }

    private fun setSelectItem(text: TextView){
        text.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_gray_80))
        text.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.gray_item))
    }
}