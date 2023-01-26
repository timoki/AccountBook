package kr.timoky.accountbook.utils

import android.content.res.ColorStateList
import android.graphics.Color
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.textfield.TextInputEditText
import kr.timoky.accountbook.R
import kr.timoky.accountbook.base.MoneyType
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter(
    "setDateText",
    "formatPatternString"
)
fun setDateText(
    view: TextView,
    time: Long,
    formatPatternString: String? = "yyyy-MM-dd"
) {
    view.text = SimpleDateFormat(formatPatternString, Locale.KOREA).format(Date(time))
}

@BindingAdapter(
    value = [
        "app:backgroundTint",
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

@BindingAdapter("setMoney")
fun setMoney(
    view: TextInputEditText,
    value: Int
) {
    if (value <= 0) {
        view.setText("")
        return
    }

    view.setText("$value")
    view.setSelection(view.text?.length ?: 100)
}

@InverseBindingAdapter(attribute = "setMoney")
fun getMoney(
    view: TextInputEditText
): Int {
    return if (view.text.isNullOrEmpty()) 0
    else view.text.toString().toInt()
}

@BindingAdapter("setMoneyAttrChanged")
fun setMoneyListener(
    view: TextInputEditText,
    attrChanged: InverseBindingListener
) {
    attrChanged.run {
        view.addTextChangedListener {
            onChange()
        }
    }
}

@BindingAdapter("setChecked")
fun setCheckedToggleGroup(
    toggleGroup: MaterialButtonToggleGroup,
    state: MoneyType
) {
    toggleGroup.findViewWithTag<MaterialButton>(state).isChecked = true
}

@InverseBindingAdapter(attribute = "setChecked")
fun getCheckedToggleGroup(
    toggleGroup: MaterialButtonToggleGroup
): MoneyType {
    return toggleGroup.findViewById<MaterialButton>(toggleGroup.checkedButtonId).tag as MoneyType
}

@BindingAdapter("setCheckedAttrChanged")
fun setCheckedToggleGroupListener(
    toggleGroup: MaterialButtonToggleGroup,
    attrChanged: InverseBindingListener
) {
    attrChanged.run {
        toggleGroup.addOnButtonCheckedListener { _, _, _ ->
            onChange()
        }
    }
}