package todo.pages

import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.*
import com.varabyte.kobweb.browser.api
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.WhiteSpace
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.style.ComponentStyle
import com.varabyte.kobweb.silk.components.style.base
import com.varabyte.kobweb.silk.components.style.toModifier
import com.varabyte.kobweb.silk.components.text.SpanText
import kotlinx.browser.window
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.em
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Footer
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import todo.components.widgets.LoadingSpinner
import todo.components.widgets.TodoCard
import todo.components.widgets.TodoForm
import todo.model.TodoItem

private suspend fun loadAndReplaceTodos(id: String, todos: SnapshotStateList<TodoItem>) {
    return window.api.get("list?owner=$id").let { listBytes ->
        Snapshot.withMutableSnapshot {
            todos.clear()
            todos.addAll(Json.decodeFromString(listBytes.decodeToString()))
        }
    }
}

val TitleStyle by ComponentStyle.base {
    Modifier
        .lineHeight(1.15)
        .fontSize(4.cssRem)
        .margin(top = 0.4.em, bottom = 0.6.em)
        .fontWeight(FontWeight.Bold)
}

@Page
@Composable
fun HomePage() {
    var id by remember { mutableStateOf("") }
    var ready by remember { mutableStateOf(false) }
    var loadingCount by remember { mutableStateOf(1) } // How many API requests are occurring at the same time
    val todos = remember { mutableStateListOf<TodoItem>() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        check(!ready && loadingCount == 1)
        id = window.localStorage.getItem("id") ?: run {
            window.api.get("id").decodeToString().also {
                window.localStorage.setItem("id", it)
            }
        }

        loadAndReplaceTodos(id, todos)
        loadingCount--
        ready = true
    }

    Column(
        modifier = Modifier.fillMaxSize().minWidth(600.px),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {


        Column(Modifier.fillMaxSize()) {
            Column(
                Modifier.fillMaxWidth().maxWidth(800.px).align(Alignment.CenterHorizontally),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                Button(onClick = {
                    coroutineScope.launch {
                        loadingCount++
                        window.api.post("getWeatherData").decodeToString()
                        loadingCount--
                    }
                }) { Text(value = "Get weather data") }


            }

            Spacer()
            Footer {
                Row {
                    SpanText("Project inspired by kokoska   ")

                }
            }
        }
    }
}
