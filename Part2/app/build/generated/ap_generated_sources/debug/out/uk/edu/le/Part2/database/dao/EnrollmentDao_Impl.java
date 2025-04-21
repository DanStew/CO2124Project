package uk.edu.le.Part2.database.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import uk.edu.le.Part2.database.Course;
import uk.edu.le.Part2.database.Enrollment;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class EnrollmentDao_Impl implements EnrollmentDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Enrollment> __insertionAdapterOfEnrollment;

  private final EntityDeletionOrUpdateAdapter<Enrollment> __deletionAdapterOfEnrollment;

  private final SharedSQLiteStatement __preparedStmtOfRemoveStudentFromCourse;

  public EnrollmentDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfEnrollment = new EntityInsertionAdapter<Enrollment>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR IGNORE INTO `enrollment` (`studentId`,`courseId`) VALUES (?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final Enrollment entity) {
        statement.bindLong(1, entity.getStudentId());
        statement.bindLong(2, entity.getCourseId());
      }
    };
    this.__deletionAdapterOfEnrollment = new EntityDeletionOrUpdateAdapter<Enrollment>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `enrollment` WHERE `studentId` = ? AND `courseId` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final Enrollment entity) {
        statement.bindLong(1, entity.getStudentId());
        statement.bindLong(2, entity.getCourseId());
      }
    };
    this.__preparedStmtOfRemoveStudentFromCourse = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM enrollment WHERE courseId = ? AND studentId = ?";
        return _query;
      }
    };
  }

  @Override
  public void insert(final Enrollment enrollment) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfEnrollment.insert(enrollment);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Enrollment enrollment) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfEnrollment.handle(enrollment);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void removeStudentFromCourse(final int courseId, final int studentId) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfRemoveStudentFromCourse.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, courseId);
    _argIndex = 2;
    _stmt.bindLong(_argIndex, studentId);
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfRemoveStudentFromCourse.release(_stmt);
    }
  }

  @Override
  public Enrollment getEnrollment(final int studentId, final int courseId) {
    final String _sql = "SELECT * FROM enrollment WHERE studentId = ? AND courseId = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, studentId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, courseId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfStudentId = CursorUtil.getColumnIndexOrThrow(_cursor, "studentId");
      final int _cursorIndexOfCourseId = CursorUtil.getColumnIndexOrThrow(_cursor, "courseId");
      final Enrollment _result;
      if (_cursor.moveToFirst()) {
        final int _tmpStudentId;
        _tmpStudentId = _cursor.getInt(_cursorIndexOfStudentId);
        final int _tmpCourseId;
        _tmpCourseId = _cursor.getInt(_cursorIndexOfCourseId);
        _result = new Enrollment(_tmpStudentId,_tmpCourseId);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Enrollment> getAll() {
    final String _sql = "SELECT * FROM enrollment";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfStudentId = CursorUtil.getColumnIndexOrThrow(_cursor, "studentId");
      final int _cursorIndexOfCourseId = CursorUtil.getColumnIndexOrThrow(_cursor, "courseId");
      final List<Enrollment> _result = new ArrayList<Enrollment>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Enrollment _item;
        final int _tmpStudentId;
        _tmpStudentId = _cursor.getInt(_cursorIndexOfStudentId);
        final int _tmpCourseId;
        _tmpCourseId = _cursor.getInt(_cursorIndexOfCourseId);
        _item = new Enrollment(_tmpStudentId,_tmpCourseId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<List<Course>> getCoursesForStudent(final long studentId) {
    final String _sql = "SELECT * FROM Course WHERE courseId IN (SELECT courseId FROM Enrollment WHERE studentId = ?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, studentId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"Course",
        "Enrollment"}, true, new Callable<List<Course>>() {
      @Override
      @Nullable
      public List<Course> call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
          try {
            final int _cursorIndexOfCourseId = CursorUtil.getColumnIndexOrThrow(_cursor, "courseId");
            final int _cursorIndexOfCourseCode = CursorUtil.getColumnIndexOrThrow(_cursor, "courseCode");
            final int _cursorIndexOfCourseName = CursorUtil.getColumnIndexOrThrow(_cursor, "courseName");
            final int _cursorIndexOfLecturerName = CursorUtil.getColumnIndexOrThrow(_cursor, "lecturerName");
            final List<Course> _result = new ArrayList<Course>(_cursor.getCount());
            while (_cursor.moveToNext()) {
              final Course _item;
              _item = new Course();
              final int _tmpCourseId;
              _tmpCourseId = _cursor.getInt(_cursorIndexOfCourseId);
              _item.setCourseId(_tmpCourseId);
              final String _tmpCourseCode;
              if (_cursor.isNull(_cursorIndexOfCourseCode)) {
                _tmpCourseCode = null;
              } else {
                _tmpCourseCode = _cursor.getString(_cursorIndexOfCourseCode);
              }
              _item.setCourseCode(_tmpCourseCode);
              final String _tmpCourseName;
              if (_cursor.isNull(_cursorIndexOfCourseName)) {
                _tmpCourseName = null;
              } else {
                _tmpCourseName = _cursor.getString(_cursorIndexOfCourseName);
              }
              _item.setCourseName(_tmpCourseName);
              final String _tmpLecturerName;
              if (_cursor.isNull(_cursorIndexOfLecturerName)) {
                _tmpLecturerName = null;
              } else {
                _tmpLecturerName = _cursor.getString(_cursorIndexOfLecturerName);
              }
              _item.setLecturerName(_tmpLecturerName);
              _result.add(_item);
            }
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            _cursor.close();
          }
        } finally {
          __db.endTransaction();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
