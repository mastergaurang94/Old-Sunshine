package com.example.abc.sunshine;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {
    private final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ForecastFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
            return true;
        }
        if (id == R.id.action_view_map) {
            showMap();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Shows preferred location on a map
    private void showMap() {
        // get preferred location
        String location = PreferenceManager.getDefaultSharedPreferences(this)
                .getString(getString(R.string.pref_location_key),
                        getString(R.string.pref_location_default));

        final String SCHEME = "geo:0,0?";
        final String QUERY_PARAM = "q";
        // build URI
        Uri builtUri = Uri.parse(SCHEME).buildUpon()
                .appendQueryParameter(QUERY_PARAM, location)
                .build();

        // start map activity
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(builtUri);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else
            Log.d(LOG_TAG, "Couldn't call " + location + ", no location found");
    }
}
