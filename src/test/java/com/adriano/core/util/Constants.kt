package com.adriano.core.util

object Constants {

  const val DEFAULT_TIMEOUT_IN_SECS = 10L

  /**
   * Scripts
   */
  const val JS_CHECK_PAGE_IS_READY = "return (document.readyState == 'complete' && jQuery.active == 0)"

}