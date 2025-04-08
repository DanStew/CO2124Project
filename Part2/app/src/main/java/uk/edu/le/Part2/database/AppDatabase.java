package uk.edu.le.Part2.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import uk.edu.le.Part2.database.dao.CourseDao;
import uk.edu.le.Part2.database.dao.EnrollmentDao;
import uk.edu.le.Part2.database.dao.StudentDao;

@Database(entities = {Course.class, Student.class, Enrollment.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    public abstract CourseDao courseDao();
    public abstract StudentDao studentDao();
    public abstract EnrollmentDao enrollmentDao();

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "university_db")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    //initialises values when started
    private static final RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            Log.d("DB", "Inserting test data into database");
           

            databaseWriteExecutor.execute(()->{
                CourseDao dao = INSTANCE.courseDao();
                dao.deleteAll();

                Course course = new Course("1234","Computer Science", "ayo");
                dao.insert(course);
                course = new Course("32105", "Maths", "Dan");
                dao.insert(course);
            });
        }

    };
}
