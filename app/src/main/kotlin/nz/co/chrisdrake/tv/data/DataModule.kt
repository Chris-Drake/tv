package nz.co.chrisdrake.tv.data

import dagger.Module
import dagger.Provides
import nz.co.chrisdrake.tv.data.api.ApiModule
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import javax.inject.Singleton

@Module(includes = arrayOf(ApiModule::class)) class DataModule {
  @Provides @Singleton fun provideHttpClient(): OkHttpClient {
    val logger = HttpLoggingInterceptor.Logger {
      Timber.tag("OkHttp").d(it);
    }
    val loggingInterceptor = HttpLoggingInterceptor(logger).apply {
      level = HttpLoggingInterceptor.Level.BASIC
    }

    return OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()
  }
}