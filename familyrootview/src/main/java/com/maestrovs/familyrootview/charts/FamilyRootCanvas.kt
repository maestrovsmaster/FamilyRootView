package com.maestrovs.familyrootview.charts

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.maestrovs.familyrootview.data.AbstractPerson
import com.maestrovs.familyrootview.mock.mockFather
import com.maestrovs.familyrootview.mock.mockMother
import com.maestrovs.familyrootview.mock.mockPerson
import kotlin.math.pow
import kotlin.random.Random


@Preview
@Composable
private fun FamilyRootCanvasPreview(){
    FamilyRootCanvas(
        sizeDp = 300.dp,
    binaryTree = TreeNode(mockPerson, TreeNode(mockMother), TreeNode((mockFather))),
    onClick= {}
    )
}


@Composable
public fun <T : AbstractPerson<T>> FamilyRootCanvas(
    sizeDp: Dp,
    binaryTree: TreeNode<T>,
    onClick: (AbstractPerson<T>?) -> Unit
) {

    val density = LocalDensity.current
    val size = with(density) { sizeDp.toPx() }
    val depth = binaryTree.measureDeep()
    val miniR = (size) / 2 / depth

    val touchSegmentToPersonMap = mutableMapOf<TouchSegment, AbstractPerson<T>>()


    Box(
        contentAlignment = Alignment.TopStart,
        modifier = Modifier
            .width(sizeDp)
            .height(sizeDp)
           // .background(Color.White)
    ) {
        Canvas(
            modifier = Modifier
                //.padding(all = 0.dp)
                .width(sizeDp)
                .height(sizeDp)

                .pointerInput(Unit) {
                    detectTapGestures { offset ->
                        val coords = calculateTouchedSegment(offset, miniR = 100f, size)
                        val person = touchSegmentToPersonMap[coords]
                        onClick(person)
                    }
                },
        ) {
            drawTree(size, miniR, this, binaryTree, onTouch = { touchSegment, t ->
                touchSegmentToPersonMap[touchSegment] = t
            })
        }
    }
}

// val touchSegmentToPersonMap = mutableMapOf<TouchSegment, AbstractPerson<T>>()

fun <T : AbstractPerson<T>> drawTree(
    totalSize: Float,
    miniR: Float,
    drawScope: DrawScope,
    root: TreeNode<T>?,
    dFrom: Float = 0f,
    level: Int = 0,
    onTouch: (TouchSegment, T) -> (Unit)
) {
    if (root == null) return

    val random = Random.Default
    val randomColor = Color(1f,0f,0f)// Color(random.nextFloat(), random.nextFloat(), random.nextFloat(), 1.0f)

    val brush = drawScope.createStripeBrush(
        stripeColor = randomColor,
        stripeWidth = 10.dp,
        stripeToGapRatio = 1.0f
    )

    val centerX = totalSize / 2

    val currentXOffset = centerX - level * miniR - miniR / 2
    val currentYOffset = centerX - level * miniR - miniR / 2
    val currentSize = (level * 2) * miniR + miniR




    val sectionsCount = 2.0.pow(level)
    val currentDiapazon = 360f / (sectionsCount).toFloat();
    val dFromLeft = dFrom
    val dFromRight = dFrom + currentDiapazon / 2


    val sectionMiddle = dFrom + currentDiapazon / 2
    val sectionSize = 360/sectionsCount
    val currentSection: Int = (sectionMiddle / sectionSize).toInt()



   // touchSegmentToPersonMap[TouchSegment(level, currentSection)] = root.person
    onTouch(TouchSegment(level, currentSection),root.person)

    val diap = if(currentDiapazon == 360f){360f}else{currentDiapazon-0.5f}

    drawScope.drawArc(
        brush = brush,
        startAngle = dFrom-0.5f ,
        sweepAngle = diap,
        useCenter = false,
        topLeft = Offset(currentXOffset, currentYOffset),
        style = Stroke(width = miniR - 2, cap = StrokeCap.Butt),
        size = Size(currentSize, currentSize)
    )




    val textCenterX = 2f * centerX / 2f
    val textCenterY = 2f * centerX / 2f

    val textOffsetY = if (currentDiapazon == 360f) {
        textCenterY + 10f
    } else {
        if (dFrom in 0.0..179.0) {
            textCenterY + level * miniR + miniR / 3
        } else {
            textCenterY - level * miniR - miniR / 3
        }
    }

    val rotareAngle = if (currentDiapazon == 360f) {
        0f
    } else {
        if (dFrom < 180f) {
            1 * (dFrom + currentDiapazon / 2 - 90)
        } else {
            1 * (dFrom + currentDiapazon / 2 + 90)
        }
    }

    drawScope.rotate(
        rotareAngle, pivot =
        Offset(textCenterX, textCenterY)
    ) {
        drawContext.canvas.nativeCanvas.apply {
            drawText(
                "${root.person.description()?:""} ",
                textCenterX,
                textOffsetY,
                Paint().apply {
                    textSize = 18f
                    // color = randomColor
                    textAlign = Paint.Align.CENTER
                }
            )
        }
    }
    drawTree(totalSize, miniR, drawScope, root.right, dFromLeft, level + 1,onTouch)
    drawTree(totalSize, miniR, drawScope, root.left, dFromRight, level + 1,onTouch)
}
