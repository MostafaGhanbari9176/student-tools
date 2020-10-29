package ir.pepotec.app.awesomeapp.model.student.ability

import androidx.annotation.Keep

@Keep
class AbilityStatus {
    companion object {
        const val darEntezar = 0
        const val montasherShode = 1
        const val radShode = 2
        const val adamNamayesh = 3
        const val delete = 4
    }
}

@Keep
data class AbilityData(
    val ability_id: Int,
    val user_id: Int,
    val subject: String,
    val resume: String,
    val description: String,
    val add_date: String,
    val status: Int,
    val seen_num: Int
)

@Keep
data class AbilityList(
    val ability_id: Int,
    val subject: String
)

