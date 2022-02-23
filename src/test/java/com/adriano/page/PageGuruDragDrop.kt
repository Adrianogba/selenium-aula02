package com.adriano.page

import com.adriano.core.CorePage
import com.adriano.core.util.Utils.haveClass
import com.adriano.driver.TLDriverFactory
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Action
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.support.ui.WebDriverWait

class PageGuruDragDrop : CorePage<PageGuruDragDrop>() {

    @FindBy(xpath = "//*[@id=\"products\"]/div/ul")
    private val draggables: WebElement? = null

    @FindBy(xpath = "//*[@id=\"amt7\"]")
    private val debitAmountContainer: WebElement? = null

    @FindBy(xpath = "//*[@id=\"bank\"]")
    private val debitAcountContainer: WebElement? = null

    @FindBy(xpath = "//*[@id=\"loan\"]")
    private val creditAmountContainer: WebElement? = null

    @FindBy(xpath = "//*[@id=\"amt8\"]")
    private val creditAcountContainer: WebElement? = null

    @FindBy(xpath = "//*[@id=\"equal\"]/a")
    private val successMessage: WebElement? = null

    @FindBy(xpath = "//*[@id=\"e1\"]")
    private val cantMove: WebElement? = null

    init {
        this.driver = TLDriverFactory.driver
        PageFactory.initElements(this.driver, this)
    }

    fun verifyAndMoveDraggables(): PageGuruDragDrop {
        val draggables = draggables?.findElements(By.tagName("li"))
                ?: throw NullPointerException()
        val action = Actions(driver)
        lateinit var dragAndDrop: Action

        draggables.forEach { draggable ->
            getContainersDraggableCanBeMovedTo(draggable).forEach { container ->
                dragAndDrop = action.clickAndHold(draggable)
                                .moveToElement(container)
                                .release()
                                .build()
                dragAndDrop.perform()
            }
        }
        return PageGuruDragDrop()
    }
    
    fun verifySuccess() {
        val successMessage = successMessage ?: throw NullPointerException()
        assert(successMessage.text == "Perfect!")
    }

    private fun getContainersDraggableCanBeMovedTo(draggable: WebElement): List<WebElement> {

        val action = Actions(driver)
        action.clickAndHold(draggable).moveByOffset(0,20).release().build().perform()
        val cannotBeMoved = cantMove?.isDisplayed

        if (cannotBeMoved == true) return emptyList()

        val draggableContainers = mutableListOf<WebElement>()

        listOf(debitAmountContainer,
            debitAcountContainer,
            creditAmountContainer,
            creditAcountContainer
        ).forEach { container ->
            if (container != null && container.haveClass("content-active"))
                draggableContainers.add(container)
        }

        return draggableContainers.toList()

    }
}