package com.example.uinavegacion.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uinavegacion.domain.validateEmail
import com.example.uinavegacion.domain.validateConfirm
import com.example.uinavegacion.domain.validateLettersOnly
import com.example.uinavegacion.domain.validateStrongPassword
import com.example.uinavegacion.domain.validatePhoneDigitsOnly
import com.example.uinavegacion.domain.UserRepository
import com.example.uinavegacion.domain.AdminRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class LoginUiState(
    val email: String = "", //campo email del formulario
    val pass: String = "", //campo clave del formulario
    val emailError: String? = null, //campo error de correo
    val passError: String? = null, //campo error de clave
    val isSubmitting: Boolean = false, //flag de carga
    val canSubmit: Boolean = false, //visibilidad del botón
    val success: Boolean = false, //resultado ok del formulario
    val errorMsg: String? = null // error general (credenciales son incorrectas)
)

data class RegisterUiState(
    //variables para los campos
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val pass: String = "",
    val confirm: String = "",
    //variables para los errores en los campos
    val nameError: String? = null,
    val emailError: String? = null,
    val phoneError: String? = null,
    val passError: String? = null,
    val confirmError: String? = null,

    val isSubmitting: Boolean = false, // flag de carga
    val canSubmit: Boolean = false, //visibilidad del botón
    val success: Boolean = false, //formulario OK
    val errorMsg: String? = null //error general (usuario ya existe)

)


//clase para manipular la logica de Login y Register
class AuthViewModel: ViewModel(){
    // Repositorio para manejar usuarios
    private val userRepository = UserRepository()
    // Repositorio para manejar administradores
    private val _adminRepository = AdminRepository()
    val adminRepository: AdminRepository get() = _adminRepository
    // Flujos de estado para observar desde la UI
    private val _login = MutableStateFlow(LoginUiState())   // Estado interno (Login)
    val login: StateFlow<LoginUiState> = _login             // Exposición inmutable

    private val _register = MutableStateFlow(RegisterUiState()) // Estado interno (Registro)
    val register: StateFlow<RegisterUiState> = _register        // Exposición inmutable

    // ----------------- LOGIN: handlers y envío -----------------

    fun onLoginEmailChange(value: String) {                 // Handler cuando cambia el email
        _login.update { it.copy(email = value, emailError = validateEmail(value)) } // Guardamos + validamos
        recomputeLoginCanSubmit()                           // Recalculamos habilitado
    }

    fun onLoginPassChange(value: String) {                  // Handler cuando cambia la contraseña
        _login.update { it.copy(pass = value) }             // Guardamos (sin validar fuerza aquí)
        recomputeLoginCanSubmit()                           // Recalculamos habilitado
    }

    private fun recomputeLoginCanSubmit() {                 // Regla para habilitar botón "Entrar"
        val s = _login.value                                // Tomamos el estado actual
        val can = s.emailError == null &&                   // Email válido
                s.email.isNotBlank() &&                   // Email no vacío
                s.pass.isNotBlank()                       // Password no vacía
        _login.update { it.copy(canSubmit = can) }          // Actualizamos el flag
    }

    fun submitLogin() {                                     // Acción de login (simulación async)
        val s = _login.value                                // Snapshot del estado
        if (!s.canSubmit || s.isSubmitting) return          // Si no se puede o ya está cargando, salimos
        viewModelScope.launch {                             // Lanzamos corrutina
            _login.update { it.copy(isSubmitting = true, errorMsg = null, success = false) } // Seteamos loading
            delay(500)                                      // Simulamos tiempo de verificación

            // Validar credenciales de admin primero
            val admin = adminRepository.validateAdmin(s.email, s.pass)
            val isAdmin = admin != null
            
            // Si no es admin, validar usuario normal
            val user = if (!isAdmin) userRepository.validateUser(s.email, s.pass) else null
            val ok = isAdmin || user != null

            _login.update {                                 // Actualizamos con el resultado
                it.copy(
                    isSubmitting = false,                   // Fin carga
                    success = ok,                           // true si credenciales correctas
                    errorMsg = if (!ok) "Credenciales inválidas" else null // Mensaje si falla
                )
            }
        }
    }

    fun clearLoginResult() {                                // Limpia banderas tras navegar
        _login.update { it.copy(success = false, errorMsg = null) }
    }

    // ----------------- REGISTRO: handlers y envío -----------------

    fun onNameChange(value: String) {                       // Handler del nombre
        val filtered = value.filter { it.isLetter() || it.isWhitespace() } // Filtramos números/símbolos (solo letras/espacios)
        _register.update {                                  // Guardamos + validamos
            it.copy(name = filtered, nameError = validateLettersOnly(filtered))
        }
        recomputeRegisterCanSubmit()                        // Recalculamos habilitado
    }

    fun onRegisterEmailChange(value: String) {              // Handler del email
        _register.update { it.copy(email = value, emailError = validateEmail(value)) } // Guardamos + validamos
        recomputeRegisterCanSubmit()
    }

    fun onPhoneChange(value: String) {                      // Handler del teléfono
        val digitsOnly = value.filter { it.isDigit() }      // Dejamos solo dígitos
        _register.update {                                  // Guardamos + validamos
            it.copy(phone = digitsOnly, phoneError = validatePhoneDigitsOnly(digitsOnly))
        }
        recomputeRegisterCanSubmit()
    }

    fun onRegisterPassChange(value: String) {               // Handler de la contraseña
        _register.update { it.copy(pass = value, passError = validateStrongPassword(value)) } // Validamos seguridad
        // Revalidamos confirmación con la nueva contraseña
        _register.update { it.copy(confirmError = validateConfirm(it.pass, it.confirm)) }
        recomputeRegisterCanSubmit()
    }

    fun onConfirmChange(value: String) {                    // Handler de confirmación
        _register.update { it.copy(confirm = value, confirmError = validateConfirm(it.pass, value)) } // Guardamos + validamos
        recomputeRegisterCanSubmit()
    }

    private fun recomputeRegisterCanSubmit() {              // Habilitar "Registrar" si todo OK
        val s = _register.value                              // Tomamos el estado actual
        val noErrors = listOf(s.nameError, s.emailError, s.phoneError, s.passError, s.confirmError).all { it == null } // Sin errores
        val filled = s.name.isNotBlank() && s.email.isNotBlank() && s.phone.isNotBlank() && s.pass.isNotBlank() && s.confirm.isNotBlank() // Todo lleno
        _register.update { it.copy(canSubmit = noErrors && filled) } // Actualizamos flag
    }

    fun submitRegister() {                                  // Acción de registro (simulación async)
        val s = _register.value                              // Snapshot del estado
        if (!s.canSubmit || s.isSubmitting) return          // Evitamos reentradas
        viewModelScope.launch {                             // Corrutina
            _register.update { it.copy(isSubmitting = true, errorMsg = null, success = false) } // Loading
            delay(700)                                      // Simulamos IO

            // Intentar registrar usuario usando el repositorio
            val result = userRepository.registerUser(
                email = s.email.trim(),
                name = s.name.trim(),
                password = s.pass,
                phone = s.phone.trim()
            )

            if (result.isSuccess) {
                _register.update {                               // Éxito
                    it.copy(isSubmitting = false, success = true, errorMsg = null)
                }
            } else {
                _register.update {                               // Error
                    it.copy(
                        isSubmitting = false, 
                        success = false, 
                        errorMsg = result.exceptionOrNull()?.message ?: "Error al registrar usuario"
                    )
                }
            }
        }
    }

    fun clearRegisterResult() {                             // Limpia banderas tras navegar
        _register.update { it.copy(success = false, errorMsg = null) }
    }
}