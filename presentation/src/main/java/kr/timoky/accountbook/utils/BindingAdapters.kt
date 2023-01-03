package kr.timoky.accountbook.utils

import android.content.res.ColorStateList
import android.graphics.Color
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import kr.timoky.accountbook.R

@BindingAdapter(
    value = [
        "account:backgroundTint",
    ]
)
fun setBackgroundTintAndTextColor(
    view: TextView,
    color: Int
) {

    view.backgroundTintList = ColorStateList.valueOf(color)

    view.setTextColor(
        ContextCompat.getColor(
            view.context,
            if (color == Color.BLACK) R.color.white
            else R.color.black
        )
    )
}