package pl.inpost.recruitmenttask.ui.extensions

import androidx.compose.ui.text.intl.Locale
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

private const val STATUS_DATE_TIME_PATTERN = "eee. | dd:MM:yy | HH:mm"

private val statusDateTimeFormatter =
    DateTimeFormatter.ofPattern(STATUS_DATE_TIME_PATTERN, Locale.current.platformLocale)

fun ZonedDateTime.formatStatusDateTime(): String = format(statusDateTimeFormatter)
    .lowercase(Locale.current.platformLocale)