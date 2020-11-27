import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.horairesbustb.dao.StopTimeDao
import com.example.horairesbustb.model.StopTime

@Database(entities = [StopTime ::class], version = 1, exportSchema = false)
abstract class StopTimeDatabase : RoomDatabase() {

    abstract fun stopTimeDao(): StopTimeDao

    companion object {
        @Volatile
        private var INSTANCE: StopTimeDatabase? = null

        fun getDatabase(context: Context): StopTimeDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StopTimeDatabase::class.java,
                    "StopTimes"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}