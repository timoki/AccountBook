package kr.timoky.accountbook.utils

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object Common {
    fun calculationTime(createDateTime: Long): String {
        val calendar = Calendar.getInstance() //현재 시간
        val differenceValue = calendar.timeInMillis - createDateTime //현재 시간 - 비교가 될 시간
        val createDate = Date(createDateTime)
        val nowYear = calendar.get(Calendar.YEAR)
        calendar.time = createDate

        return when {
            differenceValue < 60000 -> { //59초 보다 적다면
                "방금 전"
            }

            differenceValue < 3600000 -> { //59분 보다 적다면
                TimeUnit.MILLISECONDS.toMinutes(differenceValue).toString() + "분 전"
            }

            differenceValue < 86400000 -> { //23시간 보다 적다면
                TimeUnit.MILLISECONDS.toHours(differenceValue).toString() + "시간 전"
            }

            calendar.get(Calendar.YEAR) == nowYear -> { // 올해라면
                SimpleDateFormat("MM월 dd일", Locale.KOREA).format(createDate)
            }

            else -> { //그 외
                SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREA).format(createDate)
            }
        }
    }
}