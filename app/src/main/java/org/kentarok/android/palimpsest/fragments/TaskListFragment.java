package org.kentarok.android.palimpsest.fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.kentarok.android.palimpsest.R;
import org.kentarok.android.palimpsest.models.Task;

public class TaskListFragment extends ListFragment {
    private ArrayAdapter<Task> adapter;
    private AbsListView listView;

    public static TaskListFragment newInstance() {
        TaskListFragment fragment = new TaskListFragment();
        return fragment;
    }

    public TaskListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayAdapter<Task> adapter = (new ArrayAdapter<Task>(getActivity(), R.layout.fragment_task_list, Task.taskList()){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                LayoutInflater inflater = LayoutInflater.from(getContext());
                View view = inflater.inflate(R.layout.fragment_task_list_item, parent, false);

                Task task = getItem(position);
                TextView textTitle = (TextView)view.findViewById(R.id.task_list_item_title);
                textTitle.setText(task.title);
                TextView textCurrentCount = (TextView)view.findViewById(R.id.task_list_item_current_count);
                textCurrentCount.setText(task.currentCount().toString());

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

    public void setEmptyText(CharSequence emptyText) {
        View emptyView = listView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }
}
