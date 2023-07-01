package com.gmsp.desafiocadastro.ui

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
import com.gmsp.desafiocadastro.util.DateManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForwardViewModel @Inject constructor(
    private val sendForwardUseCase: SendForwardUseCase
) : ViewModel() {

    var forward = Forward()

    private val _nameErrorResId = MutableLiveData<Int>()
    val nameErrorResId: LiveData<Int> = _nameErrorResId

    private val _cpfErrorResId = MutableLiveData<Int>()
    val cpfErrorResId: LiveData<Int> = _cpfErrorResId

    private val _dateBirthErrorResId = MutableLiveData<Int>()
    val dateBirthErrorResId: LiveData<Int> = _dateBirthErrorResId

    private val _phoneErrorResId = MutableLiveData<Int>()
    val phoneErrorResId: LiveData<Int> = _phoneErrorResId

    private val _motiveErrorResId = MutableLiveData<Int>()
    val motiveErrorresId: LiveData<Int> = _motiveErrorResId

    private val _addresseeErrorResId = MutableLiveData<Int>()
    val serviceErrorResId: LiveData<Int> = _addresseeErrorResId

    private val _confirmDataValidate = MutableLiveData<Boolean>()
    val confirmDataValidate: LiveData<Boolean> = _confirmDataValidate

    private val _sendData = MutableLiveData<Boolean>()
    val sendData: LiveData<Boolean> = _sendData

    private val _goToScreenSelectService = MutableLiveData<Boolean>()
    val goToScreenSelectService: LiveData<Boolean?> = _goToScreenSelectService

    private val _goToScreenDispatch = MutableLiveData<Boolean>()
    val goToScreenDispatch: LiveData<Boolean?> = _goToScreenDispatch

    private var isFormValid = false
    private var isDataValid = false

    fun createUser(name: String, cpf: String, dateBirth: String, phone: String) {
        isFormValid = true
        validateName(name)
        validateDate(dateBirth)
        validateCPF(cpf)
        validatePhone(phone)

        if (isFormValid) {
            val date = DateManager.stringToDate(dateBirth)
            forward.from = User(name, cpf, dateBirth = date, phone)
            _goToScreenSelectService.value = true
        } else {
            _goToScreenSelectService.value = false
        }
    }

    fun selectService(serviceType: ServiceType) {
        forward.service = serviceType
    }

    fun selectMotive(motive: String) {
        forward.motive = motive
    }

    fun selectAddressee(to: AddresseeEnum) {
        forward.to = to
    }

    fun nextScreenDispatch() {
        if (forward.service != null) {
            _goToScreenDispatch.value = true
        }
    }

    fun validateDataToSend() {
        isDataValid = true
        validateMotive(forward.motive)
        validateAddressee(forward.to)
        _confirmDataValidate.value = isDataValid
    }

    fun sendFoward() {
        viewModelScope.launch {
            try {
                forward = sendForwardUseCase.invoke(
                    forward.from!!,
                    forward.to!!,
                    forward.motive!!,
                    forward.service!!
                )
                _sendData.value = true
            } catch (e: Exception) {
                _sendData.value = false
            }
        }
    }

    private fun validateDate(date: String) {
        if (date.isEmpty()) {
            _dateBirthErrorResId.value = R.string.massage_error_field_null
            isFormValid = false
        } else if (date.length < 10) {
            _dateBirthErrorResId.value = R.string.massage_error_date
            isFormValid = false
        } else if (DateManager.isDateGreaterThanToday(date)) {
            _dateBirthErrorResId.value = R.string.massage_error_date_greater_than_current
            isFormValid = false
        } else if (DateManager.calculateUserAge(date) > 149) {
            _dateBirthErrorResId.value = R.string.massage_error_date_150_years_old
            isFormValid = false
        }
    }

    private fun validateCPF(cpf: String) {
        if (cpf.isEmpty()) {
            _cpfErrorResId.value = R.string.massage_error_field_null
            isFormValid = false
        } else if (cpf.length < 14) {
            _cpfErrorResId.value = R.string.massage_error_cpf
            isFormValid = false
        }
    }

    private fun validateName(nome: String) {
        if (nome.isEmpty()) {
            _nameErrorResId.value = R.string.massage_error_field_null
            isFormValid = false
        }
    }

    private fun validatePhone(phone: String) {
        if (phone.isNotBlank() && phone.length < 15) {
            _phoneErrorResId.value = R.string.massage_error_phone
            isFormValid = false
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

    fun clearData() {
        forward = Forward()
    }

}