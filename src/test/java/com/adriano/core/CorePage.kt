package com.adriano.core

import org.openqa.selenium.WebDriver
import com.adriano.driver.TLDriverFactory
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.WebElement
import org.openqa.selenium.WebDriverException
import org.testng.Assert
import java.lang.Exception
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.By
import org.openqa.selenium.NoSuchElementException
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.ui.Select

/**
 *
 * @author thiago.freire
 * @param <T>
 * Classe resposavel por gerencia todas as ações das paginas.
</T> */
abstract class CorePage<T> {
    private var windowHandleJanelaInicial: String? = null
    @JvmField
	var driver: WebDriver = TLDriverFactory.driver

    init {
        PageFactory.initElements(driver, this)
    }

    fun openPage(clazz: Class<T>?, BASE_URL: String): T {
        val page = PageFactory.initElements(driver, clazz)
        driver[BASE_URL + url]
        return page
    }

    val url: String
        get() = ""

    fun preencherCampo(element: WebElement, keysToSend: String?) {
        try {
            element.clear()
            element.sendKeys(keysToSend)
        } catch (e: WebDriverException) {
            Assert.fail("""Nao foi possivel encontrar o elemento para preencher: $element. Pagina: ${driver.title}
 ${e.message}""")
        }
    }

    fun click(element: WebElement) {
        try {
            aguardarElementoVisivel(element)
            element.click()
        } catch (e: Exception) {
            Assert.fail("""Nao foi possivel encontrar o elemento para clicar: $element. Pagina: ${driver.title}
 ${e.message}""")
        }
    }

    fun getTextElement(element: WebElement): String {
        if (!isVisibility(element)) {
            Assert.fail("Erro ao buscar texto em tela. Elemento: [$element] Favor verificar evidencia.")
        }
        return element.text
    }

    fun getValorAtributo(element: WebElement): String {
        return element.getAttribute("value")
    }

    fun selectElementByVisibleText(element: WebElement, textVisible: String) {
        try {
            Select(element).selectByVisibleText(textVisible)
        } catch (e: NoSuchElementException) {
            Assert.fail("Erro ao selecionar no elemento: [" + element.tagName + "] com o o valor: " + textVisible)
        }
    }

    fun selectElementByVisibleValue(element: WebElement, valueVisibel: String) {
        try {
            Select(element).selectByValue(valueVisibel)
        } catch (e: NoSuchElementException) {
            Assert.fail("Erro ao selecionar no elemento: [" + element.tagName + "] com o o valor: " + valueVisibel)
        }
    }

    fun acceptAlert() {
        try {
            val alert = driver.switchTo().alert()
            alert.accept()
        } catch (e: Exception) {
            Assert.fail("Erro ao realizar a confirmacao do Alerta")
        }
    }

    val alert: String
        get() {
            var alerta = ""
            try {
                val alert = driver.switchTo().alert()
                alerta = alert.text
            } catch (e: Exception) {
            }
            return alerta
        }

    fun aguardarElementoVisivel(element: WebElement) {
        try {
            val driverWait = WebDriverWait(driver, LOAD_TIMEOUT.toLong())
            driverWait.until(ExpectedConditions.visibilityOf(element))
        } catch (e: Exception) {
            Assert.fail("Tempo excedido para aguardar elemento: $element")
        }
    }

    fun aguardarListElementoVisivel(elements: List<WebElement?>) {
        try {
            val driverWait = WebDriverWait(driver, LOAD_TIMEOUT.toLong())
            driverWait.until(ExpectedConditions.visibilityOfAllElements(elements))
        } catch (e: Exception) {
            Assert.fail("Tempo excedido para aguardar elemento: $elements")
        }
    }

    fun aguardarElementoDesaparecer(locator: By) {
        try {
            val driverWait = WebDriverWait(driver, LOAD_TIMEOUT.toLong())
            driverWait.until(ExpectedConditions.invisibilityOfElementLocated(locator))
        } catch (e: Exception) {
            Assert.fail("Tempo excedido para aguardar elemento desaparecer da tela: $locator")
        }
    }

    fun aguardarElementoVisivelComTexto(element: WebElement, text: String) {
        try {
            val driverWait = WebDriverWait(driver, LOAD_TIMEOUT.toLong())
            driverWait.until(ExpectedConditions.textToBePresentInElement(element, text.trim { it <= ' ' }))
        } catch (e: Exception) {
            Assert.fail("Tempo excedido para aguardar elemento: $element")
        }
    }

    fun aguardarElementoClicado(element: WebElement) {
        try {
            val wait = WebDriverWait(driver, LOAD_TIMEOUT.toLong())
            wait.until(ExpectedConditions.elementToBeClickable(element))
        } catch (e: Exception) {
            Assert.fail("Tempo excedido para aguardar elemento: $element")
        }
    }

    fun isVisibility(elemento: WebElement): Boolean {
        return try {
            elemento.isDisplayed
        } catch (e: NoSuchElementException) {
            false
        }
    }

    fun isVisibility(locator: By?): Boolean {
        try {
            val element = driver.findElement(locator)
            element.isDisplayed
        } catch (e: Exception) {
            return false
        }
        return true
    }

    fun clicarBotaoDireito(elemento: WebElement?) {
        try {
            val action = Actions(driver)
            action.contextClick(elemento).build().perform()
        } catch (e: Exception) {
            val action = Actions(driver)
            action.contextClick().build().perform()
        }
    }

    fun moverCursorPara(elemento: WebElement?) {
        val action = Actions(driver)
        action.moveToElement(elemento).build().perform()
    }

    fun existText(elemento: WebElement, texto: String?): Boolean {
        aguardarElementoVisivel(elemento)
        return elemento.text.contains(texto!!)
    }

    /**
     * Seta para nova janela aberta
     */
    fun alternarJanela() {
        windowHandleJanelaInicial = driver.windowHandle
        val windowHandles = driver.windowHandles
        for (windowHandle in windowHandles) {
            if (windowHandle != windowHandleJanelaInicial) {
                driver.switchTo().window(windowHandle)
            }
        }
        windowHandleJanelaInicial = windowHandleJanelaInicial
    }

    /**
     * @return Janela anterior
     */
    fun retornarJanelaOriginal() {
        driver.switchTo().window(windowHandleJanelaInicial)
    }

    fun alertaSaidaDoSistema() {
        val alert = driver.switchTo().alert()
        alert.accept()
    }

    fun getElement(by: By?): WebElement {
        return driver.findElement(by)
    }

    fun cleanElement(element: WebElement) {
        try {
            element.clear()
        } catch (e: Exception) {
            Assert.fail("Erro ao limpar campo em tela. $element")
        }
    }

    val title: String
        get() = driver.title

    companion object {
        private const val LOAD_TIMEOUT = 30
    }
}