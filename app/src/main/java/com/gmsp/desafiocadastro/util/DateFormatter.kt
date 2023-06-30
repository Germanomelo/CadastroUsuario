package com.gmsp.desafiocadastro.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class DateFormatter(private val editText: EditText) : TextWatcher {
    private var isUpdating = false

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable?) {
        if (isUpdating) {
            isUpdating = false
            return
        }

        val date = s.toString().replace("[^\\d]".toRegex(), "")
        val maskedDate = formatterText(date, MASK)

        isUpdating = true
        editText.setText(maskedDate)
        editText.setSelection(maskedDate.length)
    }

    companion object {
        private const val MASK = "##/##/####"
    }
}
