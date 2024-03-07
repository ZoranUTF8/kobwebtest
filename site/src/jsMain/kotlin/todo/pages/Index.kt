package todo.pages

import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.*
import com.varabyte.kobweb.browser.api
import com.varabyte.kobweb.browser.http.http
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
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.em
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Footer
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import org.w3c.xhr.FormData
import todo.components.widgets.LoadingSpinner
import todo.components.widgets.TodoCard
import todo.components.widgets.TodoForm
import todo.model.TodoItem
import kotlin.js.json


@Page
@Composable
fun HomePage() {
    var id by remember { mutableStateOf("") }
    var ready by remember { mutableStateOf(false) }
    var loadingCount by remember { mutableStateOf(1) } // How many API requests are occurring at the same time
    val todos = remember { mutableStateListOf<TodoItem>() }
    val coroutineScope = rememberCoroutineScope()



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
                    println("Im in the button onclick before the  coroutine")

                    coroutineScope.launch {
                        println("Im in the button coroutine start")
                        val response = window.api.post("getweather").decodeToString()
//                        val response = window.http.post("getweather").decodeToString()

//                        val response = window.http.get("https://api.weather.gov/").decodeToString()
//                        println("response is ${response}")
//
//                        val myBody = MyBody(
//                            "user",
//                            "get_user_info_from_email",
//                            "gladmin@gl-navi.co.jp"
//                        )
//
//                        val byteArray = Json.encodeToString(myBody).encodeToByteArray()
//
//
//                        val awsDB = "https://p1mjhl95g4.execute-api.ap-northeast-1.amazonaws.com/default/no-proxy"
//
//                        val header = mapOf("x-api-key" to Credentials.API_KEY, "Access-Control-Allow-Origin" to '*')
//
//                        val response = window.http.post(awsDB,headers = header, body = byteArray).decodeToString()


//
//                        val postData = "Hello, HTTPBin!".encodeToByteArray()
//
//                        val response = window.http.post(
//                            resource = "https://httpbin.org/post",
//                            body = postData
//                        ).decodeToString()

                        println("response is $response")


                        println("Im in the button coroutine end ")

                    }
                    println("Im in the button onclick after the  coroutine")

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
