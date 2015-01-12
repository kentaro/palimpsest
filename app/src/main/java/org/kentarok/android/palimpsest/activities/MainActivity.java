package org.kentarok.android.palimpsest.activities;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.squareup.otto.Subscribe;

import org.kentarok.android.palimpsest.R;
import org.kentarok.android.palimpsest.consts.MenuType;
import org.kentarok.android.palimpsest.fragments.TaskFormFragment;
import org.kentarok.android.palimpsest.fragments.TaskListFragment;
import org.kentarok.android.palimpsest.models.Task;
import org.kentarok.android.palimpsest.utils.BusHolder;

public class MainActivity extends ActionBarActivity implements TaskListFragment.OnFragmentInteractionListener {
    MenuType menuType = MenuType.TASK_LIST;

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
        switch (this.menuType) {
            case TASK_LIST:
                getMenuInflater().inflate(R.menu.menu_main, menu);
                return true;
            case TASK_FORM:
                getMenuInflater().inflate(R.menu.menu_task_form, menu);
                return true;
            default:
                return true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                switchFragmentTo((Fragment)TaskFormFragment.newInstance(new Task()));
                return true;
            default:
                return true;
        }
    }

    public void refreshOptionsMenu(MenuType menuType) {
        this.menuType = menuType;
        invalidateOptionsMenu();
    }

    public void switchFragmentTo(Fragment fragment) {
        getFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
            refreshOptionsMenu(MenuType.TASK_LIST);
        } else {
            super.onBackPressed();
        }
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

    @Subscribe
    public void onTaskFormSubmitted(TaskFormFragment.OnSubmitted event) {
        getFragmentManager().popBackStack();
        refreshOptionsMenu(MenuType.TASK_LIST);
    }

    @Subscribe
    public void onTaskFormShown(TaskFormFragment.OnShown event) {
        refreshOptionsMenu(MenuType.TASK_FORM);
    }
}
