package com.adriano.core

import org.openqa.selenium.Platform
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.firefox.FirefoxProfile
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.remote.DesiredCapabilities
import java.lang.Exception
import org.testng.Assert
import java.io.File

class OptionsManager {
    //options.setHeadless(true);
    //Get Chrome Options
    val chromeOptions: ChromeOptions
        get() {
            val options = ChromeOptions()
            options.addArguments("--start-maximized")
            options.addArguments("--ignore-certificate-errors")
            options.addArguments("--disable-popup-blocking")
            //options.setHeadless(true);
            System.setProperty("webdriver.chrome.driver", File("src/test/resources/chromedriver.exe").absolutePath)
            return options
        }//Accept Untrusted Certificates

    //Use No Proxy Settings
    //Set Firefox profile to capabilities
    //Get Firefox Options
    val firefoxOptions: FirefoxOptions
        get() {
            val options = FirefoxOptions()
            val profile = FirefoxProfile()
            //Accept Untrusted Certificates
            profile.setAcceptUntrustedCertificates(true)
            profile.setAssumeUntrustedCertificateIssuer(false)
            //Use No Proxy Settings
            profile.setPreference("network.proxy.type", 0)
            //Set Firefox profile to capabilities
            options.setCapability(FirefoxDriver.PROFILE, profile)
            return options
        }
    val remoteDriverChrome: DesiredCapabilities?
        get() {
            try {
                val desiredCapabilities = DesiredCapabilities.chrome()
                desiredCapabilities.platform = Platform.LINUX
                desiredCapabilities.setCapability("chrome.verbose", true)
                return desiredCapabilities
            } catch (e: Exception) {
                Assert.fail(e.message)
            }
            return null
        }
}