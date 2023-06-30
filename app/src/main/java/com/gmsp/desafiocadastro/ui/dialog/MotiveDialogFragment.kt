package com.gmsp.desafiocadastro.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.gmsp.desafiocadastro.R
import com.gmsp.desafiocadastro.databinding.FragmentMotiveDialogBinding

class MotiveDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentMotiveDialogBinding
    private var motive: String? = null
    private var dialogListener: MotiveDialogListener? = null

    fun setDialogListener(listener: MotiveDialogListener, to: String?) {
        this.dialogListener = listener
        this.motive = to
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMotiveDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        setListeners()
        setInitDate(motive)
    }

    private fun setListeners() {
        binding.textSave.setOnClickListener {
            dialogListener?.onMotiveDialogResult(binding.editTextTextMultiLine.text.toString())
            dismiss()
        }

        binding.textCancel.setOnClickListener {
            dismiss()
        }
    }

    private fun setInitDate(motive: String?) {
        if(!motive.isNullOrBlank()){
            binding.editTextTextMultiLine.setText(motive)
        }
    }


}