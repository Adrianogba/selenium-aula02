package com.adriano.teste

import com.adriano.core.InvokedMethodListener
import com.adriano.page.PageGoogle
import org.testng.annotations.Listeners
import org.testng.annotations.Test

@Listeners(InvokedMethodListener::class)
class GoogleSearchTest {
    @Test
    @Throws(Exception::class)
    fun google0() {
        PageGoogle()
                .openPage(PageGoogle::class.java, "http://www.google.com")
                .buscarNoGoogle("recursividade")
    }

    @Test
    @Throws(Exception::class)
    fun google1() {
        PageGoogle().openPage(PageGoogle::class.java, "http://www.google.com").buscarNoGoogle("selenium")
    }
}