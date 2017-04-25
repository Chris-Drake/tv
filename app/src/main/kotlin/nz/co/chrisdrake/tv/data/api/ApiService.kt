package nz.co.chrisdrake.tv.data.api;

import io.reactivex.Single
import nz.co.chrisdrake.tv.data.api.model.OpgResponse
import org.threeten.bp.LocalDate
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface ApiService {
  @GET("opg/schedule") @Headers("Accept: text/xml")
  fun listings(): Single<OpgResponse>

  @GET("opg/schedule/{date}") @Headers("Accept: text/xml")
  fun listings(@Path("date") date: LocalDate): Single<OpgResponse>
}
