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

import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Optional;

public class TaskFormFragment extends Fragment {
    Task task;

    @InjectView(R.id.task_form_edit_title)
    EditText titleEdit;
    @InjectView(R.id.task_form_edit_count)
    EditText countEdit;

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
        int resource = task.isEmpty() ? R.layout.fragment_task_form : R.layout.fragment_task_form_with_task;
        View view = inflater.inflate(resource, container, false);

        // 便利なものたち
        ButterKnife.inject(this, view);
        BusHolder.getInstance().post(new OnShownEvent());

        titleEdit.setText(this.task.title);
        countEdit.setText(this.task.count != null ? this.task.count.toString() : null);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Optional
    @OnClick({
            R.id.task_form_button_submit_create,
            R.id.task_form_button_submit_update
    })
    public void submit() {
        Boolean isNewObject = false;
        String title = titleEdit.getText().toString();
        Integer count = Integer.parseInt(countEdit.getText().toString());

        task.title = title;
        task.count = count;
        if (task.createdOn == null) {
            isNewObject = true;
            task.createdOn = new Date();
        }
        task.save();

        BusHolder.getInstance().post(
                isNewObject ? new OnCreatedEvent(task) : new OnUpdatedEvent(task)
        );
    }

    @Optional
    @OnClick(R.id.task_form_button_delete)
    public void delete() {
        this.task.delete();
        BusHolder.getInstance().post(new OnDeletedEvent(this.task));
    }

    public final class OnCreatedEvent {
        public Task task;
        public OnCreatedEvent(Task task) {
            this.task = task;
        }
    }
    public final class OnUpdatedEvent {
        public Task task;
        public OnUpdatedEvent(Task task) {
            this.task = task;
        }
    }
    public final class OnDeletedEvent {
        public Task task;
        public OnDeletedEvent(Task task) {
            this.task = task;
        }
    }
    public final class OnShownEvent {}
}
