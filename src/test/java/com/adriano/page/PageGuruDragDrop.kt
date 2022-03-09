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

    /** DICIONÁRIO
     * Draggable = bloquinho a ser movido
     * Container = bloco onde o bloquinho deve ser movido
     */
    @FindBy(xpath = "//*[@id=\"products\"]/div/ul")
    private lateinit var  draggables: WebElement

    @FindBy(id = "amt7")
    private lateinit var  debitAmountContainer: WebElement

    @FindBy(id = "bank")
    private lateinit var  debitAcountContainer: WebElement

    @FindBy(id = "loan")
    private lateinit var  creditAmountContainer: WebElement

    @FindBy(id = "amt8")
    private lateinit var  creditAcountContainer: WebElement

    @FindBy(id = "equal")
    private lateinit var  successMessage: WebElement

    @FindBy(id = "e1")
    private lateinit var cantMove: WebElement

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
        val action = Actions(driver)
        lateinit var dragAndDrop: Action

        val draggables = draggables.findElements(By.tagName("li"))
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
        val successMessage = successMessage
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
    private val cannotBeMoved: Boolean get() = cantMove.isDisplayed

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
            if (container.haveClass("content-active")
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