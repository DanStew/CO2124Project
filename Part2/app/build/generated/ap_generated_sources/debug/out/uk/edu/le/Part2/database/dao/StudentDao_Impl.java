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
import uk.edu.le.Part2.database.Student;
import uk.edu.le.Part2.database.StudentWithCourses;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class StudentDao_Impl implements StudentDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Student> __insertionAdapterOfStudent;

  private final EntityDeletionOrUpdateAdapter<Student> __deletionAdapterOfStudent;

  private final EntityDeletionOrUpdateAdapter<Student> __updateAdapterOfStudent;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public StudentDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfStudent = new EntityInsertionAdapter<Student>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR IGNORE INTO `student` (`studentId`,`fullName`,`email`,`matricNum`,`username`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Student entity) {
        statement.bindLong(1, entity.getStudentId());
        if (entity.getFullName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getFullName());
        }
        if (entity.getEmail() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getEmail());
        }
        statement.bindLong(4, entity.getMatricNum());
        if (entity.getUsername() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getUsername());
        }
      }
    };
    this.__deletionAdapterOfStudent = new EntityDeletionOrUpdateAdapter<Student>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `student` WHERE `studentId` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Student entity) {
        statement.bindLong(1, entity.getStudentId());
      }
    };
    this.__updateAdapterOfStudent = new EntityDeletionOrUpdateAdapter<Student>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `student` SET `studentId` = ?,`fullName` = ?,`email` = ?,`matricNum` = ?,`username` = ? WHERE `studentId` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Student entity) {
        statement.bindLong(1, entity.getStudentId());
        if (entity.getFullName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getFullName());
        }
        if (entity.getEmail() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getEmail());
        }
        statement.bindLong(4, entity.getMatricNum());
        if (entity.getUsername() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getUsername());
        }
        statement.bindLong(6, entity.getStudentId());
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM student";
        return _query;
      }
    };
  }

  @Override
  public long insert(final Student student) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfStudent.insertAndReturnId(student);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Student student) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfStudent.handle(student);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Student student) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfStudent.handle(student);
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
  public LiveData<List<Student>> getAllStudents() {
    final String _sql = "SELECT * FROM student";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"student"}, false, new Callable<List<Student>>() {
      @Override
      @Nullable
      public List<Student> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfStudentId = CursorUtil.getColumnIndexOrThrow(_cursor, "studentId");
          final int _cursorIndexOfFullName = CursorUtil.getColumnIndexOrThrow(_cursor, "fullName");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfMatricNum = CursorUtil.getColumnIndexOrThrow(_cursor, "matricNum");
          final int _cursorIndexOfUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "username");
          final List<Student> _result = new ArrayList<Student>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Student _item;
            _item = new Student();
            final int _tmpStudentId;
            _tmpStudentId = _cursor.getInt(_cursorIndexOfStudentId);
            _item.setStudentId(_tmpStudentId);
            final String _tmpFullName;
            if (_cursor.isNull(_cursorIndexOfFullName)) {
              _tmpFullName = null;
            } else {
              _tmpFullName = _cursor.getString(_cursorIndexOfFullName);
            }
            _item.setFullName(_tmpFullName);
            final String _tmpEmail;
            if (_cursor.isNull(_cursorIndexOfEmail)) {
              _tmpEmail = null;
            } else {
              _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            }
            _item.setEmail(_tmpEmail);
            final int _tmpMatricNum;
            _tmpMatricNum = _cursor.getInt(_cursorIndexOfMatricNum);
            _item.setMatricNum(_tmpMatricNum);
            final String _tmpUsername;
            if (_cursor.isNull(_cursorIndexOfUsername)) {
              _tmpUsername = null;
            } else {
              _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
            }
            _item.setUsername(_tmpUsername);
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
  public LiveData<StudentWithCourses> getStudentWithCourses(final int studentId) {
    final String _sql = "SELECT * FROM student WHERE studentId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, studentId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"enrollment", "course",
        "student"}, true, new Callable<StudentWithCourses>() {
      @Override
      @Nullable
      public StudentWithCourses call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfStudentId = CursorUtil.getColumnIndexOrThrow(_cursor, "studentId");
            final int _cursorIndexOfFullName = CursorUtil.getColumnIndexOrThrow(_cursor, "fullName");
            final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
            final int _cursorIndexOfMatricNum = CursorUtil.getColumnIndexOrThrow(_cursor, "matricNum");
            final int _cursorIndexOfUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "username");
            final LongSparseArray<ArrayList<Course>> _collectionCourses = new LongSparseArray<ArrayList<Course>>();
            while (_cursor.moveToNext()) {
              final Long _tmpKey;
              if (_cursor.isNull(_cursorIndexOfStudentId)) {
                _tmpKey = null;
              } else {
                _tmpKey = _cursor.getLong(_cursorIndexOfStudentId);
              }
              if (_tmpKey != null) {
                if (!_collectionCourses.containsKey(_tmpKey)) {
                  _collectionCourses.put(_tmpKey, new ArrayList<Course>());
                }
              }
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshipcourseAsukEduLePart2DatabaseCourse(_collectionCourses);
            final StudentWithCourses _result;
            if (_cursor.moveToFirst()) {
              final Student _tmpStudent;
              if (!(_cursor.isNull(_cursorIndexOfStudentId) && _cursor.isNull(_cursorIndexOfFullName) && _cursor.isNull(_cursorIndexOfEmail) && _cursor.isNull(_cursorIndexOfMatricNum) && _cursor.isNull(_cursorIndexOfUsername))) {
                _tmpStudent = new Student();
                final int _tmpStudentId;
                _tmpStudentId = _cursor.getInt(_cursorIndexOfStudentId);
                _tmpStudent.setStudentId(_tmpStudentId);
                final String _tmpFullName;
                if (_cursor.isNull(_cursorIndexOfFullName)) {
                  _tmpFullName = null;
                } else {
                  _tmpFullName = _cursor.getString(_cursorIndexOfFullName);
                }
                _tmpStudent.setFullName(_tmpFullName);
                final String _tmpEmail;
                if (_cursor.isNull(_cursorIndexOfEmail)) {
                  _tmpEmail = null;
                } else {
                  _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
                }
                _tmpStudent.setEmail(_tmpEmail);
                final int _tmpMatricNum;
                _tmpMatricNum = _cursor.getInt(_cursorIndexOfMatricNum);
                _tmpStudent.setMatricNum(_tmpMatricNum);
                final String _tmpUsername;
                if (_cursor.isNull(_cursorIndexOfUsername)) {
                  _tmpUsername = null;
                } else {
                  _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
                }
                _tmpStudent.setUsername(_tmpUsername);
              } else {
                _tmpStudent = null;
              }
              final ArrayList<Course> _tmpCoursesCollection;
              final Long _tmpKey_1;
              if (_cursor.isNull(_cursorIndexOfStudentId)) {
                _tmpKey_1 = null;
              } else {
                _tmpKey_1 = _cursor.getLong(_cursorIndexOfStudentId);
              }
              if (_tmpKey_1 != null) {
                _tmpCoursesCollection = _collectionCourses.get(_tmpKey_1);
              } else {
                _tmpCoursesCollection = new ArrayList<Course>();
              }
              _result = new StudentWithCourses();
              _result.setStudent(_tmpStudent);
              _result.setCourses(_tmpCoursesCollection);
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

  @Override
  public LiveData<Student> getStudentById(final int studentId) {
    final String _sql = "SELECT * FROM Student WHERE studentId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, studentId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"Student"}, false, new Callable<Student>() {
      @Override
      @Nullable
      public Student call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfStudentId = CursorUtil.getColumnIndexOrThrow(_cursor, "studentId");
          final int _cursorIndexOfFullName = CursorUtil.getColumnIndexOrThrow(_cursor, "fullName");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfMatricNum = CursorUtil.getColumnIndexOrThrow(_cursor, "matricNum");
          final int _cursorIndexOfUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "username");
          final Student _result;
          if (_cursor.moveToFirst()) {
            _result = new Student();
            final int _tmpStudentId;
            _tmpStudentId = _cursor.getInt(_cursorIndexOfStudentId);
            _result.setStudentId(_tmpStudentId);
            final String _tmpFullName;
            if (_cursor.isNull(_cursorIndexOfFullName)) {
              _tmpFullName = null;
            } else {
              _tmpFullName = _cursor.getString(_cursorIndexOfFullName);
            }
            _result.setFullName(_tmpFullName);
            final String _tmpEmail;
            if (_cursor.isNull(_cursorIndexOfEmail)) {
              _tmpEmail = null;
            } else {
              _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            }
            _result.setEmail(_tmpEmail);
            final int _tmpMatricNum;
            _tmpMatricNum = _cursor.getInt(_cursorIndexOfMatricNum);
            _result.setMatricNum(_tmpMatricNum);
            final String _tmpUsername;
            if (_cursor.isNull(_cursorIndexOfUsername)) {
              _tmpUsername = null;
            } else {
              _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
            }
            _result.setUsername(_tmpUsername);
          } else {
            _result = null;
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }

  private void __fetchRelationshipcourseAsukEduLePart2DatabaseCourse(
      @NonNull final LongSparseArray<ArrayList<Course>> _map) {
    if (_map.isEmpty()) {
      return;
    }
    if (_map.size() > RoomDatabase.MAX_BIND_PARAMETER_CNT) {
      RelationUtil.recursiveFetchLongSparseArray(_map, true, (map) -> {
        __fetchRelationshipcourseAsukEduLePart2DatabaseCourse(map);
        return Unit.INSTANCE;
      });
      return;
    }
    final StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT `course`.`courseId` AS `courseId`,`course`.`courseCode` AS `courseCode`,`course`.`courseName` AS `courseName`,`course`.`lecturerName` AS `lecturerName`,_junction.`studentId` FROM `enrollment` AS _junction INNER JOIN `course` ON (_junction.`courseId` = `course`.`courseId`) WHERE _junction.`studentId` IN (");
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
      // _junction.studentId;
      final int _itemKeyIndex = 4;
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _cursorIndexOfCourseId = 0;
      final int _cursorIndexOfCourseCode = 1;
      final int _cursorIndexOfCourseName = 2;
      final int _cursorIndexOfLecturerName = 3;
      while (_cursor.moveToNext()) {
        final long _tmpKey;
        _tmpKey = _cursor.getLong(_itemKeyIndex);
        final ArrayList<Course> _tmpRelation = _map.get(_tmpKey);
        if (_tmpRelation != null) {
          final Course _item_1;
          _item_1 = new Course();
          final int _tmpCourseId;
          _tmpCourseId = _cursor.getInt(_cursorIndexOfCourseId);
          _item_1.setCourseId(_tmpCourseId);
          final String _tmpCourseCode;
          if (_cursor.isNull(_cursorIndexOfCourseCode)) {
            _tmpCourseCode = null;
          } else {
            _tmpCourseCode = _cursor.getString(_cursorIndexOfCourseCode);
          }
          _item_1.setCourseCode(_tmpCourseCode);
          final String _tmpCourseName;
          if (_cursor.isNull(_cursorIndexOfCourseName)) {
            _tmpCourseName = null;
          } else {
            _tmpCourseName = _cursor.getString(_cursorIndexOfCourseName);
          }
          _item_1.setCourseName(_tmpCourseName);
          final String _tmpLecturerName;
          if (_cursor.isNull(_cursorIndexOfLecturerName)) {
            _tmpLecturerName = null;
          } else {
            _tmpLecturerName = _cursor.getString(_cursorIndexOfLecturerName);
          }
          _item_1.setLecturerName(_tmpLecturerName);
          _tmpRelation.add(_item_1);
        }
      }
    } finally {
      _cursor.close();
    }
  }
}
