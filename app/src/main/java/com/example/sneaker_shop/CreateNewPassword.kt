package com.example.sneaker_shop

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sneaker_shop.ui.theme.Raleway

@Preview(showBackground = true)
@Composable
fun CreateNewPassword(navController: NavController = rememberNavController()) {
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var isPasswordValid by remember { mutableStateOf(true) }
    var isConfirmPasswordValid by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Задать Новый Пароль",
            fontSize = 32.sp,
            modifier = Modifier.padding(top = 78.dp),
            fontFamily = Raleway,
            fontWeight = FontWeight.Normal
        )

        Text(
            text = "Установите Новый Пароль Для Входа В\n Вашу Учетную Запись",
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp),
            fontFamily = Raleway,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            color = colorResource(id = R.color.sub_text_dark)
        )

        Column(modifier = Modifier.padding(top = 54.dp)) {
            Text(
                text = "Пароль",
                modifier = Modifier.padding(bottom = 12.dp, top = 12.dp),
                fontSize = 16.sp,
                fontFamily = Raleway,
                fontWeight = FontWeight.Medium
            )
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    isPasswordValid = true
                    if (confirmPassword.isNotEmpty()) {
                        isConfirmPasswordValid = it == confirmPassword
                    }
                },
                shape = RoundedCornerShape(14.dp),
                singleLine = true,
                visualTransformation = if (passwordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                textStyle = TextStyle(
                    fontFamily = Raleway,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                ),
                modifier = Modifier.fillMaxWidth(),
                isError = !isPasswordValid,
                placeholder = {
                    Text(
                        text = ". . . . . .",
                        fontFamily = Raleway,
                        fontWeight = FontWeight.Normal,
                        color = colorResource(id = R.color.sub_text_dark)
                    )
                },
                trailingIcon = {
                    IconButton(
                        onClick = { passwordVisible = !passwordVisible }
                    ) {
                        Icon(
                            painter = painterResource(
                                id = if (passwordVisible) {
                                    R.drawable.eye_open
                                } else {
                                    R.drawable.eye_close
                                }
                            ),
                            contentDescription = if (passwordVisible) "Скрыть пароль" else "Показать пароль",
                            tint = colorResource(id = R.color.sub_text_dark)
                        )
                    }
                }
            )

            if (!isPasswordValid) {
                Text(
                    text = "Пароль должен быть не менее 6 символов",
                    color = colorResource(id = android.R.color.holo_red_dark),
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 4.dp, top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Подтверждение пароля",
                modifier = Modifier.padding(bottom = 12.dp, top = 12.dp),
                fontSize = 16.sp,
                fontFamily = Raleway,
                fontWeight = FontWeight.Medium
            )
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it
                    isConfirmPasswordValid = true
                    if (password.isNotEmpty()) {
                        isConfirmPasswordValid = it == password
                    }
                },
                shape = RoundedCornerShape(14.dp),
                singleLine = true,
                visualTransformation = if (confirmPasswordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                textStyle = TextStyle(
                    fontFamily = Raleway,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                ),
                modifier = Modifier.fillMaxWidth(),
                isError = !isConfirmPasswordValid,
                placeholder = {
                    Text(
                        text = ". . . . . .",
                        fontFamily = Raleway,
                        fontWeight = FontWeight.Normal,
                        color = colorResource(id = R.color.sub_text_dark)
                    )
                },
                trailingIcon = {
                    IconButton(
                        onClick = { confirmPasswordVisible = !confirmPasswordVisible }
                    ) {
                        Icon(
                            painter = painterResource(
                                id = if (confirmPasswordVisible) {
                                    R.drawable.eye_open
                                } else {
                                    R.drawable.eye_close
                                }
                            ),
                            contentDescription = if (confirmPasswordVisible) "Скрыть пароль" else "Показать пароль",
                            tint = colorResource(id = R.color.sub_text_dark)
                        )
                    }
                }
            )

            if (!isConfirmPasswordValid) {
                Text(
                    text = "Пароли не совпадают",
                    color = colorResource(id = android.R.color.holo_red_dark),
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 4.dp, top = 4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                val passwordValidation = validatePassword(password, confirmPassword)

                when {
                    password.isEmpty() || confirmPassword.isEmpty() -> {
                        errorMessage = "Заполните все поля"
                        showErrorDialog = true
                    }
                    !passwordValidation.isValid -> {
                        errorMessage = passwordValidation.errorMessage
                        showErrorDialog = true
                        isPasswordValid = false
                        isConfirmPasswordValid = false
                    }
                    else -> {
                        println("Новый пароль установлен: $password")
                        showSuccessDialog = true
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(13.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.accent),
                contentColor = colorResource(id = R.color.background)
            )
        ) {
            Text(
                text = "Подтвердить",
                fontSize = 14.sp,
                fontFamily = Raleway,
                fontWeight = FontWeight.Normal
            )
        }
    }

    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = {
                showSuccessDialog = false
                navController.navigate("signin")
            },
            title = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Пароль Установлен",
                        fontFamily = Raleway,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center
                    )
                }
            },
            text = {
                Text(
                    text = "Ваш пароль был успешно изменен.",
                    fontFamily = Raleway,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = colorResource(id = R.color.sub_text_dark),
                    textAlign = TextAlign.Center
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showSuccessDialog = false
                        navController.navigate("signin")
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.accent)
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Войти",
                        fontFamily = Raleway,
                        fontWeight = FontWeight.Medium
                    )
                }
            },
            containerColor = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(16.dp)
        )
    }

    if (showErrorDialog) {
        AlertDialog(
            onDismissRequest = {
                showErrorDialog = false
            },
            title = {
                Text(
                    text = "Ошибка",
                    fontFamily = Raleway,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                )
            },
            text = {
                Text(
                    text = errorMessage,
                    fontFamily = Raleway,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = colorResource(id = R.color.sub_text_dark)
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showErrorDialog = false
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.accent)
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "OK",
                        fontFamily = Raleway,
                        fontWeight = FontWeight.Medium
                    )
                }
            },
            containerColor = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(16.dp)
        )
    }
}

data class PasswordValidation(
    val isValid: Boolean,
    val errorMessage: String = ""
)

private fun validatePassword(password: String, confirmPassword: String): PasswordValidation {
    return when {
        password.length < 6 -> PasswordValidation(
            isValid = false,
            errorMessage = "Пароль должен содержать не менее 6 символов"
        )
        password != confirmPassword -> PasswordValidation(
            isValid = false,
            errorMessage = "Пароли не совпадают"
        )
        else -> PasswordValidation(isValid = true)
    }
}