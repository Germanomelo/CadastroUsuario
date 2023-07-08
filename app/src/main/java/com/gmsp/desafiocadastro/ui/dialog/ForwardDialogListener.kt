package com.gmsp.desafiocadastro.ui.dialog

import com.gmsp.desafiocadastro.domain.model.AddresseeType

interface ForwardDialogListener {

    fun onForwardDialogResult(result: AddresseeType?)
}