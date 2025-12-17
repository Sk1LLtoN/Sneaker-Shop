package com.example.sneaker_shop

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sneaker_shop.ui.theme.Raleway

@Preview(showBackground = true)
@Composable
//Функция экрана регистрации
fun RegistrationScreen(navController: NavController = rememberNavController()) {

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isAgreed by remember { mutableStateOf(false) }
    var showEmailErrorDialog by remember { mutableStateOf(false) }
    var isEmailValid by remember { mutableStateOf(true) }
    var emailErrorMessage by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 44.dp),
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        Text(
            text = "Регистрация",
            fontSize = 32.sp,
            modifier = Modifier.padding(top = 78.dp),
            fontFamily = Raleway,
            fontWeight = FontWeight.Normal
        )

        Text(
            text = "Заполните Свои данные",
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp),
            fontFamily = Raleway,
            fontWeight = FontWeight.Normal,
            color = colorResource(id = R.color.sub_text_dark)

        )
        Column(modifier = Modifier
            .padding(top = 54.dp)) {
            Text(
                text = "Ваше имя",
                modifier = Modifier.padding(bottom = 12.dp),
                fontSize = 16.sp,
                fontFamily = Raleway,
                fontWeight = FontWeight.Medium
            )
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                shape = RoundedCornerShape(14.dp),
                singleLine = true,
                textStyle = TextStyle(
                    fontFamily = Raleway,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                        ),
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "Email",
                modifier = Modifier.padding(bottom = 12.dp, top = 12.dp),
                fontSize = 16.sp,
                fontFamily = Raleway,
                fontWeight = FontWeight.Medium
            )
            OutlinedTextField(
                value = email,
                onValueChange = { email = it; isEmailValid = true },
                shape = RoundedCornerShape(14.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                textStyle = TextStyle(
                    fontFamily = Raleway,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                ),
                modifier = Modifier.fillMaxWidth(),
                isError = !isEmailValid && email.isNotEmpty(),
            )

            Text(
                text = "Пароль",
                modifier = Modifier.padding(bottom = 12.dp, top = 12.dp),
                fontSize = 16.sp,
                fontFamily = Raleway,
                fontWeight = FontWeight.Medium
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomSVGCheckbox(
                    checked = isAgreed,
                    onCheckedChange = { isAgreed = it }
                )
                Text(
                    text = "Даю согласие на обработку персональных данных",
                    modifier = Modifier.weight(1f),
                    fontSize = 16.sp,
                    fontFamily = Raleway,
                    fontWeight = FontWeight.Normal,
                    color = colorResource(id = R.color.sub_text_dark),
                    textDecoration = TextDecoration.Underline
                )
            }
        }

        Button(
            onClick = {
                if (isValidEmail(email)) {
                    println("Имя: $name")
                    println("Email: $email")
                    println("Пароль: $password")
                    println("Согласие: $isAgreed")
                    showSuccessDialog = true
                } else {
                    isEmailValid = false
                    emailErrorMessage = getEmailErrorMessage(email)
                    showEmailErrorDialog = true
                }
            },
            modifier = Modifier.padding(top = 20.dp)
                .width(335.dp)
                .height(50.dp),
            enabled = isAgreed,
            shape = RoundedCornerShape(13.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.accent),
                contentColor = colorResource(id = R.color.background),
                disabledContainerColor = colorResource(id = R.color.disable),
                disabledContentColor = colorResource(id = R.color.background)
            )
        ) {
            Text(
                text = "Зарегистрироваться",
                fontSize = 14.sp,
                fontFamily = Raleway,
                fontWeight = FontWeight.Normal
            )
        }

        Row(
            modifier = Modifier.padding(top = 113.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically

        ) {
            Text(
                text = "Есть аккаунт?",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontFamily = Raleway,
                fontWeight = FontWeight.Normal
            )
            TextButton(
                onClick = {
                    navController.navigate("signin")
                },
                modifier = Modifier.padding(0.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = "Войти",
                    fontSize = 16.sp,
                    fontFamily = Raleway,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
    if (showEmailErrorDialog) {
        AlertDialog(
            onDismissRequest = {
                showEmailErrorDialog = false
            },
            title = {
                Text(
                    text = "Ошибка email",
                    fontFamily = Raleway,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                )
            },
            text = {
                Text(
                    text = emailErrorMessage,
                    fontFamily = Raleway,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showEmailErrorDialog = false
                    }
                ) {
                    Text(
                        text = "OK",
                        fontFamily = Raleway,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        )
    }
    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = {
                showSuccessDialog = false
                navController.navigate("signin")
            },
            title = {
                Text(
                    text = "Регистрация успешна!",
                    fontFamily = Raleway,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )
            },
            text = {
                Text(
                    text = "Вы успешно зарегистрировались. Теперь вы можете войти в свой аккаунт.",
                    fontFamily = Raleway,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
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
                    )
                ) {
                    Text(
                        text = "Войти",
                        fontFamily = Raleway,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        )
    }
}

// Функция для валидации email
fun isValidEmail(email: String): Boolean {
    val pattern = "^[a-z0-9]+@[a-z0-9]+\\.[a-z]{3,}$".toRegex()
    return pattern.matches(email)
}

// Функция для получения сообщения об ошибке
fun getEmailErrorMessage(email: String): String {
    return when {
        email.isEmpty() -> "Поле email не может быть пустым"
        !email.contains("@") -> "Email должен содержать символ @"
        !email.contains(".") -> "Email должен содержать точку"
        email.contains(Regex("[A-Z]")) -> "Email должен содержать только строчные буквы"
        !email.matches(Regex("^[a-z0-9@.]+$")) -> "Email должен содержать только строчные буквы, цифры, @ и точку"
        email.split("@").size != 2 -> "Некорректный формат email"
        email.split("@")[1].split(".").size < 2 -> "Отсутствует доменное имя"
        email.split("@")[1].split(".")[1].length < 3 -> "Старший домен должен содержать минимум 3 символа"
        else -> "Некорректный формат email. Пример: name@domain.com"
    }
}

//Функция для отображение клика Checkbox
@Composable
fun CustomSVGCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    enabled: Boolean = true,
    checkedColorFilter: ColorFilter? = null,
    uncheckedColorFilter: ColorFilter? = null
) {
    Row(
        modifier = modifier
            .clickable(enabled = enabled) {
                if (enabled) {
                    onCheckedChange(!checked)
                }
            }
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(
                id = if (checked) {
                    R.drawable.consent_data_transfer_enabled
                } else {
                    R.drawable.consent_data_transfer_not_enabled
                }
            ),
            contentDescription = if (checked) "Отмечено" else "Не отмечено",
            modifier = Modifier.size(24.dp),
            colorFilter = if (checked) checkedColorFilter else uncheckedColorFilter
        )

        label?.let {
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = it,
                style = MaterialTheme.typography.bodyMedium,
                color = if (!enabled) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}