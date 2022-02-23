package com.adriano.page

import com.adriano.core.CorePage
import com.adriano.driver.TLDriverFactory
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory

class PageGuruRegister : CorePage<PageGuruRegister>() {

    @FindBy(name = "firstName")
    private val firstName: WebElement? = null

    @FindBy(name = "lastName")
    private val lastName: WebElement? = null

    @FindBy(name = "phone")
    private val phone: WebElement? = null

    @FindBy(name = "userName")
    private val email: WebElement? = null

    @FindBy(name = "address1")
    private val address: WebElement? = null

    @FindBy(name = "city")
    private val city: WebElement? = null

    @FindBy(name = "state")
    private val stateProvince: WebElement? = null

    @FindBy(name = "postalCode")
    private val postalCode: WebElement? = null

    @FindBy(name = "country")
    private val country: WebElement? = null

    @FindBy(name = "email")
    private val userName: WebElement? = null

    @FindBy(name = "password")
    private val password: WebElement? = null

    @FindBy(name = "confirmPassword")
    private val confirmPassword: WebElement? = null

    @FindBy(name = "submit")
    private val btnSubmit: WebElement? = null

    init {
        this.driver = TLDriverFactory.driver
        PageFactory.initElements(this.driver, this)
    }

}