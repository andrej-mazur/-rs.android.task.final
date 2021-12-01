package com.example.watchlist2.data.remote.interceptor

import com.google.common.util.concurrent.RateLimiter
import okhttp3.Interceptor
import okhttp3.Response


@Suppress("UnstableApiUsage")
class RateLimiterInterceptor(
    private val limiter: RateLimiter = RateLimiter.create(PERMITS_PER_SECOND)
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        limiter.acquire(PERMITS_TO_ACQUIRE)
        var response = chain.proceed(chain.request())
        if (response.code() == 429) { // retry if being rate limited
            limiter.acquire(PERMITS_TO_ACQUIRE_IF_RATE_LIMITED)
            response = chain.proceed(chain.request())
        }
        return response
    }

    companion object {
        const val PERMITS_PER_SECOND = 1.0
        const val PERMITS_TO_ACQUIRE = 1
        const val PERMITS_TO_ACQUIRE_IF_RATE_LIMITED = 4
    }
}
