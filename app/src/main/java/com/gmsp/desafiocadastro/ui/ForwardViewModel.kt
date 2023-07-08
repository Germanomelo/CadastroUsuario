package com.gmsp.desafiocadastro.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmsp.desafiocadastro.R
import com.gmsp.desafiocadastro.domain.model.AddresseeType
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

    /**
     * Data
     **/
    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName

    private val _userCPF = MutableLiveData<String>()
    val userCPF: LiveData<String> = _userCPF

    private val _userDateBirth = MutableLiveData<String>()
    val userDateBirth: LiveData<String> = _userDateBirth

    private val _userPhone = MutableLiveData<String>()
    val userPhone: LiveData<String> = _userPhone

    private val _motive = MutableLiveData<String>()
    val motive: LiveData<String> = _motive

    private val _serviceType = MutableLiveData<String>()
    val serviceType: LiveData<String> = _serviceType

    private val _addresseeType = MutableLiveData<AddresseeType?>()
    val addresseeType: LiveData<AddresseeType?> = _addresseeType

    /**
     * Error
     **/
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

    /**
     * validations
     **/

    private val _sendData = MutableLiveData<Boolean>()
    val sendData: LiveData<Boolean> = _sendData

    /**
     * functions
     **/

    init {
        resetData()
    }

    fun resetData() {
        _userName.value = ""
        _userCPF.value = ""
        _userDateBirth.value = ""
        _userPhone.value = ""
        _motive.value = ""
        _addresseeType.value = null
        _serviceType.value = ""
    }

    fun setUserName(name: String){
        _userName.value = name
    }

    fun setUserCPF(cpf: String){
        _userCPF.value = cpf
    }

    fun setUserPhone(phone: String){
        _userPhone.value = phone
    }

    fun setUserDateBirth(date: String){
        _userDateBirth.value = date
    }

    fun setMotive(motive: String) {
        _motive.value = motive
    }

    fun selectService(selectService: String) {
        _serviceType.value = selectService
    }

    fun selectAddressee(addresseeType: AddresseeType) {
        _addresseeType.value = addresseeType
    }

    fun isValidationRegisterUser(): Boolean {
        val validName = isNameValid()
        val validCpf = isCPFValid()
        val validDate = isDateBirthValid()
        val validPhone = isPhoneValid()
        return (validName && validCpf && validDate && validPhone)
    }

    fun isValidationSelectService(): Boolean {
        return isServiceValid()
    }

    fun isValidationDispatch() : Boolean {
        val validMotive = isMotiveValid()
        val validAddressee = isAddresseeValid()
        return (validMotive && validAddressee)
    }

    fun getAge(): String {
        if (userDateBirth.value.toString().isNotBlank())
            return DateManager.calculateUserAgetoString(userDateBirth.value!!)
        return ""
    }

    private fun getUser(): User{
        val dateBirth = DateManager.stringToDate(userDateBirth.value.toString())
        return User(
            name = userName.value.toString(),
            cpf = userCPF.value.toString(),
            phone = userPhone.value.toString(),
            dateBirth = dateBirth
        )
    }

    fun sendDataFoward() {
        viewModelScope.launch {
            try {
                val dateBirth = DateManager.stringToDate(userDateBirth.value.toString())
                val user = getUser()
                val to: AddresseeType? = addresseeType.value
                val serviceType = ServiceType.fromString(serviceType.value.toString())
                val forward = sendForwardUseCase.invoke(
                    from = user,
                    to = to!!,
                    serviceType = serviceType!!,
                    motive = motive.value.toString()
                )
                _sendData.value = (forward != null)
            } catch (e: Exception) {
                _sendData.value = false
            }
        }
    }

    /**
     * Validations private functions
     */

    private fun isDateBirthValid(): Boolean {
        if (userDateBirth.value.toString().isEmpty()) {
            _dateBirthErrorResId.value = R.string.massage_error_field_null
            return false
        } else if (userDateBirth.value?.length!! < 10) {
            _dateBirthErrorResId.value = R.string.massage_error_date
            return false
        } else if (DateManager.isDateGreaterThanToday(userDateBirth.value!!)) {
            _dateBirthErrorResId.value = R.string.massage_error_date_greater_than_current
            return false
        } else if (DateManager.calculateUserAge(userDateBirth.value!!) > 149) {
            _dateBirthErrorResId.value = R.string.massage_error_date_150_years_old
            return false
        }
        return true
    }

    private fun isCPFValid(): Boolean {
        if (userCPF.value?.isEmpty() == true) {
            _cpfErrorResId.value = R.string.massage_error_field_null
            return false
        } else if (userCPF.value?.length!! < 14) {
            _cpfErrorResId.value = R.string.massage_error_cpf
            return false
        }
        return true
    }

    private fun isNameValid(): Boolean {
        if (userName.value?.isEmpty() == true) {
            _nameErrorResId.value = R.string.massage_error_field_null
            return false
        }
        return true
    }

    private fun isPhoneValid(): Boolean {
        if (userPhone.value?.isNotBlank() == true && userPhone.value?.length!! < 15) {
            _phoneErrorResId.value = R.string.massage_error_phone
            return false
        }
        return true
    }

    private fun isServiceValid(): Boolean {
        if (serviceType.value?.isEmpty() == true) {
            _motiveErrorResId.value = R.string.massage_error_field_motivo
            return false
        }
        return true
    }

    private fun isMotiveValid(): Boolean {
        if (motive.value?.isEmpty() == true) {
            _motiveErrorResId.value = R.string.massage_error_field_motivo
            return false
        }
        return true
    }

    private fun isAddresseeValid(): Boolean {
        if (addresseeType.value == null) {
            _addresseeErrorResId.value = R.string.massage_error_field_addressee
            return false
        }
        return true
    }
}