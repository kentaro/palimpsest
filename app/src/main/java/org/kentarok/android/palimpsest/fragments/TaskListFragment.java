package org.kentarok.android.palimpsest.fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import org.kentarok.android.palimpsest.R;
import org.kentarok.android.palimpsest.models.Task;
import org.kentarok.android.palimpsest.utils.BusHolder;

public class TaskListFragment extends ListFragment {
    public ArrayAdapter<Task> adapter;

    public static TaskListFragment newInstance() {
        TaskListFragment fragment = new TaskListFragment();
        return fragment;
    }

    public TaskListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.adapter = (new ArrayAdapter<Task>(getActivity(), R.layout.fragment_task_list, Task.taskList()){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                LayoutInflater inflater = LayoutInflater.from(getContext());
                final View view = inflater.inflate(R.layout.fragment_task_list_item, parent, false);

                final Task task = getItem(position);
                TextView textTitle = (TextView)view.findViewById(R.id.task_list_item_title);
                textTitle.setText(task.title);
                TextView textCurrentCount = (TextView)view.findViewById(R.id.task_list_item_current_count);
                textCurrentCount.setText(task.currentCount().toString());

                CheckBox checkBox = (CheckBox)view.findViewById(R.id.task_list_item_checkbox);
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        task.done();
                        view.animate().setDuration(1000).alpha(0).withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                adapter.remove(task);
                            }
                        });
                    }
                });

                return view;
            }
        });
        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        BusHolder.getInstance().register(this);
    }

    @Override
    public void onStop() {
        BusHolder.getInstance().unregister(this);
        super.onStop();
    }

    @Subscribe
    public void onTaskCreated(TaskFormFragment.OnCreatedEvent event) {
        this.adapter.insert(event.task, 0);
        this.adapter.notifyDataSetInvalidated();
    }

    @Subscribe
    public void onTaskUpdated(TaskFormFragment.OnUpdatedEvent event) {
        this.adapter.notifyDataSetInvalidated();
    }

    @Override
    public void onListItemClick(ListView listView, View view, int pos, long id) {
        Task task = this.adapter.getItem(pos);
        BusHolder.getInstance().post(new OnListItemClickedEvent(task));
    }

    public final class OnListItemClickedEvent {
        public Task task;
        public OnListItemClickedEvent(Task task) {
            this.task = task;
        }
    }
}
