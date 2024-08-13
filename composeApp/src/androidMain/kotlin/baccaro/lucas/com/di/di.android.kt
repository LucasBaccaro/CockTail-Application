import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import baccaro.lucas.com.data.local.dao.AppDatabase
import org.koin.dsl.module

fun getDatabaseBuilder(ctx: Context): RoomDatabase.Builder<AppDatabase> {
    val appContext = ctx.applicationContext
    val dbFile = appContext.getDatabasePath("app.db")
    return Room.databaseBuilder<AppDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}
actual val nativeModule = module {
    single { getDatabaseBuilder(get()) }
}