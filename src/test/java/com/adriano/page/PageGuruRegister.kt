package com.adriano.page

import com.adriano.core.CorePage
import com.adriano.driver.TLDriverFactory
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory
import org.testng.Assert

class PageGuruRegister : CorePage<PageGuruRegister>() {

    //    FORMULARIO
    @FindBy(name = "firstName")
    private lateinit var firstName: WebElement

    @FindBy(name = "lastName")
    private lateinit var lastName: WebElement

    @FindBy(name = "phone")
    private lateinit var phone: WebElement

    @FindBy(name = "userName")
    private lateinit var email: WebElement

    @FindBy(name = "address1")
    private lateinit var address: WebElement

    @FindBy(name = "city")
    private lateinit var city: WebElement

    @FindBy(name = "state")
    private lateinit var stateProvince: WebElement

    @FindBy(name = "postalCode")
    private lateinit var postalCode: WebElement

    @FindBy(name = "country")
    private lateinit var country: WebElement

    @FindBy(name = "email")
    private lateinit var userName: WebElement

    @FindBy(name = "password")
    private lateinit var password: WebElement

    @FindBy(name = "confirmPassword")
    private lateinit var confirmPassword: WebElement

    @FindBy(name = "submit")
    private lateinit var btnSubmit: WebElement

    //    CONFIRMAÇÃO
    @FindBy(xpath = "/html/body/div[2]/table/tbody/tr/td[2]/table/tbody/tr[4]/td/table/tbody/tr/td[2]/table/tbody/tr[3]/td/p[1]/font/b")
    private lateinit var nameRegister: WebElement

    @FindBy(xpath = "/html/body/div[2]/table/tbody/tr/td[2]/table/tbody/tr[4]/td/table/tbody/tr/td[2]/table/tbody/tr[3]/td/p[2]/font/a")
    private lateinit var signIn: WebElement

    @FindBy(xpath = "/html/body/div[2]/table/tbody/tr/td[2]/table/tbody/tr[4]/td/table/tbody/tr/td[2]/table/tbody/tr[3]/td/p[3]/font/b")
    private lateinit var userNameConfirm: WebElement

    @FindBy(id = "dismiss-button")
    private lateinit var skipAd: WebElement

    //    LOGIN
    @FindBy(xpath = "/html/body/div[2]/table/tbody/tr/td[2]/table/tbody/tr[4]/td/table/tbody/tr/td[2]/table/tbody/tr[5]/td/form/table/tbody/tr[1]/td[2]/input")
    private lateinit var userNameLogin: WebElement

    @FindBy(xpath = "/html/body/div[2]/table/tbody/tr/td[2]/table/tbody/tr[4]/td/table/tbody/tr/td[2]/table/tbody/tr[5]/td/form/table/tbody/tr[2]/td[2]/input")
    private lateinit var passwordLogin: WebElement

    @FindBy(xpath = "/html/body/div[2]/table/tbody/tr/td[2]/table/tbody/tr[4]/td/table/tbody/tr/td[2]/table/tbody/tr[5]/td/form/table/tbody/tr[4]/td/input")
    private lateinit var submitLogin: WebElement

    @FindBy(xpath = "/html/body/div[2]/table/tbody/tr/td[2]/table/tbody/tr[4]/td/table/tbody/tr/td[2]/table/tbody/tr[1]/td/h3")
    private lateinit var confirmLogin: WebElement

    init {
        this.driver = TLDriverFactory.driver
        PageFactory.initElements(this.driver, this)
    }

    fun guruRegister() {
        val firstNameMock = "Fulano"
        val lastNameMock = "Fuleira"
        val userNameMock = "xablau1"
        val passwordMock = "x@blau123"
        preencherCampo(firstName, firstNameMock)
        preencherCampo(lastName, lastNameMock)
        preencherCampo(phone, "987456321")
        preencherCampo(email, "ema@il.com")
        preencherCampo(address, "Rua Coronel Xlablau Junior")
        preencherCampo(city, "Xablablau")
        preencherCampo(stateProvince, "São Xablau")
        preencherCampo(postalCode, "987456321")
        preencherCampo(userName, userNameMock)
        preencherCampo(password, passwordMock)
        preencherCampo(confirmPassword, passwordMock)
        aguardarElementoClicado(btnSubmit)
        click(btnSubmit)

//        CONFIRMAÇÃO
        Assert.assertEquals(getTextElement(nameRegister), "Dear $firstNameMock $lastNameMock,")
        Assert.assertEquals(getTextElement(userNameConfirm), "Note: Your user name is $userNameMock.")
        aguardarElementoClicado(signIn)
        click(signIn)

//         PULAR PUBLICIDADE
//        Thread.sleep(1000)
//        click(skipAd)

//        LOGIN
        aguardarElementoVisivel(userNameLogin)
        preencherCampo(userNameLogin, userNameMock)
        preencherCampo(passwordLogin, passwordMock)
        aguardarElementoClicado(submitLogin)
        click(submitLogin)

//        CONFIRMAR LOGIN
        aguardarElementoVisivel(confirmLogin)
        Assert.assertEquals(getTextElement(confirmLogin), "Login Successfully")
    }

}