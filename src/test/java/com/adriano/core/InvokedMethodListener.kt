package com.adriano.core

import org.testng.IInvokedMethodListener
import org.testng.IInvokedMethod
import org.testng.ITestResult
import com.adriano.driver.TLDriverFactory

class InvokedMethodListener : IInvokedMethodListener {
    override fun beforeInvocation(method: IInvokedMethod, testResult: ITestResult) {
        if (method.isTestMethod) {
            println("Metodo de teste invocado no Before. " + Thread.currentThread().id)
            val browserName = method.testMethod.xmlTest.localParameters["browser"]
            if (null == browserName) {
                TLDriverFactory.setDriver("chrome")
            } else {
                TLDriverFactory.setDriver(browserName)
            }
        }
    }

    override fun afterInvocation(method: IInvokedMethod, testResult: ITestResult) {
        if (method.isTestMethod) {
            println("Metodo de teste invocado no After. " + Thread.currentThread().id)
            val driver = TLDriverFactory.driver
            driver?.quit()
        }
    }
}