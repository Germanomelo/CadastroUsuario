package com.gmsp.desafiocadastro.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class CPFFormatter(private val editText: EditText) : TextWatcher {
    private var isUpdating = false

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable?) {
        if (isUpdating) {
            isUpdating = false
            return
        }

        val cpf = s.toString().replace("[^\\d]".toRegex(), "")
        val maskedCpf = formatterText(cpf, MASK)

        isUpdating = true
        editText.setText(maskedCpf)
        editText.setSelection(maskedCpf.length)
    }

    companion object {
        private const val MASK = "###.###.###-##"
    }
}