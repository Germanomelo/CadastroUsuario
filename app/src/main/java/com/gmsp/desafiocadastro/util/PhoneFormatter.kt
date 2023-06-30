package com.gmsp.desafiocadastro.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class PhoneFormatter(private val editText: EditText) : TextWatcher {
    private var isUpdating = false

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable?) {
        if (isUpdating) {
            isUpdating = false
            return
        }

        val phone = s.toString().replace("[^\\d]".toRegex(), "")
        val maskedPhone =  formatterText(phone, MASK)

        isUpdating = true
        editText.setText(maskedPhone)
        editText.setSelection(maskedPhone.length)
    }

    companion object {
        private const val MASK = "(##) #####-####"
    }
}
