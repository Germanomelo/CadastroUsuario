package com.gmsp.desafiocadastro.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.gmsp.desafiocadastro.R
import com.gmsp.desafiocadastro.databinding.FragmentSuccessDialogBinding


class SuccessDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentSuccessDialogBinding
    private var dialogListener: SuccessDialogListener? = null

    fun setDialogListener(listener: SuccessDialogListener) {
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
        binding = FragmentSuccessDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        setListeners()
    }

    private fun setListeners() {
        binding.buttonConfirm.setOnClickListener {
            dialogListener?.onSuccessDialogResult()
            dismiss()
        }
    }

}