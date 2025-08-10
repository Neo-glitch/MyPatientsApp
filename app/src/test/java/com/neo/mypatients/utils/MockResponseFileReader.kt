package com.neo.mypatients.utils

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import java.io.InputStreamReader

/**
  *  Helper class to read the mock Json response for tests
 */
class MockResponseFileReader(path: String) {
    val content: String
    init {
        val inputStream = this.javaClass.classLoader?.getResourceAsStream(path)
            ?: throw IllegalArgumentException("File not found $path")
        content = InputStreamReader(inputStream).use { reader ->
            reader.readText()
        }
    }
}

// to test if helper class actually works
class MockResponseFileReaderTest {

    @Test
    fun `read simple file`() {
        val reader = MockResponseFileReader("test.json")
        assertThat(reader.content).isEqualTo("\"success\"")
    }
}