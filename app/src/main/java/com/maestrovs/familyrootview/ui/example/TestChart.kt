package com.maestrovs.familyrootview.ui.example

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.maestrovs.familyrootview.charts.FamilyRootCanvas
import com.maestrovs.familyrootview.charts.buildBinaryTree
import kotlin.math.roundToInt

@Preview
@Composable
fun ScalableTestCanvas() {

    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val sizeDp = configuration.screenWidthDp.dp


    var scale by remember { mutableStateOf(1f) }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }



    Box(
        modifier = Modifier
            .fillMaxSize()
            .scale(scale)
            .background(Color.Black)
            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
            .pointerInput(Unit) {
                detectTransformGestures { centroid, pan, zoom, rotation ->
                    val (newX, newY) = centroid
                    scale *= zoom
                    offsetX += pan.x
                    offsetY += pan.y
                }
            },

        contentAlignment = Alignment.Center,

        ) {
        val peopleMap = listOfPersons.associateBy { it.id?:"null" }
        val personId = "0"

        val rootPerson = peopleMap[personId]
        rootPerson?.let {
            val binaryTree = buildBinaryTree(it, peopleMap)


            FamilyRootCanvas(sizeDp, binaryTree) { person ->
                Log.d("Mathtr","ClickedPerson = $person")
            }
        }
    }
}



















