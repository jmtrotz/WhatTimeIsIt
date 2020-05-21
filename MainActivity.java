package com.jeffTrotz.whatTimeIsIt;

// Import required packages
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
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
 * @version 2.0
 */
public class MainActivity extends AppCompatActivity
{
    private RecyclerView mRecyclerView; // Recycler view in the UI that displays cards with data
    private static Handler mHandler; // Handler used to pass messages between threads
    private static final int UPDATE_UI = 0; // Identifier for the handler messages
    private static final String TAG = MainActivity.class.getName(); // Tag used for logging

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

        // Set the layout for the recycler view
        mRecyclerView = findViewById(R.id.citiesRV);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        // Start a process running on the UI thread to update the times shown in each card
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                // Initialize the handler and start listening for messages
                mHandler = new Handler()
                {
                    @Override
                    public void handleMessage(Message message)
                    {
                        // If the correct message is received, update the cards shown in the recycler view
                        if (message.what == UPDATE_UI)
                        {
                            mRecyclerView.setAdapter((RecyclerViewAdapter) message.obj);
                        }
                    }
                };
            }
        });

        // Start a background thread to update the date/time
        Thread updateTimeThread = new Thread()
        {
            @Override
            public void run()
            {
                // Never ending loop to keep the thread running
                while (true)
                {
                    // Call method to update the time
                    updateTime();

                    // Try to pause the thread for 20 seconds before re-updating the date/time
                    try
                    {
                        sleep(20000);
                    }

                    // Catch any exceptions
                    catch (InterruptedException interruptedException)
                    {
                        Log.e(TAG, interruptedException.getMessage());
                    }
                }
            }
        };

        // Start the thread
        updateTimeThread.start();
    }

    /**
     * This method gets the current date/time according to the user's device, then uses that to
     * calculate the date/time at various locations around the world. After that it loads them into
     * a LinkedHashMap, creates a new recycler view adapter and feeds in the LinkedHasHMap, and
     * finally uses a handler to pass it to the process running on the UI thread to update the UI.
     */
    private void updateTime()
    {
        // Get the time/date according to the user's phone
        Date myTime = Calendar.getInstance().getTime();

        // Initialize and load hash map with keys (label indicating the city) and values (current time at that city)
        LinkedHashMap<String, String> timeData = new LinkedHashMap<>();
        timeData.put(getString(R.string.my_time_label), this.formatDate(myTime));
        timeData.put(getString(R.string.honolulu_time_label), this.getDate(getString(R.string.honolulu_time_zone)));
        timeData.put(getString(R.string.los_angeles_time_label), this.getDate(getString(R.string.los_angeles_time_zone)));
        timeData.put(getString(R.string.santiago_time_label), this.getDate(getString(R.string.santiago_time_zone)));
        timeData.put(getString(R.string.london_time_label), this.getDate(getString(R.string.london_time_zone)));
        timeData.put(getString(R.string.cairo_time_label), this.getDate(getString(R.string.cairo_time_zone)));
        timeData.put(getString(R.string.moscow_time_label), this.getDate(getString(R.string.moscow_time_zone)));
        timeData.put(getString(R.string.hong_kong_time_label), this.getDate(getString(R.string.hong_kong_time_zone)));
        timeData.put(getString(R.string.sydney_time_label), this.getDate(getString(R.string.sydney_time_zone)));
        timeData.put(getString(R.string.tokyo_time_label), this.getDate(getString(R.string.tokyo_time_zone)));

        // Create and set a new Recycler View adapter, passing in the LinkedHashMap
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getApplicationContext(), timeData);

        // Create and send a new message to the process running on the UI thread to update the user interface
        Message message = mHandler.obtainMessage();
        message.what = UPDATE_UI;
        message.obj = adapter;
        mHandler.sendMessage(message);
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