package com.cancercureos.app

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.net.URL


data class SignalProfile(
    val id: String,
    val label: String,
    val group: String,
    val spectralPeaks: List<Double>,
    val targetFrequencyKhz: Double
)

object DataPipelineManager {

    
    private const val URL_STREAM_01 = "https://github.com"
    private const val URL_STREAM_02 = "https://github.com"
    
    
    private const val CACHE_FILE_01 = "datapipe_01.dat"
    private const val CACHE_FILE_02 = "datapipe_02.dat"

    suspend fun synchronizeDataStreams(context: Context): Boolean = withContext(Dispatchers.IO) {
        val file01 = File(context.filesDir, CACHE_FILE_01)
        val file02 = File(context.filesDir, CACHE_CACHE_02)
        var isSystemReady = true

        
        try {
            val content01 = URL(URL_STREAM_01).readText()
            file01.writeText(content01) // Actualiza copia local en seco
        } catch (e: Exception) {
            
            isSystemReady = file01.exists()
        }

        
        try {
            val content02 = URL(URL_STREAM_02).readText()
            file02.writeText(content02) // Actualiza copia local en seco
        } catch (e: Exception) {
            
            if (!file02.exists()) isSystemReady = false
        }

        isSystemReady
    }

  
    fun loadLocalProfiles(context: Context): List<SignalProfile> {
        val integratedProfiles = mutableListOf<SignalProfile>()
        val file01 = File(context.filesDir, CACHE_FILE_01)
        val file02 = File(context.filesDir, CACHE_FILE_02)

        
        if (file01.exists()) {
            file01.bufferedReader().useLines { lines ->
                lines.drop(1).forEach { line ->
                    val columns = line.split(",")
                    if (columns.size >= 5) {
                        val id = columns.getOrNull(0) ?: ""
                        val label = columns.getOrNull(1) ?: "Unknown"
                        val matrixGroup = columns.getOrNull(2) ?: "Default"
                        val peaks = columns.getOrNull(3)?.split("|")?.mapNotNull { it.toDoubleOrNull() } ?: emptyList()
                        val freq = columns.getOrNull(4)?.toDoubleOrNull() ?: 700.0 // 700 kHz estándar de Michigan
                        
                        integratedProfiles.add(SignalProfile(id, label, matrixGroup, peaks, freq))
                    }
                }
            }
        }

        
        if (file02.exists()) {
            file02.bufferedReader().useLines { lines ->
                lines.drop(1).forEach { line ->
                    val columns = line.split(",")
                    if (columns.size >= 5) {
                        val id = columns.getOrNull(0) ?: ""
                        val label = columns.getOrNull(1) ?: "Unknown"
                        val matrixGroup = columns.getOrNull(2) ?: "Default"
                        val peaks = columns.getOrNull(4)?.split("|")?.mapNotNull { it.toDoubleOrNull() } ?: emptyList()
                        val freq = columns.getOrNull(5)?.toDoubleOrNull() ?: 440.0 // Frecuencia estimada viral
                        
                        integratedProfiles.add(SignalProfile(id, label, matrixGroup, peaks, freq))
                    }
                }
            }
        }

        return integratedProfiles
    }
}
