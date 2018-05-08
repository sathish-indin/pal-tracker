package io.pivotal.pal.tracker;

import java.util.List;



public interface TimeEntryRepository {

    public TimeEntry create(TimeEntry tmeEntry) throws Exception;

    public TimeEntry find(Long id) throws Exception;

    public TimeEntry update(Long id, TimeEntry timeEntry) throws Exception;

    public List<TimeEntry> list() throws Exception;

    public TimeEntry delete(Long id) throws Exception;

}
