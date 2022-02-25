package com.adriano.page

import com.adriano.core.CorePage
import com.adriano.driver.TLDriverFactory
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory
import org.testng.Assert

class PageGuruDeleteCostumer : CorePage<PageGuruDeleteCostumer>() {

    @FindBy(name = "name")
    private val customerId: WebElement? = null

    @FindBy(name = "submit")
    private val btnSubmit: WebElement? = null

    init {
        this.driver = TLDriverFactory.driver
        PageFactory.initElements(this.driver, this)
    }

    fun guruDeleteCustomer() {
        if (customerId == null || btnSubmit == null) throw NullPointerException()
        aguardarElementoVisivel(customerId)
        preencherCampo(customerId, "xablau")
        aguardarElementoClicado(btnSubmit)
        click(btnSubmit)
        val alert = driver.switchTo().alert()
        alert.accept()
        val alertMessage = driver.switchTo().alert().text
        Assert.assertEquals(alertMessage, "Customer Successfully Delete!")
        alert.accept()
    }

}