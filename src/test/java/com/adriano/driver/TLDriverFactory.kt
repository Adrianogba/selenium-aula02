package com.adriano.driver

import com.adriano.core.OptionsManager
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.support.ui.WebDriverWait
import java.net.MalformedURLException
import java.net.URL

object TLDriverFactory {
    private val optionsManager = OptionsManager()
    private var tlDriver = ThreadLocal<WebDriver>()
    @Synchronized
    fun setDriver(browser: String) {
        if (browser == "firefox") {
            //For Local Usage
            tlDriver = ThreadLocal.withInitial { FirefoxDriver(optionsManager.firefoxOptions) }
        } else if (browser == "chrome") {
            //For Local Usage
            tlDriver.set(ChromeDriver(optionsManager.chromeOptions))
        } else if (browser == "grid") {
            try {
                tlDriver.set(RemoteWebDriver(URL("http://localhost:4444/wd/hub"), optionsManager.remoteDriverChrome))
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            }
        }
    }

    @Synchronized
    fun getWait(driver: WebDriver?): WebDriverWait {
        return WebDriverWait(driver, 20)
    }

    @JvmStatic
    @get:Synchronized
    val driver: WebDriver
        get() = tlDriver.get()

    @Synchronized
    fun endDriver() {
        tlDriver.get().quit()
        tlDriver.remove()
    }
}