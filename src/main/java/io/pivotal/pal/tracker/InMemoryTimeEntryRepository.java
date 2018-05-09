package io.pivotal.pal.tracker;

import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class InMemoryTimeEntryRepository implements TimeEntryRepository{

       HashMap<Long, TimeEntry> inMemory = new HashMap<>();


    @Override
    public TimeEntry create(TimeEntry timeEntry) throws Exception {


        long id= timeEntry.getId();
        if (id ==0) {
            timeEntry.setId(inMemory.size() + 1);
            id = timeEntry.getId();
        }

        inMemory.put(id,timeEntry);
        return timeEntry;
    }

    @Override
    public TimeEntry find(Long id) throws Exception {

         return inMemory.get(id);
    }

    @Override
    public TimeEntry update(Long id, TimeEntry timeEntry) throws Exception {
        TimeEntry inMemTimeEntry = inMemory.get(id);
        inMemTimeEntry.setDate(timeEntry.getDate());
        inMemTimeEntry.setHours(timeEntry.getHours());
        inMemTimeEntry.setProjectId(timeEntry.getProjectId());
        inMemTimeEntry.setUserId(timeEntry.getUserId());

        return inMemTimeEntry;
    }

    @Override
    public List<TimeEntry> list() throws Exception {
        return new ArrayList<>(inMemory.values());
    }

    @Override
    public TimeEntry delete(Long id) throws Exception {
        return inMemory.remove(id);
    }
}
