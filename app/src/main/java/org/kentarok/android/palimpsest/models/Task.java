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

    @Column(name = "title")
    public String title;
    @Column(name = "count")
    public Integer count;
    @Column(name = "created_on")
    public Date createdOn;
    @Column(name = "done_on")
    public Date doneOn;

    public Task() {
        super();
    }

    public Task(String title, Integer count, Date doneOn) {
        super();
        this.title = title;
        this.count = count;
        this.doneOn = doneOn;
    }

    public boolean isEmpty() {
        return this.title == "";
    }

    public static List<Task> taskList() {
        List<Task> tasks = new Select().from(Task.class).orderBy("id DESC").execute();
        List<Task> taskList = new ArrayList<Task>();

        for (Task task : tasks) {
            if (task.currentCount() > 0) {
                taskList.add(task);
            }
        }

        return taskList;
    }

    public Integer currentCount() {
        Date today = new Date();
        Date before = this.doneOn == null ? this.createdOn : this.doneOn;
        return this.count * today.compareTo(before);
    }
}
