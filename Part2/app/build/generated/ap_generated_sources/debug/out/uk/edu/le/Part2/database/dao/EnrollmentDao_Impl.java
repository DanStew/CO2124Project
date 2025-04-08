package uk.edu.le.Part2.database.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;
import uk.edu.le.Part2.database.Enrollment;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class EnrollmentDao_Impl implements EnrollmentDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Enrollment> __insertionAdapterOfEnrollment;

  private final SharedSQLiteStatement __preparedStmtOfRemoveStudentFromCourse;

  public EnrollmentDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfEnrollment = new EntityInsertionAdapter<Enrollment>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `enrollment` (`studentId`,`courseId`) VALUES (?,?)";
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
  public void enrollStudent(final Enrollment enrollment) {
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
