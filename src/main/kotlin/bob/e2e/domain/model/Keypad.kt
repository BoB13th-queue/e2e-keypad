package bob.e2e.domain.model

data class Keypad(
    val id: Int,
    val image: String,
    val keypadList: List<String>,
    val token: String
)
