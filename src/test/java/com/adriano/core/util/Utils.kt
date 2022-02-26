package com.adriano.core.util

import com.adriano.driver.TLDriverFactory.driver
import org.apache.commons.io.FileUtils
import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * Classe com metodos de apoio, que otimizam a codificacao das classes de pagina
 *
 * @author thiago.freire
 */
object Utils {
    fun extrairStackTrace(e: Exception): String {
        val stack = e.stackTrace
        var exception = ""
        for (s in stack) {
            exception = """$exception$s
		"""
        }
        return exception
    }

    /**
     * Metodo para capturar screenshot
     * @param driver
     *
     * @param fileName
     * - Nome do arquivo
     */
    fun takeScreenshot(casoDeteste: String, nomeEvidencia: String) {
        val scrFile = (driver as TakesScreenshot)
                .getScreenshotAs(OutputType.FILE)
        try {
            val pastaImages = "Evidencias/$casoDeteste/$nomeEvidencia.jpg"
            val pastaResultado = "resultadoTest/$pastaImages"
            val fileDestino = File(pastaResultado)
            FileUtils.copyFile(scrFile, fileDestino, true)
        } catch (e: Exception) {
            System.err.println(e.message)
        }
    }

    val data: Date
        get() {
            val cal = Calendar.getInstance()
            return cal.time
        }
    val dataAtual: String?
        get() = try {
            val formatDate = SimpleDateFormat("ddMMyyyy")
            val calendar: Calendar = GregorianCalendar()
            val data = Date()
            calendar.time = data
            formatDate.format(data)
        } catch (e: Exception) {
            null
        }

    fun WebElement?.haveClass(className: String): Boolean {
        if (this == null) return false
        return this.getAttribute("class").split(" ").contains(className)
    }

    fun Actions.dragAndHold(element: WebElement, xOffset: Int, yOffset: Int): Actions {
        this.dragAndDropBy(element, xOffset, yOffset).build().perform()
        return this.clickAndHold(element).moveByOffset(0, 1)
    }
}
