import androidx.room.Room
import androidx.room.RoomDatabase
import baccaro.lucas.com.data.local.dao.AppDatabase
import baccaro.lucas.com.data.local.dao.instantiateImpl
import org.koin.dsl.module
import platform.Foundation.NSHomeDirectory

fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val dbFilePath = NSHomeDirectory() + "/app.db"
    return Room.databaseBuilder<AppDatabase>(
        name = dbFilePath,
        factory = { AppDatabase::class.instantiateImpl() }
    )
}

actual val nativeModule = module {
    single { getDatabaseBuilder() }
}