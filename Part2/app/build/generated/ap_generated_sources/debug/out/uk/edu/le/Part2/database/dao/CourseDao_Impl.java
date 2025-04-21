package uk.edu.le.Part2.database.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.LongSparseArray;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.RelationUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import uk.edu.le.Part2.database.Course;
import uk.edu.le.Part2.database.CourseWithStudents;
import uk.edu.le.Part2.database.Student;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class CourseDao_Impl implements CourseDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Course> __insertionAdapterOfCourse;

  private final EntityDeletionOrUpdateAdapter<Course> __deletionAdapterOfCourse;

  private final EntityDeletionOrUpdateAdapter<Course> __updateAdapterOfCourse;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public CourseDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCourse = new EntityInsertionAdapter<Course>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR IGNORE INTO `course` (`courseId`,`courseCode`,`courseName`,`lecturerName`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Course entity) {
        statement.bindLong(1, entity.getCourseId());
        if (entity.getCourseCode() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getCourseCode());
        }
        if (entity.getCourseName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getCourseName());
        }
        if (entity.getLecturerName() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getLecturerName());
        }
      }
    };
    this.__deletionAdapterOfCourse = new EntityDeletionOrUpdateAdapter<Course>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `course` WHERE `courseId` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Course entity) {
        statement.bindLong(1, entity.getCourseId());
      }
    };
    this.__updateAdapterOfCourse = new EntityDeletionOrUpdateAdapter<Course>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `course` SET `courseId` = ?,`courseCode` = ?,`courseName` = ?,`lecturerName` = ? WHERE `courseId` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Course entity) {
        statement.bindLong(1, entity.getCourseId());
        if (entity.getCourseCode() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getCourseCode());
        }
        if (entity.getCourseName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getCourseName());
        }
        if (entity.getLecturerName() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getLecturerName());
        }
        statement.bindLong(5, entity.getCourseId());
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM course";
        return _query;
      }
    };
  }

  @Override
  public long insert(final Course course) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfCourse.insertAndReturnId(course);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Course course) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfCourse.handle(course);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Course course) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfCourse.handle(course);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAll() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfDeleteAll.release(_stmt);
    }
  }

  @Override
  public LiveData<List<Course>> getCourseList() {
    final String _sql = "SELECT * FROM course ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"course"}, false, new Callable<List<Course>>() {
      @Override
      @Nullable
      public List<Course> call() throws Exception {
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
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<CourseWithStudents> getCourseWithStudents(final int courseId) {
    final String _sql = "SELECT * FROM course WHERE courseId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, courseId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"enrollment", "student",
        "course"}, true, new Callable<CourseWithStudents>() {
      @Override
      @Nullable
      public CourseWithStudents call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfCourseId = CursorUtil.getColumnIndexOrThrow(_cursor, "courseId");
            final int _cursorIndexOfCourseCode = CursorUtil.getColumnIndexOrThrow(_cursor, "courseCode");
            final int _cursorIndexOfCourseName = CursorUtil.getColumnIndexOrThrow(_cursor, "courseName");
            final int _cursorIndexOfLecturerName = CursorUtil.getColumnIndexOrThrow(_cursor, "lecturerName");
            final LongSparseArray<ArrayList<Student>> _collectionStudents = new LongSparseArray<ArrayList<Student>>();
            while (_cursor.moveToNext()) {
              final Long _tmpKey;
              if (_cursor.isNull(_cursorIndexOfCourseId)) {
                _tmpKey = null;
              } else {
                _tmpKey = _cursor.getLong(_cursorIndexOfCourseId);
              }
              if (_tmpKey != null) {
                if (!_collectionStudents.containsKey(_tmpKey)) {
                  _collectionStudents.put(_tmpKey, new ArrayList<Student>());
                }
              }
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshipstudentAsukEduLePart2DatabaseStudent(_collectionStudents);
            final CourseWithStudents _result;
            if (_cursor.moveToFirst()) {
              final Course _tmpCourse;
              if (!(_cursor.isNull(_cursorIndexOfCourseId) && _cursor.isNull(_cursorIndexOfCourseCode) && _cursor.isNull(_cursorIndexOfCourseName) && _cursor.isNull(_cursorIndexOfLecturerName))) {
                _tmpCourse = new Course();
                final int _tmpCourseId;
                _tmpCourseId = _cursor.getInt(_cursorIndexOfCourseId);
                _tmpCourse.setCourseId(_tmpCourseId);
                final String _tmpCourseCode;
                if (_cursor.isNull(_cursorIndexOfCourseCode)) {
                  _tmpCourseCode = null;
                } else {
                  _tmpCourseCode = _cursor.getString(_cursorIndexOfCourseCode);
                }
                _tmpCourse.setCourseCode(_tmpCourseCode);
                final String _tmpCourseName;
                if (_cursor.isNull(_cursorIndexOfCourseName)) {
                  _tmpCourseName = null;
                } else {
                  _tmpCourseName = _cursor.getString(_cursorIndexOfCourseName);
                }
                _tmpCourse.setCourseName(_tmpCourseName);
                final String _tmpLecturerName;
                if (_cursor.isNull(_cursorIndexOfLecturerName)) {
                  _tmpLecturerName = null;
                } else {
                  _tmpLecturerName = _cursor.getString(_cursorIndexOfLecturerName);
                }
                _tmpCourse.setLecturerName(_tmpLecturerName);
              } else {
                _tmpCourse = null;
              }
              final ArrayList<Student> _tmpStudentsCollection;
              final Long _tmpKey_1;
              if (_cursor.isNull(_cursorIndexOfCourseId)) {
                _tmpKey_1 = null;
              } else {
                _tmpKey_1 = _cursor.getLong(_cursorIndexOfCourseId);
              }
              if (_tmpKey_1 != null) {
                _tmpStudentsCollection = _collectionStudents.get(_tmpKey_1);
              } else {
                _tmpStudentsCollection = new ArrayList<Student>();
              }
              _result = new CourseWithStudents();
              _result.setCourse(_tmpCourse);
              _result.setStudents(_tmpStudentsCollection);
            } else {
              _result = null;
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

  private void __fetchRelationshipstudentAsukEduLePart2DatabaseStudent(
      @NonNull final LongSparseArray<ArrayList<Student>> _map) {
    if (_map.isEmpty()) {
      return;
    }
    if (_map.size() > RoomDatabase.MAX_BIND_PARAMETER_CNT) {
      RelationUtil.recursiveFetchLongSparseArray(_map, true, (map) -> {
        __fetchRelationshipstudentAsukEduLePart2DatabaseStudent(map);
        return Unit.INSTANCE;
      });
      return;
    }
    final StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT `student`.`studentId` AS `studentId`,`student`.`fullName` AS `fullName`,`student`.`email` AS `email`,`student`.`matricNum` AS `matricNum`,`student`.`username` AS `username`,_junction.`courseId` FROM `enrollment` AS _junction INNER JOIN `student` ON (_junction.`studentId` = `student`.`studentId`) WHERE _junction.`courseId` IN (");
    final int _inputSize = _map.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _stmt = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (int i = 0; i < _map.size(); i++) {
      final long _item = _map.keyAt(i);
      _stmt.bindLong(_argIndex, _item);
      _argIndex++;
    }
    final Cursor _cursor = DBUtil.query(__db, _stmt, false, null);
    try {
      // _junction.courseId;
      final int _itemKeyIndex = 5;
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _cursorIndexOfStudentId = 0;
      final int _cursorIndexOfFullName = 1;
      final int _cursorIndexOfEmail = 2;
      final int _cursorIndexOfMatricNum = 3;
      final int _cursorIndexOfUsername = 4;
      while (_cursor.moveToNext()) {
        final long _tmpKey;
        _tmpKey = _cursor.getLong(_itemKeyIndex);
        final ArrayList<Student> _tmpRelation = _map.get(_tmpKey);
        if (_tmpRelation != null) {
          final Student _item_1;
          _item_1 = new Student();
          final int _tmpStudentId;
          _tmpStudentId = _cursor.getInt(_cursorIndexOfStudentId);
          _item_1.setStudentId(_tmpStudentId);
          final String _tmpFullName;
          if (_cursor.isNull(_cursorIndexOfFullName)) {
            _tmpFullName = null;
          } else {
            _tmpFullName = _cursor.getString(_cursorIndexOfFullName);
          }
          _item_1.setFullName(_tmpFullName);
          final String _tmpEmail;
          if (_cursor.isNull(_cursorIndexOfEmail)) {
            _tmpEmail = null;
          } else {
            _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
          }
          _item_1.setEmail(_tmpEmail);
          final int _tmpMatricNum;
          _tmpMatricNum = _cursor.getInt(_cursorIndexOfMatricNum);
          _item_1.setMatricNum(_tmpMatricNum);
          final String _tmpUsername;
          if (_cursor.isNull(_cursorIndexOfUsername)) {
            _tmpUsername = null;
          } else {
            _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
          }
          _item_1.setUsername(_tmpUsername);
          _tmpRelation.add(_item_1);
        }
      }
    } finally {
      _cursor.close();
    }
  }
}
