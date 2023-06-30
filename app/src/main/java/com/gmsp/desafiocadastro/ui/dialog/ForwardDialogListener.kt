package com.gmsp.desafiocadastro.ui.dialog

import com.gmsp.desafiocadastro.domain.model.AddresseeEnum

interface ForwardDialogListener {

    fun onForwardDialogResult(result: AddresseeEnum?)
}