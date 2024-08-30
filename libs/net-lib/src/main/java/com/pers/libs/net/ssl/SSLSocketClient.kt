package com.pers.libs.net.ssl

import android.annotation.SuppressLint
import java.security.KeyStore
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.*

object SSLSocketClient {

    /**
     * 1.信任所有证书
     *
     */
    fun getSSLSocketFactoryAll(): SSLSocketFactory? {
        return try {
            val sslContext: SSLContext = SSLContext.getInstance("SSL")
            sslContext.init(null, getTrustManagers(), SecureRandom())
            sslContext.socketFactory
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    /**
     * 获取TrustManager
     */
    private fun getTrustManagers(): Array<TrustManager>? {
        return arrayOf<TrustManager>(object : X509TrustManager {
            @SuppressLint("TrustAllX509TrustManager")
            override fun checkClientTrusted(chain: Array<X509Certificate?>?, authType: String?) {
            }

            @SuppressLint("TrustAllX509TrustManager")
            override fun checkServerTrusted(chain: Array<X509Certificate?>?, authType: String?) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate?>? {
                //信任所有https 证书
                return arrayOf()
            }
        })
    }


    fun getTrustManager(): X509TrustManager? {
        var trustManagerFactory: TrustManagerFactory?
        try {
            trustManagerFactory =
                TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
            trustManagerFactory.init(null as KeyStore?)
            val trustManagers: Array<TrustManager> =
                trustManagerFactory.getTrustManagers()
            check(!(trustManagers.size != 1 || trustManagers[0] !is X509TrustManager)) {
                "Unexpected default trust managers:" + trustManagers.contentToString()
            }
            return trustManagers[0] as X509TrustManager
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return null
    }


    /**
     * 信任所有的 HTTPS url 域名
     */
    fun getHostnameVerifierAll(): HostnameVerifier {
        return HostnameVerifier { _, _ ->
            true //信任所有域名
        }
    }

}