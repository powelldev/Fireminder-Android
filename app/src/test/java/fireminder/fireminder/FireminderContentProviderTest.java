package fireminder.fireminder;

import android.content.ContentResolver;
import android.database.Cursor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowContentResolver;

import java.util.UUID;


@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class FireminderContentProviderTest {

    private ContentResolver contentResolver;
    private ShadowContentResolver shadowContentResolver;
    private FireminderContentProvider fireminderContentProvider;

    @Before
    public void setup() {
        fireminderContentProvider = new FireminderContentProvider();
        contentResolver = RuntimeEnvironment.application.getContentResolver();
        shadowContentResolver = Shadows.shadowOf(contentResolver);

        fireminderContentProvider.onCreate();
        ShadowContentResolver.registerProvider(FireminderContentProvider.AUTHORITY, fireminderContentProvider);
    }

    @Test
    public void insert_happyPath() {
        AlarmTable.Alarm alarm = new AlarmTable.Alarm(new UUID(0,1), "title", "descript");
        contentResolver.insert(FireminderContentProvider.Table.Alarm.uri, alarm.toContentValues());
        Cursor cursor = shadowContentResolver.query(FireminderContentProvider.Table.Alarm.uri, null, null, null, null);
        while (cursor.moveToNext()) {
            AlarmTable.Alarm alarmFromCursor = new AlarmTable.Alarm(cursor);
            Assert.assertEquals(alarm, alarmFromCursor);
        }
    }

}
