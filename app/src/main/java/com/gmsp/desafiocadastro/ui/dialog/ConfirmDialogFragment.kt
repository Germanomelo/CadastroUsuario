package com.gmsp.desafiocadastro.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.gmsp.desafiocadastro.R
import com.gmsp.desafiocadastro.databinding.FragmentConfirmDialogBinding

class ConfirmDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentConfirmDialogBinding
    private var dialogListener: ConfirmDialogListener? = null

    fun setDialogListener(listener: ConfirmDialogListener) {
        this.dialogListener = listener
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConfirmDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        setListeners()
    }

    private fun setListeners() {
        binding.buttonConfirm.setOnClickListener {
            dialogListener?.onConfirmDialogResult()
            dismiss()
        }

        binding.buttonCancel.setOnClickListener {
            dismiss()
        }
    }
}