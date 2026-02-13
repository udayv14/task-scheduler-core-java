package com.example.taskscheduler.app.util;


import com.example.taskscheduler.app.model.Task;

import java.util.Comparator;

public class TaskComparator implements Comparator<Task> {

    @Override
    public int compare(Task t1, Task t2) {


        int timeComparison = t1.getExecuteAt()
                .compareTo(t2.getExecuteAt());

        if (timeComparison != 0) {
            return timeComparison;
        }

        
        return Integer.compare(t2.getPriority(), t1.getPriority());
    }
}
