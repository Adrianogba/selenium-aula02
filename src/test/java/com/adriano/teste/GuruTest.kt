package com.adriano.teste

import com.adriano.core.InvokedMethodListener
import com.adriano.page.PageGuruDeleteCostumer
import com.adriano.page.PageGuruDragDrop
import com.adriano.page.PageGuruHome
import com.adriano.page.PageGuruRegister
import org.testng.annotations.Listeners
import org.testng.annotations.Test

@Listeners(InvokedMethodListener::class)
class GuruTest {
    @Test
    @Throws(Exception::class)
    fun loginSucesso() {
        PageGuruHome()
            .openPage(PageGuruHome::class.java, "http://demo.guru99.com/test/login.html")
            .acessarLoginGuru("thiagoovcf@gmail.com", "123")
            .validarAcessoOK()
    }

    @Test
    @Throws(Exception::class)
    fun loginXablau() {
        PageGuruHome()
            .openPage(PageGuruHome::class.java, "http://demo.guru99.com/test/login.html")
            .acessarLoginGuru("xablau", "xablau123")
            .validarAcessoOK()
    }

    @Test
    @Throws(Exception::class)
    fun alocarBlocos() {
        PageGuruDragDrop()
            .openPage(PageGuruDragDrop::class.java, "https://demo.guru99.com/test/drag_drop.html")
            .verifyAndMoveDraggables()
            .verifySuccess()
    }

    @Test
    @Throws(Exception::class)
    fun registrar() {
        PageGuruRegister()
            .openPage(PageGuruRegister::class.java, "http://demo.guru99.com/test/newtours/register.php")
            .guruRegister()
    }

    @Test
    @Throws(Exception::class)
    fun deletarCliente() {
        PageGuruDeleteCostumer()
            .openPage(PageGuruDeleteCostumer::class.java, "https://demo.guru99.com/test/delete_customer.php")
    }
}