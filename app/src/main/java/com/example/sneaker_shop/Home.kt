package com.example.sneaker_shop

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sneaker_shop.ui.theme.Raleway

data class Product(
    val id: Int,
    val name: String,
    val price: String,
    val category: String,
    val isBestSeller: Boolean = false,
    val imageRes: Int = R.drawable.sneakers_nike
)

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun HomeScreen(navController: NavController = rememberNavController()) {
    var selectedCategory by remember { mutableStateOf("Все") }
    var searchQuery by remember { mutableStateOf("") }
    var isSearchActive by remember { mutableStateOf(false) }

    val products = listOf(
        Product(1, "Nike Air Max", "P752.00", "All", true),
        Product(2, "Nike Air Max", "P752.00", "All", true),
        Product(3, "Adidas Ultraboost", "P85.00", "Outdoor"),
        Product(4, "Nike Court", "P650.00", "Tennis"),
        Product(5, "New Balance 550", "P800.00", "Outdoor"),
        Product(6, "Asics Gel", "P700.00", "Tennis"),
    )

    val categories = listOf("Все", "Outdoor", "Tennis")

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Главная",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = Raleway
                    )
                },
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            SearchBar(
                searchQuery = searchQuery,
                onSearchQueryChange = { searchQuery = it },
                isSearchActive = isSearchActive,
                onSearchActiveChange = { isSearchActive = it },
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = "Категории",
                fontSize = 18.sp,
                fontFamily = Raleway,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                items(categories) { category ->
                    CategoryChip(
                        category = category,
                        isSelected = selectedCategory == category,
                        onClick = { selectedCategory = category },
                        accentColor = colorResource(R.color.accent)
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Популярное",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = Raleway
                )

                Text(
                    text = "Все",
                    fontSize = 12.sp,
                    color = colorResource(R.color.accent),
                    modifier = Modifier.clickable { /* Показать все */ }
                )
            }

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                items(products.filter { it.isBestSeller }) { product ->
                    ProductCard(product = product)
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Акции",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = Raleway
                )

                Text(
                    text = "Все",
                    fontSize = 12.sp,
                    color = colorResource(R.color.accent),
                    modifier = Modifier.clickable { /* Показать все акции */ }
                )
            }

            SaleBanner(modifier = Modifier.padding(vertical = 16.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    isSearchActive: Boolean,
    onSearchActiveChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    if (isSearchActive) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.LightGray.copy(alpha = 0.1f))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Поиск",
                    tint = colorResource(R.color.hint),
                    modifier = Modifier.padding(end = 12.dp)
                )
                TextField(
                    value = searchQuery,
                    onValueChange = onSearchQueryChange,
                    modifier = Modifier.weight(1f),
                    placeholder = {
                        Text(
                            text = "Поиск товаров...",
                            color = colorResource(R.color.hint)
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            onSearchActiveChange(false)
                        }
                    ),
                    singleLine = true
                )
                Text(
                    text = "Отмена",
                    color = colorResource(R.color.accent),
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .clickable {
                            onSearchQueryChange("")
                            onSearchActiveChange(false)
                        }
                )
            }
        }
    } else {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.LightGray.copy(alpha = 0.1f))
                .clickable { onSearchActiveChange(true) }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Поиск",
                    tint = colorResource(R.color.hint),
                    modifier = Modifier.padding(end = 12.dp)
                )
                Text(
                    text = "Поиск",
                    fontSize = 12.sp,
                    color = colorResource(R.color.hint)
                )
            }
        }
    }
}

@Composable
fun CategoryChip(
    category: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    accentColor: Color = colorResource(R.color.accent)
) {
    Surface(
        modifier = Modifier
            .width(108.dp)
            .height(40.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() },
        color = if (isSelected) accentColor else Color.LightGray.copy(alpha = 0.2f)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = category,
                fontSize = 12.sp,
                fontFamily = Raleway,
                color = if (isSelected) Color.White else Color.Black,
                fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal
            )
        }
    }
}

@Composable
fun ProductCard(product: Product) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .clickable { /* Переход к деталям продукта */ },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .width(117.91.dp)
                    .height(70.dp)
            ) {
                Image(
                    painter = painterResource(id = product.imageRes),
                    contentDescription = product.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }

            if (product.isBestSeller) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, top = 4.dp)
                ) {
                    Text(
                        text = "BEST SELLER",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        color = colorResource(R.color.accent)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = product.name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = colorResource(R.color.hint)
                )

                Text(
                    text = product.price,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

@Composable
fun SaleBanner(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(colorResource(R.color.accent)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Summer Sale",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Text(
                text = "15% OFF",
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )
        }
    }
}