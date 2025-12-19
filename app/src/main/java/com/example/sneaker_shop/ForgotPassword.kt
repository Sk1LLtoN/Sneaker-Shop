package com.example.sneaker_shop

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sneaker_shop.ui.theme.Raleway

@Preview(showBackground = true)
@Composable
fun ForgotPassword(navController: NavController = rememberNavController()) {
    var email by remember { mutableStateOf("") }
    var isEmailValid by remember { mutableStateOf(true) }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
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
            text = "Забыл Пароль",
            fontSize = 32.sp,
            modifier = Modifier.padding(top = 78.dp),
            fontFamily = Raleway,
            fontWeight = FontWeight.Normal
        )

        Text(
            text = "Введите Свою Учетную Запись\n Для Сброса",
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp),
            fontFamily = Raleway,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            color = colorResource(id = R.color.sub_text_dark)
        )

        Column(
            modifier = Modifier
                .padding(top = 40.dp)
        ) {
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    isEmailValid = true
                },
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
                placeholder = {
                    Text(
                        text = "xyz@gmail.com",
                        fontFamily = Raleway,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Normal,
                        color = colorResource(id = R.color.sub_text_dark)
                    )
                }
            )
        }

        Button(
            onClick = {
                if (email.isEmpty()) {
                    errorMessage = "Поле email не может быть пустым"
                    showErrorDialog = true
                } else if (isValidEmail(email)) {
                    println("Отправка письма на: $email")
                    showSuccessDialog = true
                } else {
                    isEmailValid = false
                    errorMessage = getEmailErrorMessage(email)
                    showErrorDialog = true
                }
            },
            modifier = Modifier
                .padding(top = 40.dp)
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
                text = "Отправить",
                fontSize = 14.sp,
                fontFamily = Raleway,
                fontWeight = FontWeight.Normal
            )
        }
    }

    if (showSuccessDialog) {
        Dialog(
            onDismissRequest = {
                showSuccessDialog = false
                email = ""
            }
        ) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier
                    .width(330.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(24.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.email_otp),
                        contentDescription = "Email icon",
                        modifier = Modifier.size(60.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Проверьте Ваш Email",
                        fontFamily = Raleway,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Мы Отправили Код Восстановления Пароля На Вашу Электронную Почту.",
                        fontFamily = Raleway,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        color = colorResource(id = R.color.sub_text_dark),
                        textAlign = TextAlign.Center,
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            showSuccessDialog = false
                            navController.navigate("verification")
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.accent),
                            contentColor = colorResource(id = R.color.background)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Text(
                            text = "OK",
                            fontFamily = Raleway,
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
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
                    fontSize = 18.sp,
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