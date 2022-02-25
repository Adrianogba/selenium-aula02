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

class PageGuruDragDrop : CorePage<PageGuruDragDrop>() {

    @FindBy(xpath = "//*[@id=\"products\"]/div/ul")
    private val draggables: WebElement? = null

    @FindBy(id = "amt7")
    private val debitAmountContainer: WebElement? = null

    @FindBy(id = "bank")
    private val debitAcountContainer: WebElement? = null

    @FindBy(id = "loan")
    private val creditAmountContainer: WebElement? = null

    @FindBy(id = "amt8")
    private val creditAcountContainer: WebElement? = null

    @FindBy(id = "equal")
    private val successMessage: WebElement? = null

    @FindBy(id = "e1")
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
        action.clickAndHold(draggable).moveByOffset(0,30).build().perform()
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
        Thread.sleep(50)
        action.release().build().perform()

        return draggableContainers.toList()

    }
}