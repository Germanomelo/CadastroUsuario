package com.gmsp.desafiocadastro.ui.registeruser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmsp.desafiocadastro.R
import com.gmsp.desafiocadastro.domain.model.User
import com.gmsp.desafiocadastro.domain.usecase.RegisterUserUseCase
import com.gmsp.desafiocadastro.util.DateManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class RegisterUserViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel() {

    private val _nameErrorResId = MutableLiveData<Int>()
    val nameErrorresId: LiveData<Int> = _nameErrorResId

    private val _cpfErrorResId = MutableLiveData<Int>()
    val cpfErrorResId: LiveData<Int> = _cpfErrorResId

    private val _dateBirthErrorResId = MutableLiveData<Int>()
    val dateBirthErrorResId: LiveData<Int> = _dateBirthErrorResId

    private val _phoneErrorResId = MutableLiveData<Int>()
    val phoneErrorResId: LiveData<Int> = _phoneErrorResId

    private val _userCreated = MutableLiveData<User?>()
    val userCreated: LiveData<User?> = _userCreated

    private var isFormValid = false

    fun createUser(name: String, cpf: String, dateBirth: String, phone: String) =
        viewModelScope.launch {
            isFormValid = true
            validateName(name)
            validateDate(dateBirth)
            validateCPF(cpf)
            validatePhone(phone)

            if (isFormValid) {
                try {
                    val date = DateManager.stringToDate(dateBirth)
                    val user = registerUserUseCase.invoke(name, cpf, dateBirth = date, phone)
                    _userCreated.value = user
                } catch (e: Exception) {
                    _userCreated.value = null
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
        if (phone.isEmpty()) {
            _phoneErrorResId.value = R.string.massage_error_field_null
            isFormValid = false
        } else if (phone.length < 15) {
            _phoneErrorResId.value = R.string.massage_error_phone
            isFormValid = false
        }
    }

}