package com.codlin.cardiai.domain.util

import android.util.Patterns
import com.codlin.cardiai.domain.util.exception.BlankPasswordException
import com.codlin.cardiai.domain.util.exception.InvalidEmailException
import com.codlin.cardiai.domain.util.exception.ShortNameException
import com.codlin.cardiai.domain.util.exception.ShortPasswordException

object ValidationUtil {
    fun validateEmail(email: String) {
        if (email.isBlank()) {
            throw InvalidEmailException()
        }

//        val regex = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$")
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            throw InvalidEmailException()
        }
    }

    fun validatePassword(password: String) {
        if (password.isBlank()) {
            throw BlankPasswordException()
        }
        if (password.length < 6) {
            throw ShortPasswordException()
        }
    }

    fun validateName(name: String) {
        if (name.length < 4) {
            throw ShortNameException()
        }
    }
}
