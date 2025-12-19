package com.example.sneaker_shop

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
fun SignIn(navController: NavController = rememberNavController()) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var showEmailErrorDialog by remember { mutableStateOf(false) }
    var showEmptyFieldsDialog by remember { mutableStateOf(false) }
    var isEmailValid by remember { mutableStateOf(true) }
    var emailErrorMessage by remember { mutableStateOf("") }
    var emptyFieldsErrorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 44.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    )
    {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(
                onClick = { navController.popBackStack() }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Назад"
                )
            }
        }
        Text(
            text = "Привет!",
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

        Column(modifier = Modifier.padding(top = 54.dp)) {
            Text(
                text = "Email",
                modifier = Modifier.padding(bottom = 12.dp),
                fontSize = 16.sp,
                fontFamily = Raleway,
                fontWeight = FontWeight.Medium
            )
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    isEmailValid = true
                },
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth(),
                isError = !isEmailValid && email.isNotEmpty(),
                textStyle = TextStyle(
                    fontFamily = Raleway,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                ),
                placeholder = {
                    Text(
                        text = "xyz@gmail.com",
                        fontFamily = Raleway,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Normal,
                        color = colorResource(id = R.color.sub_text_dark)
                    )
                }
            )

            Text(
                text = "Пароль",
                modifier = Modifier.padding(top = 30.dp, bottom = 12.dp),
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
                placeholder = {
                    Text(
                        text = ". . . . . .",
                        fontFamily = Raleway,
                        //textAlign = TextAlign.Center,
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

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.End
            ) {
                TextButton(
                    onClick = {
                        navController.navigate("forgotpassword")
                    },
                    modifier = Modifier.padding(0.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = "Восстановить",
                        modifier = Modifier.padding(top = 12.dp),
                        fontSize = 12.sp,
                        fontFamily = Raleway,
                        fontWeight = FontWeight.Normal,
                        color = colorResource(id = R.color.sub_text_dark)
                    )
                }
            }

            Button(
                onClick = {
                    if (email.isEmpty() || password.isEmpty()) {
                        emptyFieldsErrorMessage = when {
                            email.isEmpty() && password.isEmpty() ->
                                "Пожалуйста, заполните все поля"
                            email.isEmpty() ->
                                "Поле email не может быть пустым"
                            password.isEmpty() ->
                                "Поле пароль не может быть пустым"
                            else -> "Пожалуйста, заполните все поля"
                        }
                        showEmptyFieldsDialog = true
                    }
                    else if (isValidEmail(email)) {
                        println("Email: $email")
                        println("Пароль: $password")
                    } else {
                        isEmailValid = false
                        emailErrorMessage = getEmailErrorMessage(email)
                        showEmailErrorDialog = true
                    }
                },
                modifier = Modifier
                    .padding(top = 24.dp)
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(13.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.accent),
                    contentColor = colorResource(id = R.color.background),
                    disabledContainerColor = colorResource(id = R.color.disable),
                    disabledContentColor = colorResource(id = R.color.background)
                )
            ) {
                Text(
                    text = "Войти",
                    fontSize = 14.sp,
                    fontFamily = Raleway,
                    fontWeight = FontWeight.Normal
                )
            }
        }

        Row(
            modifier = Modifier.padding(top = 113.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Вы впервые?",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontFamily = Raleway,
                fontWeight = FontWeight.Normal
            )
            TextButton(
                onClick = {
                    navController.navigate("register")
                },
                modifier = Modifier.padding(start = 4.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = "Создать",
                    fontSize = 16.sp,
                    fontFamily = Raleway,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }

    // Диалоговое окно для отображения ошибки email
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

    if (showEmptyFieldsDialog) {
        AlertDialog(
            onDismissRequest = {
                showEmptyFieldsDialog = false
            },
            title = {
                Text(
                    text = "Заполните поля",
                    fontFamily = Raleway,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                )
            },
            text = {
                Text(
                    text = emptyFieldsErrorMessage,
                    fontFamily = Raleway,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showEmptyFieldsDialog = false
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
}