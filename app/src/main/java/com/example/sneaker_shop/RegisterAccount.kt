package com.example.sneaker_shop

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true)
@Composable
//Функция для регистрации
fun RegistrationScreen() {

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isAgreed by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 44.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)

    ) {
        Text(
            text = "Регистрация",
            fontSize = 32.sp,
            modifier = Modifier.padding(top = 78.dp),
            style = MaterialTheme.typography.headlineLarge
        )

        Text(
            text = "Заполните Свои данные",
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "Ваше имя",
            modifier = Modifier.padding(end = 278.dp),
            fontSize = 16.sp
        )
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = "Email",
            modifier = Modifier.padding(end = 310.dp),
            fontSize = 16.sp
        )
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = "Пароль",
            modifier = Modifier.padding(end = 290.dp),
            fontSize = 16.sp
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isAgreed,
                onCheckedChange = { isAgreed = it }
            )
            Text(
                text = "Даю согласие на обработку персональных данных",
                modifier = Modifier.weight(1f),
                fontSize = 16.sp
            )
        }

        Button(
            onClick = {
                println("Имя: $name")
                println("Email: $email")
                println("Пароль: $password")
                println("Согласие: $isAgreed")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(top = 16.dp),
            enabled = name.isNotBlank() &&
                    email.isNotBlank() &&
                    password.isNotBlank() &&
                    isAgreed
        ) {
            Text(
                text = "Зарегистрироваться",
                fontSize = 14.sp,
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Есть аккаунт? ",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            TextButton(
                onClick = {
                    println("Переход к входу")
                },
            ) {
                Text(
                    text = "Войти",
                    fontSize = 16.sp
                )
            }
        }
    }
}