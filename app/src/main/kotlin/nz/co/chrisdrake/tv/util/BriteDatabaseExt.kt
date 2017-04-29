package nz.co.chrisdrake.tv.util

import com.squareup.sqlbrite.BriteDatabase
import com.squareup.sqldelight.SqlDelightCompiledStatement

/** https://gist.github.com/AlecStrong/6a6b33c7a961756ed038408df4858be3 */
inline fun <T : SqlDelightCompiledStatement> BriteDatabase.bindAndExecute(compiledStatement: T, bind: T.() -> Unit): Long {
  synchronized(compiledStatement) {
    compiledStatement.bind()
    return when (compiledStatement) {
      is SqlDelightCompiledStatement.Insert -> {
        executeInsert(compiledStatement.table, compiledStatement.program)
      }
      is SqlDelightCompiledStatement.Update -> {
        executeUpdateDelete(compiledStatement.table, compiledStatement.program).toLong()
      }
      is SqlDelightCompiledStatement.Delete -> {
        executeUpdateDelete(compiledStatement.table, compiledStatement.program).toLong()
      }
      else -> throw IllegalStateException("Call execute() on non-mutating compiled statements.")
    }
  }
}