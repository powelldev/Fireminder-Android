package fireminder.fireminder;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;

import java.util.UUID;

import static fireminder.fireminder.SqlTable.Type.*;

public class AlarmTable extends SqlTable {

  public static final String NAME = "Alarm";

  public AlarmTable(@NonNull String tableName) {
    super(tableName);
  }

  public interface Contract {
    String ID = "uuid";
    String TITLE = "title";
    String DESCRIPTION = "description";
  }

  public AlarmTable() {
    super(NAME);
    Column[] columns = {
        new Column(TEXT, Contract.ID),
        new Column(TEXT, Contract.TITLE),
        new Column(TEXT, Contract.DESCRIPTION),
    };
    setColumns(columns);
  }

  public static class Alarm {
    public final UUID id;
    public final String title;
    public final String description;

    public Alarm(UUID uuid, String title, String description) {
      this.id = uuid;
      this.title = title;
      this.description = description;
    }

    public Alarm(Cursor cursor) {
      this.id = UUID.fromString(cursor.getString(cursor.getColumnIndex(Contract.ID)));
      this.title = cursor.getString(cursor.getColumnIndex(Contract.TITLE));
      this.description = cursor.getString(cursor.getColumnIndex(Contract.DESCRIPTION));
    }


    public ContentValues toContentValues() {
      ContentValues cv = new ContentValues();
      cv.put(Contract.ID, id.toString());
      cv.put(Contract.TITLE, title);
      cv.put(Contract.DESCRIPTION, description);
      return cv;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      Alarm alarm = (Alarm) o;

      if (id != null ? !id.equals(alarm.id) : alarm.id != null) return false;
      if (title != null ? !title.equals(alarm.title) : alarm.title != null) return false;
      return !(description != null ? !description.equals(alarm.description) : alarm.description != null);

    }

    @Override
    public int hashCode() {
      int result = id != null ? id.hashCode() : 0;
      result = 31 * result + (title != null ? title.hashCode() : 0);
      result = 31 * result + (description != null ? description.hashCode() : 0);
      return result;
    }
  }

}