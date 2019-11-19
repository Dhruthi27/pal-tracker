package io.pivotal.pal.tracker;

import java.util.*;

public class InMemoryTimeEntryRepository implements TimeEntryRepository{


    Map<Long,TimeEntry> map = new HashMap<Long,TimeEntry>();
    Long firstEntry=1L;
    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        //TimeEntry copy=new TimeEntry(firstEntry,timeEntry.getProjectId(),timeEntry.getUserId(), timeEntry.getDate(),timeEntry.getHours())
        //TODO copy the timeEntry and set the id ++firstEntry
        //TODO put opy in map
        timeEntry.setId(firstEntry);
        map.put(firstEntry,timeEntry);
        firstEntry++;
        // TODO return copy
        return timeEntry;
    }

    public TimeEntry find(long id) {

        return map.get(id);
    }

    public List<TimeEntry> list()
    {
        List<TimeEntry> valueList = new ArrayList<TimeEntry>(map.values());
        return valueList;
    }
    public TimeEntry update(long id, TimeEntry timeEntry) {
        timeEntry.setId(id);
        if(map.get(id)==null)
            return null;
        else {

            map.put(id, timeEntry);
            return timeEntry;
        }
    }

    public TimeEntry delete(long id) {
        TimeEntry entry = map.get(id);
        map.remove(id);
        return entry;
    }
}