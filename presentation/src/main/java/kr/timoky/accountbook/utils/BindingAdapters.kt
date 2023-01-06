package kr.timoky.accountbook.utils

import android.content.res.ColorStateList
import android.graphics.Color
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.CircleCropTransformation
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

@BindingAdapter(
    value = [
        "setIconSrc"
    ]
)
fun setIconSrc(
    view: TextView,
    image: Int
) {
    val drawable = ContextCompat.getDrawable(
        view.context,
        image
    )

    view.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
}

@BindingAdapter(
    value = [
        "coilSrc",
        "coilCircularCrop",
    ],
    requireAll = false
)
fun bindCoilSrc(
    view: ImageView,
    @DrawableRes drawableRes: Int?,
    circularCrop: Boolean = false
) {
    if (drawableRes == null) return

    view.load(drawableRes) {
        if (circularCrop) transformations(CircleCropTransformation())
    }
}