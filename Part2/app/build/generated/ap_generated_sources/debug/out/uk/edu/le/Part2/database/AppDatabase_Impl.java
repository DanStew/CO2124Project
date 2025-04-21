package uk.edu.le.Part2.database;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;
import uk.edu.le.Part2.database.dao.CourseDao;
import uk.edu.le.Part2.database.dao.CourseDao_Impl;
import uk.edu.le.Part2.database.dao.EnrollmentDao;
import uk.edu.le.Part2.database.dao.EnrollmentDao_Impl;
import uk.edu.le.Part2.database.dao.StudentDao;
import uk.edu.le.Part2.database.dao.StudentDao_Impl;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile CourseDao _courseDao;

  private volatile StudentDao _studentDao;

  private volatile EnrollmentDao _enrollmentDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(3) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `course` (`courseId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `courseCode` TEXT, `courseName` TEXT, `lecturerName` TEXT)");
        db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_course_courseCode` ON `course` (`courseCode`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `student` (`studentId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `fullName` TEXT, `email` TEXT, `matricNum` INTEGER NOT NULL, `username` TEXT NOT NULL)");
        db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_student_username` ON `student` (`username`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `enrollment` (`studentId` INTEGER NOT NULL, `courseId` INTEGER NOT NULL, PRIMARY KEY(`studentId`, `courseId`), FOREIGN KEY(`studentId`) REFERENCES `student`(`studentId`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`courseId`) REFERENCES `course`(`courseId`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'f75ff8f1c7dfb08c5b7a56550c84dd52')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `course`");
        db.execSQL("DROP TABLE IF EXISTS `student`");
        db.execSQL("DROP TABLE IF EXISTS `enrollment`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsCourse = new HashMap<String, TableInfo.Column>(4);
        _columnsCourse.put("courseId", new TableInfo.Column("courseId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCourse.put("courseCode", new TableInfo.Column("courseCode", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCourse.put("courseName", new TableInfo.Column("courseName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCourse.put("lecturerName", new TableInfo.Column("lecturerName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCourse = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCourse = new HashSet<TableInfo.Index>(1);
        _indicesCourse.add(new TableInfo.Index("index_course_courseCode", true, Arrays.asList("courseCode"), Arrays.asList("ASC")));
        final TableInfo _infoCourse = new TableInfo("course", _columnsCourse, _foreignKeysCourse, _indicesCourse);
        final TableInfo _existingCourse = TableInfo.read(db, "course");
        if (!_infoCourse.equals(_existingCourse)) {
          return new RoomOpenHelper.ValidationResult(false, "course(uk.edu.le.Part2.database.Course).\n"
                  + " Expected:\n" + _infoCourse + "\n"
                  + " Found:\n" + _existingCourse);
        }
        final HashMap<String, TableInfo.Column> _columnsStudent = new HashMap<String, TableInfo.Column>(5);
        _columnsStudent.put("studentId", new TableInfo.Column("studentId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudent.put("fullName", new TableInfo.Column("fullName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudent.put("email", new TableInfo.Column("email", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudent.put("matricNum", new TableInfo.Column("matricNum", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudent.put("username", new TableInfo.Column("username", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysStudent = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesStudent = new HashSet<TableInfo.Index>(1);
        _indicesStudent.add(new TableInfo.Index("index_student_username", true, Arrays.asList("username"), Arrays.asList("ASC")));
        final TableInfo _infoStudent = new TableInfo("student", _columnsStudent, _foreignKeysStudent, _indicesStudent);
        final TableInfo _existingStudent = TableInfo.read(db, "student");
        if (!_infoStudent.equals(_existingStudent)) {
          return new RoomOpenHelper.ValidationResult(false, "student(uk.edu.le.Part2.database.Student).\n"
                  + " Expected:\n" + _infoStudent + "\n"
                  + " Found:\n" + _existingStudent);
        }
        final HashMap<String, TableInfo.Column> _columnsEnrollment = new HashMap<String, TableInfo.Column>(2);
        _columnsEnrollment.put("studentId", new TableInfo.Column("studentId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEnrollment.put("courseId", new TableInfo.Column("courseId", "INTEGER", true, 2, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysEnrollment = new HashSet<TableInfo.ForeignKey>(2);
        _foreignKeysEnrollment.add(new TableInfo.ForeignKey("student", "CASCADE", "NO ACTION", Arrays.asList("studentId"), Arrays.asList("studentId")));
        _foreignKeysEnrollment.add(new TableInfo.ForeignKey("course", "CASCADE", "NO ACTION", Arrays.asList("courseId"), Arrays.asList("courseId")));
        final HashSet<TableInfo.Index> _indicesEnrollment = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoEnrollment = new TableInfo("enrollment", _columnsEnrollment, _foreignKeysEnrollment, _indicesEnrollment);
        final TableInfo _existingEnrollment = TableInfo.read(db, "enrollment");
        if (!_infoEnrollment.equals(_existingEnrollment)) {
          return new RoomOpenHelper.ValidationResult(false, "enrollment(uk.edu.le.Part2.database.Enrollment).\n"
                  + " Expected:\n" + _infoEnrollment + "\n"
                  + " Found:\n" + _existingEnrollment);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "f75ff8f1c7dfb08c5b7a56550c84dd52", "bd4c669745e980a862264042d1dcbc10");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "course","student","enrollment");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `course`");
      _db.execSQL("DELETE FROM `student`");
      _db.execSQL("DELETE FROM `enrollment`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(CourseDao.class, CourseDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(StudentDao.class, StudentDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(EnrollmentDao.class, EnrollmentDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public CourseDao courseDao() {
    if (_courseDao != null) {
      return _courseDao;
    } else {
      synchronized(this) {
        if(_courseDao == null) {
          _courseDao = new CourseDao_Impl(this);
        }
        return _courseDao;
      }
    }
  }

  @Override
  public StudentDao studentDao() {
    if (_studentDao != null) {
      return _studentDao;
    } else {
      synchronized(this) {
        if(_studentDao == null) {
          _studentDao = new StudentDao_Impl(this);
        }
        return _studentDao;
      }
    }
  }

  @Override
  public EnrollmentDao enrollmentDao() {
    if (_enrollmentDao != null) {
      return _enrollmentDao;
    } else {
      synchronized(this) {
        if(_enrollmentDao == null) {
          _enrollmentDao = new EnrollmentDao_Impl(this);
        }
        return _enrollmentDao;
      }
    }
  }
}
