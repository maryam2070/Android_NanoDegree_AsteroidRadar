package com.udacity.asteroidradar

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.TemporalAdjusters
import java.time.temporal.WeekFields
import java.util.*


/* to avoid plagiarism and cheating,that class is copied by me from  https://stackoverflow.com/questions/22890644/get-current-week-start-and-end-date-in-java-monday-to-sunday
to help my implement click filters in the menu since the way to get first and end of week not supported in the course
thank you for your Understanding in advance
* */
class ThisLocalizedWeek(locale: Locale) {
    private val locale: Locale
    private val firstDayOfWeek: DayOfWeek
    private val lastDayOfWeek: DayOfWeek
    val firstDay: LocalDate
        get() = LocalDate.now(TZ).with(
            TemporalAdjusters.previousOrSame(
                firstDayOfWeek
            )
        )
    val lastDay: LocalDate
        get() = LocalDate.now(TZ).with(
            TemporalAdjusters.nextOrSame(
                lastDayOfWeek
            )
        )

    override fun toString(): String {
        return java.lang.String.format(
            "The %s week starts on %s and ends on %s",
            locale.getDisplayName(),
            firstDayOfWeek,
            lastDayOfWeek
        )
    }

    companion object {
        // Try and always specify the time zone you're working with
        private val TZ: ZoneId = ZoneId.of("Pacific/Auckland")
    }

    init {
        this.locale = locale
        firstDayOfWeek = WeekFields.of(locale).getFirstDayOfWeek()
        lastDayOfWeek =
            DayOfWeek.of((firstDayOfWeek.getValue() + 5) % DayOfWeek.values().size + 1)
    }
}