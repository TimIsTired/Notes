package com.timistired.notes

import com.timistired.notes.util.dateHelper.DateHelper
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test
import java.util.Calendar.*

class DateHelperTest {

    @Test
    fun `getDateAsString produces expected string`() {
        val calendar = getInstance().apply {
            set(YEAR, 2019)
            set(MONTH, 11)
            set(DAY_OF_MONTH, 24)
            set(HOUR_OF_DAY, 13)
            set(MINUTE, 37)
        }
        val actual = DateHelper().getDateAsString(calendar.time)
        actual shouldBeEqualTo "24.12.2019 13:37"
    }
}