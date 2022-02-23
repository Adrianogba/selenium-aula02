package com.adriano.page

import com.adriano.core.CorePage
import com.adriano.core.util.Utils
import com.adriano.driver.TLDriverFactory
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory
import org.testng.Assert

class PageGuruSucess : CorePage<PageGuruSucess>() {
    @FindBy(xpath = "/html/body/div[4]/div/div/h3")
    private val msgSucesso: WebElement? = null

    init {
        this.driver = TLDriverFactory.driver
        PageFactory.initElements(this.driver, this)
    }

    fun validarAcessoOK() {
        aguardarElementoVisivel(msgSucesso!!)
        Assert.assertEquals(getTextElement(msgSucesso), "Successfully Logged in...")
        Utils.takeScreenshot("loginSucesso", "001")
    }
}