package todo.pages

import kotlinx.serialization.Serializable


@Serializable
data class MyBody(
    val table: String,
    val query_type: String,
    val email: String
)
