package com.adriano.page

import com.adriano.core.CorePage
import com.adriano.driver.TLDriverFactory
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory

class PageHomeTest : CorePage<PageHomeTest>() {
    @FindBy(name = "q")
    private val inputTextSearch: WebElement? = null

    @FindBy(name = "btnK")
    private val bntSearch: WebElement? = null

    @FindBy(name = "btnK")
    private val bntSearch2: WebElement? = null

    init {
        this.driver = TLDriverFactory.driver
        PageFactory.initElements(this.driver, this)
    }

    fun pesquisarNoGoogle() {
        click(bntSearch!!)
        driver.findElement(By.id("btnK")).click()
    }
}