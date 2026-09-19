package com.cancercureos.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
override fun Bundle?) {
super.onCreate(savedInstanceState)
setContent {
MaterialTheme {
CarcasaSistemaUniversal()
}
}
}
}

@Composable
fun CarcasaSistemaUniversal() {
var pestañaActiva by remember { mutableStateOf(0) }

Scaffold(
bottomBar = {
NavigationBar(containerColor = Color(0xFFF0F2F5)) {
NavigationBarItem(
selected = pestañaActiva == 0,
onClick = { pestañaActiva = 0 },
icon = { Text("🔮", fontSize = 24.sp) },
label = { Text("Inicio / Tutorial") }
)
NavigationBarItem(
selected = pestañaActiva == 1,
onClick = { pestañaActiva = 1 },
icon = { Text("🎯", fontSize = 24.sp) },
label = { Text("Frecuencias") }
)
NavigationBarItem(
selected = pestañaActiva == 2,
onClick = { pestañaActiva = 2 },
icon = { Text("📚", fontSize = 24.sp) },
label = { Text("Código OS") }
)
}
}
) { paddingValores ->
Box(
modifier = Modifier
.fillMaxSize()
.padding(paddingValores)
.background(Color.White)
) {
when (pestañaActiva) {
0 -> PantallaInicioTutorial()
1 -> PantallaFrecuenciasElicitadas()
2 -> PantallaBibliotecaCodigo()
}
}
}
}

@Composable
fun PantallaInicioTutorial() {
Column(
modifier = Modifier.fillMaxSize().padding(24.dp),
horizontalAlignment = Alignment.CenterHorizontally,
verticalArrangement = Arrangement.Center
) {
Text("✨ POR AQUÍ COMENZAMOS ✨", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A73E8))
Spacer(modifier = Modifier.height(16.dp))
Text("Paso 1: Entrar descalzo a la cabina.\nPaso 2: Coloca tus manitas en las paredes de luz.", fontSize = 16.sp)
Spacer(modifier = Modifier.height(32.dp))
Button(
onClick = { /* Aquí irá el disparo real del espectrómetro después */ },
colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A73E8))
) {
Text("INICIAR ESCANEO / START SCAN", fontSize = 18.sp, modifier = Modifier.padding(8.dp))
}
}
}

@Composable
fun PantallaFrecuenciasElicitadas() {
Column(
modifier = Modifier.fillMaxSize().padding(24.dp),
horizontalAlignment = Alignment.CenterHorizontally,
verticalArrangement = Arrangement.Center
) {
Text("🎯 CONTROL DE FRECUENCIAS", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
Spacer(modifier = Modifier.height(16.dp))
// El color cambiará a verde suave cuando se elicite la frecuencia automáticamente
Box(
modifier = Modifier.size(200.dp).background(Color(0xFFE8F5E9)),
contentAlignment = Alignment.Center
) {
Text("45:00", fontSize = 42.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
}
Spacer(modifier = Modifier.height(16.dp))
Text("El sistema elicita la frecuencia óptima según la lectura óptica.", fontSize = 14.sp)
}
}

@Composable
fun PantallaBibliotecaCodigo() {
Column(
modifier = Modifier.fillMaxSize().padding(24.dp),
horizontalAlignment = Alignment.Start,
verticalArrangement = Arrangement.Top
) {
Text("📚 CÓDIGO ABIERTO GLOBAL", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.Black)
Spacer(modifier = Modifier.height(16.dp))
Text("Este sistema pertenece a la humanidad de forma libre bajo licencia MIT.", fontSize = 16.sp)
Spacer(modifier = Modifier.height(8.dp))
Text("• Módulo de Espectroscopía (Luz): Pendiente\n• Módulo de Oscilador (Cobre): Pendiente", fontSize = 14.sp, color = Color.Gray)
}
}
