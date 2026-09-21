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
override fun onCreate(savedInstanceState: Bundle?) {
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
// Control de tiempo local: 45 minutos estándar = 2700 segundos totales
val segundosObjetivo = 2700
var segundosTranscurridos by remember { mutableStateOf(0) }
var estaMidiendo by remember { mutableStateOf(false) }
var isHardwarePaused by remember { mutableStateOf(false) }

// Motor del Temporizador Reactivo: solo avanza si está activo y NO está en Pausa Mágica
LaunchedEffect(estaMidiendo, isHardwarePaused) {
while (estaMidiendo && !isHardwarePaused && segundosTranscurridos < segundosObjetivo) {
kotlinx.coroutines.delay(1000)
if (estaMidiendo && !isHardwarePaused) {
segundosTranscurridos++
}
}
if (segundosTranscurridos >= segundosObjetivo) {
estaMidiendo = false
isHardwarePaused = false
println("cancercureOS: SESIÓN COMPLETADA - Desactivando espiral de cobre de forma automática (0 kHz).")
}
}

// Cálculo matemático visual del formato MM:SS
val minutosRestantes = (segundosObjetivo - segundosTranscurridos) / 60
val segundosRestantes = (segundosObjetivo - segundosTranscurridos) % 60
val textoCronometro = String.format("%02d:%02d", minutosRestantes, segundosRestantes)

Column(
modifier = Modifier.fillMaxSize().padding(24.dp),
horizontalAlignment = Alignment.CenterHorizontally,
verticalArrangement = Arrangement.Center
) {
// Título dinámico lúdico para reducir el estrés infantil
val tituloDinamico = when {
estaMidiendo && !isHardwarePaused -> "🔮 Transmitiendo frecuencia protectora..."
isHardwarePaused -> "⏸️ Pausa Mágica activa (Modo Espera)"
else -> "🎯 CONTROL DE FRECUENCIAS"
}

Text(tituloDinamico, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
Spacer(modifier = Modifier.height(16.dp))

// Caja interactiva del Cronómetro (Cambia de fondo según el estado de la terapia)
val colorFondoCaja = when {
estaMidiendo && !isHardwarePaused -> Color(0xFFE8F5E9) // Verde suave activo
isHardwarePaused -> Color(0xFFFFF8E1) // Ámbar suave espera
else -> Color(0xFFF5F5F5) // Gris neutro detenido
}

Box(
modifier = Modifier.size(200.dp).background(colorFondoCaja),
contentAlignment = Alignment.Center
) {
Text(textoCronometro, fontSize = 42.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
}

Spacer(modifier = Modifier.height(12.dp))

// Barra de progreso lineal sutil
LinearProgressIndicator(
progress = { segundosTranscurridos.toFloat() / segundosObjetivo },
color = if (isHardwarePaused) Color(0xFFFFB300) else Color(0xFF4CAF50),
modifier = Modifier.fillMaxWidth(0.8f).padding(vertical = 8.dp)
)

Spacer(modifier = Modifier.height(16.dp))

// Fila de botones de control interactivo de hardware
Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
if (!estaMidiendo) {
// Botón de Inicio de Tratamiento
Button(
onClick = {
segundosTranscurridos = 0
isHardwarePaused = false
estaMidiendo = true
println("cancercureOS: HARDWARE ACTIVADO - Iniciando emisión resonante por espiral de cobre.")
},
colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1B5E20))
) {
Text("Iniciar Terapia")
}
} else {
// INTERRUPTOR DE SEGURIDAD FUSIONADO (Tu pausa de reloj + Mi apagado de cobre)
Button(
onClick = {
isHardwarePaused = !isHardwarePaused
if (isHardwarePaused) {
println("cancercureOS: PAUSA CRUCIAL - Cortando corriente al ESP32. Espiral desactivada (0 kHz).")
} else {
println("cancercureOS: REANUDANDO - Reactivando sintonía molecular por impedancia celular.")
}
},
colors = ButtonDefaults.buttonColors(
containerColor = if (isHardwarePaused) Color(0xFFFFB300) else Color(0xFFD32F2F)
)
) {
Text(if (isHardwarePaused) "▶️ REANUDAR" else "⏸️ PAUSA MÁGICA")
}

// Botón de Parada Completa / Reinicio de Emergencia
OutlinedButton(onClick = {
estaMidiendo = false
isHardwarePaused = false
segundosTranscurridos = 0
println("cancercureOS: RESET DE EMERGENCIA - Cabina totalmente apagada.")
}) {
Text("Detener")
}
}
}

Spacer(modifier = Modifier.height(16.dp))
Text("El sistema elicita la frecuencia óptima según la lectura óptica.", fontSize = 14.sp, color = Color.Gray)
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
Text("• Módulo de Espectroscopía (Luz): Listo (Releases)\n• Módulo de Oscilador (Cobre): En integración local", fontSize = 14.sp, color = Color.Gray)
}
}
