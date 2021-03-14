package app.upaya.timer.hints

import android.content.Context
import app.upaya.timer.R


class HintRepository(private val context: Context) {

    fun getRandomHint() : Hint {
        val hint: String = context.resources.getStringArray(R.array.hints).random()
        return Hint(hint = hint)
    }

}
