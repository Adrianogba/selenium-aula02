package com.adriano.page

import com.adriano.core.CorePage
import com.adriano.driver.TLDriverFactory
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory

class PageGuruDeleteCostumer : CorePage<PageGuruDeleteCostumer>() {

    @FindBy(name = "name")
    private val costumerId: WebElement? = null

    @FindBy(name = "submit")
    private val btnSubmit: WebElement? = null


    init {
        this.driver = TLDriverFactory.driver
        PageFactory.initElements(this.driver, this)
    }

}