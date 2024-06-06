package com.maestrovs.familyrootview.charts

import android.util.Log
import androidx.compose.ui.geometry.Offset
import kotlin.math.pow
import kotlin.math.sqrt




data class TouchSegment(val level: Int, val segment: Int)



fun calculateTouchedSegment(offset: Offset, miniR: Float, size: Float): TouchSegment {

    val center = size /2
    val rX = center - offset.x
    val rY = center - offset.y
    val radius = sqrt(rX*rX + rY* rY)
    val level: Int = (radius / miniR).toInt()

    val angleInDegrees = if( rY <= 0){
        Math.toDegrees(Math.acos(-1*rX.toDouble() / radius.toDouble()))
           }else{
        Math.toDegrees(Math.acos(1*rX.toDouble() / radius.toDouble())) + 180
    }

    val sectionsCount = 2.0.pow(level)
    val sectionSize = 360/sectionsCount

    val currentSection: Int = (angleInDegrees / sectionSize).toInt()



    Log.d("Mathtr","   level = $level  " +
            "   currentSection = $currentSection")


    return  TouchSegment(level, currentSection)
}


