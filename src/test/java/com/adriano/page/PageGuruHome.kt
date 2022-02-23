package com.adriano.page

import com.adriano.core.CorePage
import com.adriano.driver.TLDriverFactory
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory

class PageGuruHome : CorePage<PageGuruHome>() {
    @FindBy(id = "email")
    private val inputEmail: WebElement? = null

    @FindBy(id = "passwd")
    private val inputPasswd: WebElement? = null

    @FindBy(id = "SubmitLogin")
    private val bntLogin: WebElement? = null

    init {
        this.driver = TLDriverFactory.driver
        PageFactory.initElements(this.driver, this)
    }

    fun acessarLoginGuru(email: String?, senha: String?): PageGuruSucess {
        preencherCampo(inputEmail!!, email)
        preencherCampo(inputPasswd!!, senha)
        click(bntLogin!!)
        return PageGuruSucess()
    }
}