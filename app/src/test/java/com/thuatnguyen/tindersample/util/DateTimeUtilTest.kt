package com.thuatnguyen.tindersample.util

import com.google.common.truth.Truth.assertThat
import org.junit.Test


class DateTimeUtilTest {

    @Test
    fun longToDate() {
        assertThat(1599036006L.toDate()).isEqualTo("02/09/2020")
        assertThat(492337668L.toDate()).isEqualTo("08/08/1985")
    }

    @Test
    fun stringToDate() {
        assertThat("1599036157".toDate()).isEqualTo("02/09/2020")
    }
}