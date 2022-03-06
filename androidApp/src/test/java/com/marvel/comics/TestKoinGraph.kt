package com.marvel.comics

import com.marvel.comics.android.dependencyinjection.appModule
import com.marvel.comics.dependencyinjection.initKoin
import android.content.Context
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.test.check.checkModules
import org.koin.test.mock.MockProviderRule
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class TestKoinGraph {

    private val context = getApplicationContext<Context>()

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        Mockito.mock(clazz.java)
    }

    @Test
    fun `checking koin modules`() {
        initKoin(isTest = true) {
            androidContext(context)
            modules(appModule)
        }.checkModules()
    }
}
