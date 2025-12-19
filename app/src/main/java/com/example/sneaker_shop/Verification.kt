package com.example.sneaker_shop

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sneaker_shop.ui.theme.Raleway
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun VerificationScreen(navController: NavController = rememberNavController()) {
    var otpCode by remember { mutableStateOf(Array(6) { "" }) }
    var isResendEnabled by remember { mutableStateOf(false) }
    var timeLeft by remember { mutableStateOf(30) }
    val focusRequesters = remember { List(6) { FocusRequester() } }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = timeLeft) {
        if (timeLeft > 0) {
            delay(1000L)
            timeLeft--
            if (timeLeft == 0) {
                isResendEnabled = true
            }
        }
    }
    LaunchedEffect(Unit) {
        focusRequesters[0].requestFocus()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .padding(top = 40.dp, bottom = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 80.dp, bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "ОТР Проверка",
                fontSize = 32.sp,
                fontFamily = Raleway,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Пожалуйста, Проверьте Свою\n Электронную Почту. Чтобы Увидеть Код Подтверждения",
                fontSize = 16.sp,
                fontFamily = Raleway,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center
            )

            /*Text(
                text = "Чтобы Увидеть Код Подтверждения",
                fontSize = 16.sp,
                fontFamily = Raleway,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center
            )*/

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "ОТР Код",
                fontSize = 20.sp,
                fontFamily = Raleway,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {
                for (i in 0 until 6) {
                    OtpTextField(
                        value = otpCode[i],
                        onValueChange = { newValue ->
                            if (newValue.length <= 1 && newValue.all { it.isDigit() }) {
                                otpCode = otpCode.copyOf().apply { this[i] = newValue }

                                if (newValue.isNotEmpty() && i < 5) {
                                    coroutineScope.launch {
                                        delay(50)
                                        focusRequesters[i + 1].requestFocus()
                                    }
                                }

                                if (newValue.isEmpty() && i > 0) {
                                    coroutineScope.launch {
                                        delay(50)
                                        focusRequesters[i - 1].requestFocus()
                                    }
                                }
                            }
                        },
                        index = i,
                        focusRequester = focusRequesters[i]
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = String.format("%02d:%02d", timeLeft / 60, timeLeft % 60),
                fontSize = 18.sp,
                fontFamily = Raleway,
                fontWeight = FontWeight.Medium,
                color = colorResource(id = R.color.accent)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (isResendEnabled) {
                        timeLeft = 30
                        isResendEnabled = false
                        otpCode = Array(6) { "" }
                        coroutineScope.launch {
                            focusRequesters[0].requestFocus()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(13.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isResendEnabled)
                        colorResource(id = R.color.accent)
                    else
                        colorResource(id = R.color.disable),
                    contentColor = colorResource(id = R.color.background)
                ),
                enabled = isResendEnabled
            ) {
                Text(
                    text = "Отправить заново",
                    fontSize = 14.sp,
                    fontFamily = Raleway,
                    fontWeight = FontWeight.Normal
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    val code = otpCode.joinToString("")
                    if (code.length == 6 && code.all { it.isDigit() }) {
                        println("Код подтверждения: $code")
                        navController.navigate("createnewpassword")
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
    }
}

@Composable
fun OtpTextField(
    value: String,
    onValueChange: (String) -> Unit,
    index: Int,
    focusRequester: FocusRequester
) {
    var isFocused by remember { mutableStateOf(false) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(width = 46.dp, height = 99.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(
                color = if (isFocused)
                    colorResource(id = R.color.accent).copy(alpha = 0.1f)
                else
                    colorResource(id = R.color.background)
            )
            .border(
                width = if (isFocused) 2.dp else 1.dp,
                color = if (isFocused)
                    colorResource(id = R.color.accent)
                else
                    colorResource(id = R.color.sub_text_dark).copy(alpha = 0.3f),
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxSize()
                .focusRequester(focusRequester)
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                },
            textStyle = TextStyle(
                fontFamily = Raleway,
                fontWeight = FontWeight.SemiBold,
                fontSize = 32.sp,
                textAlign = TextAlign.Center
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            singleLine = true,
            decorationBox = { innerTextField ->
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = "0",
                            fontSize = 32.sp,
                            fontFamily = Raleway,
                            fontWeight = FontWeight.SemiBold,
                            color = colorResource(id = R.color.sub_text_dark).copy(alpha = 0.3f)
                        )
                    }
                    innerTextField()
                }
            }
        )
    }
}
