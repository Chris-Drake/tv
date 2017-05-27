package nz.co.chrisdrake.tv.data

import android.arch.persistence.room.Room
import android.content.Context
import com.jakewharton.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import nz.co.chrisdrake.tv.data.api.ApiModule
import nz.co.chrisdrake.tv.data.database.AppDatabase
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module(includes = arrayOf(ApiModule::class)) class DataModule {

  @Provides @Singleton fun provideAppDatabase(context: Context): AppDatabase {
    return Room.databaseBuilder(context, AppDatabase::class.java, "tv").build()
  }

  @Provides @Singleton fun provideChannelDao(database: AppDatabase) = database.channelDao();

  @Provides @Singleton fun provideHttpClient(context: Context): OkHttpClient {
    val logger = HttpLoggingInterceptor.Logger {
      Timber.tag("OkHttp").d(it);
    }
    val loggingInterceptor = HttpLoggingInterceptor(logger).apply {
      level = HttpLoggingInterceptor.Level.BASIC
    }

    val cacheDir = File(context.cacheDir, "http")
    val cacheSize: Long = 10 * 1024 * 1024 // 10 MiB
    val cache = Cache(cacheDir, cacheSize)

    return OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .readTimeout(30, TimeUnit.SECONDS)
        .cache(cache)
        .build()
  }

  @Provides @Singleton fun providePicasso(context: Context, httpClient: OkHttpClient): Picasso {
    return Picasso.Builder(context)
        .downloader(OkHttp3Downloader(httpClient))
        .build()
  }
}