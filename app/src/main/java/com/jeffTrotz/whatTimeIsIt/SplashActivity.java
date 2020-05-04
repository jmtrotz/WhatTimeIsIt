package com.jeffTrotz.whatTimeIsIt;

// Import required packages
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Controller class for the splash screen that is shown
 * when the application is opened
 * @author Jeffrey Trotz
 * @version 1.0
 */
public class SplashActivity extends AppCompatActivity
{
    private static final String TAG = "SplashActivity"; // Tag used when logging errors
    private static final int SLEEP_TIME = 2000;    // How long the splash screen will be shown

    /**
     * Called when the view is rendered
     * @param savedInstanceState Saved instance of the application
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // Call super and set view controlled by this class
        super.onCreate(savedInstanceState);

        // Create a new thread so the splash screen can be shown for a custom amount of time
        Thread timer = new Thread()
        {
            public void run()
            {
                // Try to pause the thread for 3 seconds
                try
                {
                    sleep(SLEEP_TIME);
                }

                // Catch any exceptions
                catch (InterruptedException interruptedException)
                {
                    Log.e(TAG, interruptedException.getMessage());
                }

                // Finish up by sending the user to the main activity
                finally
                {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };

        // Start the thread
        timer.start();
    }
}