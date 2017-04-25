package nz.co.chrisdrake.tv.data.api;

import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import javax.inject.Singleton

@Module class ApiModule {
  @Provides @Singleton fun provideRetrofit(client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl("http://www.freeviewnz.tv/localservices/")
        .client(client)
        .addConverterFactory(SimpleXmlConverterFactory.createNonStrict())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .build()
  }

  @Provides @Singleton fun provideApi(retrofit: Retrofit) = retrofit.create(ApiService::class.java)
}
