package com.jeffTrotz.whatTimeIsIt;

// Import required packages
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;

/**
 * Controller class for the activity_main view
 * Created for the Sealing Technologies interview assignment
 * @author Jeffrey Trotz
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity
{
    private RecyclerViewAdapter mAdapter;   // View adapter for the recycler view
    private LinkedHashMap<String, String> mData;  // Stores the city name/current time at that city

    /**
     * Called when the view is rendered
     * @param savedInstanceState Saved instance of the application
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // Call super and set view controlled by this class
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the time/date according to the user's phone
        Date myTime = Calendar.getInstance().getTime();

        // Initialize and load hash map with keys (label indicating the city)
        // and values (current time at that city)
        mData = new LinkedHashMap<>();
        mData.put(getString(R.string.my_time_label), this.formatDate(myTime));
        mData.put(getString(R.string.honolulu_time_label), this.getDate(getString(R.string.honolulu_time_zone)));
        mData.put(getString(R.string.los_angeles_time_label), this.getDate(getString(R.string.los_angeles_time_zone)));
        mData.put(getString(R.string.santiago_time_label), this.getDate(getString(R.string.santiago_time_zone)));
        mData.put(getString(R.string.london_time_label), this.getDate(getString(R.string.london_time_zone)));
        mData.put(getString(R.string.cairo_time_label), this.getDate(getString(R.string.cairo_time_zone)));
        mData.put(getString(R.string.moscow_time_label), this.getDate(getString(R.string.moscow_time_zone)));
        mData.put(getString(R.string.hong_kong_time_label), this.getDate(getString(R.string.hong_kong_time_zone)));
        mData.put(getString(R.string.sydney_time_label), this.getDate(getString(R.string.sydney_time_zone)));
        mData.put(getString(R.string.tokyo_time_label), this.getDate(getString(R.string.tokyo_time_zone)));

        // Set up recycler view and pass in the HashMap
        RecyclerView recyclerView = findViewById(R.id.citiesRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RecyclerViewAdapter(this, mData);
        recyclerView.setAdapter(mAdapter);
    }

    /**
     * Gets the date/time in ISO 8601 standard format using the new
     * Java ZonedDateTime utility, then converts it to a date object,
     * formats it so it looks nice, then returns it.
     * @param timeZone Time zone we want the current time/date for
     * @return Returns the time/date as a String after being formatted by
     * the formatDate() method.
     */
    private String getDate(String timeZone)
    {
        // Get the zone ID for the location we want the time of and get the time
        OffsetDateTime offsetDateTime = OffsetDateTime.now();
        ZoneId zone = ZoneId.of(timeZone);
        ZonedDateTime zonedDateTime = offsetDateTime.atZoneSameInstant(zone);

        // To convert to a date object without losing the time zone, we must calculate
        // how many milliseconds offset it is from Unix time
        long offsetMillis = ZoneOffset.from(zonedDateTime).getTotalSeconds() * 1000;
        long isoMillis = zonedDateTime.toInstant().toEpochMilli();

        // Convert to a date object, format, and return
        Date date = new Date(isoMillis + offsetMillis);
        return formatDate(date);
    }

    /**
     * Formats Date objects into something a little more eye pleasing
     * @param dateToFormat Date object to be formatted
     * @return Returns the formatted date object as String
     */
    private String formatDate(Date dateToFormat)
    {
        // Convert to a date object, format, and return
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a MM/dd/yy");
        return dateFormat.format(dateToFormat);
    }
}