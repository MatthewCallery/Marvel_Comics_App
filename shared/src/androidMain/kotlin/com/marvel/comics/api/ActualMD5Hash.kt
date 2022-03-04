package com.marvel.comics.api

import com.marvel.comics.BuildKonfig
import io.ktor.util.toLowerCasePreservingASCIIRules
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

actual fun md5Hash(timestamp: String): String {
    var m: MessageDigest? = null
    val s = "$timestamp${BuildKonfig.privateMarvelApiKey}${BuildKonfig.publicMarvelApiKey}"

    try {
        m = MessageDigest.getInstance("MD5")
    } catch (e: NoSuchAlgorithmException) {
        e.printStackTrace()
    }

    m!!.update(s.toByteArray(), 0, s.length)
    return BigInteger(1, m.digest()).toString(16).toLowerCasePreservingASCIIRules()
}
