package nz.co.chrisdrake.tv.domain.model

sealed class Result<out T> {
  class InProgress<out T> : Result<T>()
  data class Success<out T>(val value: T) : Result<T>()
  data class Failure<out T>(val error: Throwable) : Result<T>()
}