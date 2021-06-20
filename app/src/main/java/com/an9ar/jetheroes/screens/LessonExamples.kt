package com.an9ar.jetheroes.screens

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.an9ar.jetheroes.theme.JetHeroesTheme

@Composable
fun AndroidView(list: List<String> = emptyList()) {
    Column {
        androidx.compose.ui.viewinterop.AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .height(50.dp),
            factory = { context ->
                TextView(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                    )
                    text = "Example Text"
                }
            })
    }
}

@Composable
fun CallToActionButton(
    modifier: Modifier = Modifier,
    text: String = "Here is action",
    onClick: () -> Unit = {},
) {
    Column {
        Button(
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.secondary
            ),
            onClick = onClick,
            modifier = modifier.wrapContentSize(),
        ) {
            Text(text)
        }
    }
}

class CallToActionViewButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AbstractComposeView(context, attrs, defStyle) {
    var text by mutableStateOf("Here is button")
    var onClick by mutableStateOf({})

    @Composable
    override fun Content() {
        JetHeroesTheme {
            CallToActionButton(
                text = text,
                onClick = onClick
            )
        }
    }
}