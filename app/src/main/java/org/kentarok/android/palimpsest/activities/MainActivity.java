package org.kentarok.android.palimpsest.activities;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import org.kentarok.android.palimpsest.R;
import org.kentarok.android.palimpsest.fragments.TaskFormFragment;
import org.kentarok.android.palimpsest.fragments.TaskListFragment;
import org.kentarok.android.palimpsest.utils.BusHolder;

public class MainActivity extends ActionBarActivity implements TaskListFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, TaskListFragment.newInstance("aa", "bb"))
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                switchFragmentTo((Fragment)TaskFormFragment.newInstance());
                return true;
            default:
                return true;
        }
    }

    public void switchFragmentTo(Fragment fragment) {
        getFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    @Override
    public void onResume() {
       super.onResume();
        BusHolder.getInstance().register(this);
    }

    @Override
    public void onPause() {
        BusHolder.getInstance().unregister(this);
        super.onPause();
    }

    @Override
    public void onFragmentInteraction(String id) {
    }
}
