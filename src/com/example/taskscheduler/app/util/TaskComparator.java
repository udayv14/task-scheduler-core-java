package com.example.taskscheduler.app.util;


import com.example.taskscheduler.app.model.Task;

import java.util.Comparator;

public class TaskComparator implements Comparator<Task> {

    @Override
    public int compare(Task t1, Task t2) {

        // 1️⃣ Pehle execution time compare hoga
        int timeComparison = t1.getExecuteAt()
                .compareTo(t2.getExecuteAt());

        if (timeComparison != 0) {
            return timeComparison;
        }

        // 2️⃣ Agar time same hai, to priority decide karegi
        // (higher priority = pehle execute)
        return Integer.compare(t2.getPriority(), t1.getPriority());
    }
}
