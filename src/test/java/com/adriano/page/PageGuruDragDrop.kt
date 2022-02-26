package com.adriano.page

import com.adriano.core.CorePage
import com.adriano.core.util.Utils.dragAndHold
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

    /** DICIONARIO
     * Draggable = bloquinho a ser movido
     * Container = bloco onde o bloquinho deve ser movido
     */

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

    /**
     * Lista dos containers
     */
    private val containers = listOf(
        debitAmountContainer,
        debitAcountContainer,
        creditAmountContainer,
        creditAcountContainer
    )

    init {
        this.driver = TLDriverFactory.driver
        PageFactory.initElements(this.driver, this)
    }

    /**
     * Move os draggables aonde for possível
     */
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

    /**
     * Finaliza o teste e printa quanto tempo levou a execução desse teste
     */
    fun verifySuccess() = assert(hasFinished())

    /**
     * Informa se o desafio foi concluido
     */
    private fun hasFinished(): Boolean {
        val successMessage = successMessage ?: return false
        return successMessage.text == "Perfect!"
    }

    /**
     * Espera até algum container fique disponível a um draggable
     * ou se surgiu a mensagem onde ele não pode ser movido a nenhum.
     */
    private fun waitDraggableInstructions() {
        val wait = WebDriverWait(driver, 1)
        try {
            wait.until {
                containers.any { it.haveClass("content-active") } || cannotBeMoved
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Informa se apareceu a mensagem quando um
     * draggable pode ser movido ou não a algum container
     */
    private val cannotBeMoved: Boolean get() = cantMove?.isDisplayed == true

    /**
     * Retorna para um bloquinho a quais containers
     * ele pode ser movido antes de ser movido
     */
    private fun getContainersDraggableCanBeMovedTo(draggable: WebElement): List<WebElement> {
        if (hasFinished()) return emptyList()

        val action = Actions(driver)
        action.dragAndHold(draggable, 0, 1).build().perform()

        if (cannotBeMoved) return emptyList()

        val draggableContainers = mutableListOf<WebElement>()

        containers.forEach { container ->
            if (container != null
                && container.haveClass("content-active")
                && !isDraggableAlreadyInContainer(draggable, container)
            )
                draggableContainers.add(container)
        }
        waitDraggableInstructions()
        action.release().build().perform()

        return draggableContainers.toList()

    }

    /**
     * Verifica se o draggable já existe no container
     */
    private fun isDraggableAlreadyInContainer(
        draggable: WebElement,
        container: WebElement
    ): Boolean = container.findElements(By.tagName("li")).any { it.text == draggable.text }

}