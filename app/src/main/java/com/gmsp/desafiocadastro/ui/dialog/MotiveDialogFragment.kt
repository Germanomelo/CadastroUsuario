package com.gmsp.desafiocadastro.ui.dialog

import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import android.view.Gravity
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
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMotiveDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setGravity(Gravity.BOTTOM)
        setHeightPercent(85)
        dialog?.window?.setBackgroundDrawableResource(R.drawable.shape_white_radius_top)
        setListeners()
        setInitDate(motive)
    }

    fun DialogFragment.setHeightPercent(percentage: Int) {
        val percent = percentage.toFloat() / 100
        val dm = Resources.getSystem().displayMetrics
        val rect = dm.run { Rect(0, 0, widthPixels, heightPixels) }
        val percentWidth = rect.height() * percent
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, percentWidth.toInt())
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