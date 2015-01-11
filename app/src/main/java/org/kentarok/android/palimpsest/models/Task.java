package org.kentarok.android.palimpsest.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name = "tasks")
public class Task extends Model {

    @Column(name = "name")
    public String name;
    @Column(name = "count")
    public Integer count;
    @Column(name = "done_on")
    public Date doneOn;

    public Task() {
        super();
    }

    public Task(String name, Integer count, Date doneOn) {
        super();
        this.name = name;
        this.count = count;
        this.doneOn = doneOn;
    }

    public static List<Task> todoList() {
        List<Task> tasks = new Select().from(Task.class).execute();
        List<Task> todoList = new ArrayList<Task>();

        for (Task task : tasks) {
            if (task.currentCount() > 0) {
                todoList.add(task);
            }
        }

        return todoList;
    }

    public Integer currentCount() {
        Date today = new Date();
        return this.count * (today.compareTo(this.doneOn));
    }
}
