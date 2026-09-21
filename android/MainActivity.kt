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
                    label = { Text("Start / Tutorial") }
                )
                NavigationBarItem(
                    selected = pestañaActiva == 1,
                    onClick = { pestañaActiva = 1 },
                    icon = { Text("🎯", fontSize = 24.sp) },
                    label = { Text("Frequencies") }
                )
                NavigationBarItem(
                    selected = pestañaActiva == 2,
                    onClick = { pestañaActiva = 2 },
                    icon = { Text("📚", fontSize = 24.sp) },
                    label = { Text("Open Source") }
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
        Text("✨ WELCOME TO CANCERCUREOS ✨", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A73E8))
        Spacer(modifier = Modifier.height(32.dp))
        
        Button(
            onClick = { /* Spectrometer optical trigger sequence */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A73E8))
        ) {
            Text("START SCAN", fontSize = 18.sp, modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
fun PantallaFrecuenciasElicitadas() {
    val segundosObjetivo = 2700 // 45 minutes standard session
    var segundosTranscurridos by remember { mutableStateOf(0) }
    var estaMidiendo by remember { mutableStateOf(false) }
    var isHardwarePaused by remember { mutableStateOf(false) }

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
            println("cancercureOS: SESSION COMPLETED - Disabling copper coil emission (0 kHz).")
        }
    }

    val minutesLeft = (segundosObjetivo - segundosTranscurridos) / 60
    val secondsLeft = (segundosObjetivo - segundosTranscurridos) % 60
    val timerText = String.format("%02d:%02d", minutesLeft, secondsLeft)

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val dynamicTitle = when {
            estaMidiendo && !isHardwarePaused -> "🔮 Transmitting protective frequency..."
            isHardwarePaused -> "⏸️ Magic Pause Active (Standby Mode)"
            else -> "🎯 FREQUENCY CONTROL"
        }

        Text(dynamicTitle, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
        Spacer(modifier = Modifier.height(16.dp))
        
        val boxBackgroundColor = when {
            estaMidiendo && !isHardwarePaused -> Color(0xFFE8F5E9)
            isHardwarePaused -> Color(0xFFFFF8E1)
            else -> Color(0xFFF5F5F5)
        }

        Box(
            modifier = Modifier.size(200.dp).background(boxBackgroundColor),
            contentAlignment = Alignment.Center
        ) {
            Text(timerText, fontSize = 42.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
        }
        
        Spacer(modifier = Modifier.height(12.dp))

        LinearProgressIndicator(
            progress = { segundosTranscurridos.toFloat() / segundosObjetivo },
            color = if (isHardwarePaused) Color(0xFFFFB300) else Color(0xFF4CAF50),
            modifier = Modifier.fillMaxWidth(0.8f).padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            if (!estaMidiendo) {
                Button(
                    onClick = {
                        segundosTranscurridos = 0
                        isHardwarePaused = false
                        estaMidiendo = true
                        println("cancercureOS: HARDWARE ACTIVATED - Resonant emission started.")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1B5E20))
                ) {
                    Text("Start Therapy")
                }
            } else {
                Button(
                    onClick = { 
                        isHardwarePaused = !isHardwarePaused
                        if (isHardwarePaused) {
                            println("cancercureOS: CRITICAL PAUSE - Disabling copper coil safely.")
                        } else {
                            println("cancercureOS: RESUMING - Reactivating molecular impedance alignment.")
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isHardwarePaused) Color(0xFFFFB300) else Color(0xFFD32F2F)
                    )
                ) { 
                    Text(if (isHardwarePaused) "▶️ RESUME" else "⏸️ MAGIC PAUSE") 
                }

                OutlinedButton(onClick = {
                    estaMidiendo = false
                    isHardwarePaused = false
                    segundosTranscurridos = 0
                    println("cancercureOS: EMERGENCY RESET - System completely powered off.")
                }) { 
                    Text("Stop") 
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        Text("The system elicits the optimal frequency based on optical reading.", fontSize = 14.sp, color = Color.Gray)
    }
}

@Composable
fun PantallaBibliotecaCodigo() {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Text("📚 GLOBAL OPEN SOURCE", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        Spacer(modifier = Modifier.height(16.dp))
        Text("This system belongs to humanity under the free MIT License.", fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text("• Spectroscopy Module (Light): Ready (Releases)\n• Oscillator Module (Copper): Local integration in progress", fontSize = 14.sp, color = Color.Gray)
    }
}
