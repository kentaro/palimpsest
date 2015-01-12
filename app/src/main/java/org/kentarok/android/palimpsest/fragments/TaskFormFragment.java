package org.kentarok.android.palimpsest.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import org.kentarok.android.palimpsest.R;
import org.kentarok.android.palimpsest.models.Task;
import org.kentarok.android.palimpsest.utils.BusHolder;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class TaskFormFragment extends Fragment {
    public Task task;

    public static TaskFormFragment newInstance(Task task) {
        TaskFormFragment fragment = new TaskFormFragment();
        fragment.task = task;

        return fragment;
    }

    public TaskFormFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_form, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @OnClick(R.id.task_form_button_submit)
    public void submit() {
        EditText titleEdit = (EditText)getView().findViewById(R.id.task_form_edit_title);
        String title = titleEdit.getText().toString();
        EditText countEdit = (EditText)getView().findViewById(R.id.task_form_edit_count);
        Integer count = Integer.parseInt(countEdit.getText().toString());

        task.title = title;
        task.count = count;
        task.save();

        BusHolder.getInstance().post(new OnSubmitted());
    }

    public class OnSubmitted {
    }
}
