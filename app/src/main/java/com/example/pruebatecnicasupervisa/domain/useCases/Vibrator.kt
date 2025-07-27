package com.example.pruebatecnicasupervisa.domain.useCases

import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import javax.inject.Inject

class Vibrator @Inject constructor(
    private val vibrator: Vibrator
){
operator fun invoke (){
    val timeVibrator = 100L
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val vibrationEffect = VibrationEffect.createOneShot(timeVibrator, VibrationEffect.DEFAULT_AMPLITUDE)
        vibrator.vibrate(vibrationEffect)
    } else {
        vibrator.vibrate(timeVibrator)
    }
}
}