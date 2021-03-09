/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androiddevchallenge.model.MAX_INPUT_VALUE
import com.example.androiddevchallenge.model.MIN_INPUT_VALUE
import com.example.androiddevchallenge.model.TimerViewModel
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.ui.theme.black
import com.example.androiddevchallenge.ui.theme.purple200
import com.example.androiddevchallenge.ui.theme.purple100
import com.example.androiddevchallenge.ui.theme.purple500
import com.example.androiddevchallenge.utils.TimeFormatUtils.formatTimeMinute
import com.example.androiddevchallenge.utils.TimeFormatUtils.formatTimeSecond
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            MyTheme {
                MyApp()
            }
        }
    }
}

// Start building your app here!
@Composable
fun MyApp() {
    val viewModel: TimerViewModel = viewModel()
    Surface(color = MaterialTheme.colors.background) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Timer(viewModel)
            Row(
                modifier = Modifier
                    .padding(15.dp)
            ) {
                StartButton(viewModel = viewModel)
                CancelButton(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun Timer(viewModel: TimerViewModel) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(200.dp)
    ) {
        ProgressCircle(viewModel)
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            MinuteValue(viewModel)
            Text(
                text = ":",
                style = TextStyle(fontWeight = FontWeight(500), color = black),
                fontSize = 20.sp,
                modifier = Modifier.padding(2.dp)
            )
            SecondValue(viewModel)
        }
    }
}

@Composable
fun MinuteValue(
    viewModel: TimerViewModel,
) {
    var value by remember { mutableStateOf(0) }
    Box(
        modifier = Modifier
            .size(50.dp)
            .clip(RoundedCornerShape(10.dp))
            .scrollable(
                orientation = Orientation.Vertical,
                state = rememberScrollableState { delta ->
                    if (delta < 0) {
                        value++
                    } else {
                        value--
                    }
                    if (value > MAX_INPUT_VALUE || value < MIN_INPUT_VALUE) {
                        value = 0
                    }
                    viewModel.updateMinute(value)
                    value.toFloat()
                })
            .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = formatTimeMinute(viewModel.timeLeft),
            style = TextStyle(fontWeight = FontWeight(500), color = black),
            fontSize = 20.sp
        )
    }
}


@Composable
fun SecondValue(
    viewModel: TimerViewModel,
) {
    var value by remember { mutableStateOf(0) }
    Box(
        modifier = Modifier
            .size(50.dp)
            .clip(RoundedCornerShape(10.dp))
            .scrollable(
                orientation = Orientation.Vertical,
                state = rememberScrollableState { delta ->
                    if (delta < 0) {
                        value++
                    } else {
                        value--
                    }
                    if (value > MAX_INPUT_VALUE || value < MIN_INPUT_VALUE) {
                        value = 0
                    }
                    viewModel.updateSecond(value)
                    value.toFloat()
                })
            .background(Color.LightGray),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = formatTimeSecond(viewModel.timeLeft),
            style = TextStyle(fontWeight = FontWeight(500), color = black),
            fontSize = 20.sp
        )
    }
}

@Composable
fun ProgressCircle(viewModel: TimerViewModel) {
    val size = 200.dp
    Box(contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.size(size), onDraw = {
            val sweepAngle = viewModel.status.sweepAngle()
            val strokeWidth = 8.dp.toPx()
            drawCircle(
                color = Color.LightGray,
                style = Stroke(
                    width = strokeWidth,
                    pathEffect = PathEffect.cornerPathEffect(10f)
                )
            )

            drawArc(
                brush = Brush.sweepGradient(
                    0f to purple500,
                    0.35f to purple200,
                    0.75f to purple100,
                    1f to purple500
                ),
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = strokeWidth)
            )
        })
    }
}

@Composable
fun StartButton(viewModel: TimerViewModel) {
    Button(
        modifier = Modifier
            .width(120.dp)
            .padding(15.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(color = purple200),
        enabled = viewModel.totalTime > 0,
        onClick = viewModel.status::startButtonClick
    ) {
        Text(text = viewModel.status.getDisplayName())
    }
}

@Composable
fun CancelButton(viewModel: TimerViewModel) {
    Button(
        modifier = Modifier
            .width(120.dp)
            .padding(15.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(color = purple200),
        enabled = viewModel.counting,
        onClick = viewModel.status::cancelButtonClick
    ) {
        Text(text = "Cancel")
    }
}


@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        MyApp()
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        MyApp()
    }
}
