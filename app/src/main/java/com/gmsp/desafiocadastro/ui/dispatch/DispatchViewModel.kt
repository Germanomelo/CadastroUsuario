package com.gmsp.desafiocadastro.ui.dispatch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmsp.desafiocadastro.R
import com.gmsp.desafiocadastro.domain.model.AddresseeEnum
import com.gmsp.desafiocadastro.domain.model.Forward
import com.gmsp.desafiocadastro.domain.model.ServiceType
import com.gmsp.desafiocadastro.domain.model.User
import com.gmsp.desafiocadastro.domain.usecase.SendForwardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DispatchViewModel @Inject constructor(
    private val sendForwardUseCase: SendForwardUseCase
) : ViewModel() {

    private val _motiveErrorResId = MutableLiveData<Int>()
    val motiveErrorresId: LiveData<Int> = _motiveErrorResId

    private val _addresseeErrorResId = MutableLiveData<Int>()
    val serviceErrorResId: LiveData<Int> = _addresseeErrorResId

    private val _confirmDataValidate = MutableLiveData<Boolean>()
    val confirmDataValidateLiveData: LiveData<Boolean> = _confirmDataValidate

    private val _sendData = MutableLiveData<Forward?>()
    val sendDataLiveData: LiveData<Forward?> = _sendData

    private var forward: Forward? = null
    private var isDataValid = false

    fun validateData(from: User, to: AddresseeEnum?, motive: String?, serviceType: ServiceType) {
        isDataValid = true
        validateMotive(motive)
        validateAddressee(to)

        if (isDataValid) {
            forward = Forward(from, to, motive, serviceType)
        }
        _confirmDataValidate.value = isDataValid
    }

    fun sendData() {
        viewModelScope.launch {
            try {
                forward = sendForwardUseCase.invoke(
                    forward?.from!!,
                    forward?.to!!,
                    forward?.motive!!,
                    forward?.service!!
                )
                _sendData.value = forward
            } catch (e: Exception) {
                _sendData.value = null
            }
        }
    }

    private fun validateMotive(motive: String?) {
        if (motive.isNullOrBlank()) {
            _motiveErrorResId.value = R.string.massage_error_field_motivo
            isDataValid = false
        }
    }

    private fun validateAddressee(Addressee: AddresseeEnum?) {
        if (Addressee == null) {
            _addresseeErrorResId.value = R.string.massage_error_field_addressee
            isDataValid = false
        }
    }
}