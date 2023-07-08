package com.gmsp.desafiocadastro.ui.dialog

import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.gmsp.desafiocadastro.R
import com.gmsp.desafiocadastro.databinding.FragmentForwardDialogBinding
import com.gmsp.desafiocadastro.domain.model.AddresseeType

class ForwardDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentForwardDialogBinding
    private var to: AddresseeType? = null
    private var dialogListener: ForwardDialogListener? = null

    fun setDialogListener(listener: ForwardDialogListener, to: AddresseeType?) {
        this.dialogListener = listener
        this.to = to
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForwardDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        setupLayout()
        setListeners()
        setInitDate(to)
    }

    private fun setupLayout(){
        dialog?.window?.setGravity(Gravity.BOTTOM)
        setHeightPercent(60)
        dialog?.window?.setBackgroundDrawableResource(R.drawable.shape_white_radius_top)
    }

    private fun DialogFragment.setHeightPercent(percentage: Int) {
        val percent = percentage.toFloat() / 100
        val dm = Resources.getSystem().displayMetrics
        val rect = dm.run { Rect(0, 0, widthPixels, heightPixels) }
        val percentWidth = rect.height() * percent
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, percentWidth.toInt())
    }

    private fun setInitDate(to: AddresseeType?) {
        Log.d("Germano",to.toString())
        when (to) {
            AddresseeType.CRAS -> setSelectItem(binding.textCras)
            AddresseeType.CREAS -> setSelectItem(binding.textCreas)
            AddresseeType.JUDICIAL_POWER -> setSelectItem(binding.textJudPower)
            AddresseeType.PUBLIC_MINISTRY -> setSelectItem(binding.textMinPublic)
            AddresseeType.PUBLIC_DEFENSE -> setSelectItem(binding.textDefPublic)
            else -> {}
        }
    }

    private fun setListeners() {
        binding.textCras.setOnClickListener {
            setRemoveSelection(to)
            setSelectItem(binding.textCras)
            to = AddresseeType.CRAS
        }

        binding.textCreas.setOnClickListener {
            setRemoveSelection(to)
            setSelectItem(binding.textCreas)
            to = AddresseeType.CREAS
        }

        binding.textDefPublic.setOnClickListener {
            setRemoveSelection(to)
            setSelectItem(binding.textDefPublic)
            to = AddresseeType.PUBLIC_DEFENSE
        }

        binding.textJudPower.setOnClickListener {
            setRemoveSelection(to)
            setSelectItem(binding.textJudPower)
            to = AddresseeType.JUDICIAL_POWER
        }

        binding.textMinPublic.setOnClickListener {
            setRemoveSelection(to)
            setSelectItem(binding.textMinPublic)
            to = AddresseeType.PUBLIC_MINISTRY
        }

        binding.buttonSave.setOnClickListener {
            dialogListener?.onForwardDialogResult(to)
            dismiss()
        }
    }

    private fun setRemoveSelection(to: AddresseeType?) {
        when (to) {
            AddresseeType.CRAS -> setDefaultItem(binding.textCras)
            AddresseeType.CREAS -> setDefaultItem(binding.textCreas)
            AddresseeType.JUDICIAL_POWER -> setDefaultItem(binding.textJudPower)
            AddresseeType.PUBLIC_MINISTRY -> setDefaultItem(binding.textMinPublic)
            AddresseeType.PUBLIC_DEFENSE -> setDefaultItem(binding.textDefPublic)
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