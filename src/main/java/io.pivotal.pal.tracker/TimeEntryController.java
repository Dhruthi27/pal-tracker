package io.pivotal.pal.tracker;


import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {

    private TimeEntryRepository timeEntryRepository;
    private final DistributionSummary timeEntrySummary;
    private final Counter actionCounter;

    public TimeEntryController(TimeEntryRepository timeEntryRepository, MeterRegistry meterRegistry) {
        this.timeEntryRepository = timeEntryRepository;
        timeEntrySummary = meterRegistry.summary("timeEntry.summary");
        actionCounter = meterRegistry.counter("timeEntry.actionCounter");
    }

    @PostMapping()
    public ResponseEntity create(@RequestBody TimeEntry timeEntry) {
        TimeEntry timeEntry1 = timeEntryRepository.create(timeEntry);     //returning time entry obj
        return new ResponseEntity(timeEntry1, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable long id) {
        TimeEntry timeEntry1 = timeEntryRepository.find(id);
        if (timeEntry1 == null) {
            return new ResponseEntity(timeEntry1, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(timeEntry1);
    }

    @GetMapping()
    public ResponseEntity<List<TimeEntry>> list() {

        return new ResponseEntity(timeEntryRepository.list(), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<TimeEntry> update(@PathVariable long id, @RequestBody TimeEntry timeEntry) {
        TimeEntry entry = timeEntryRepository.update(id, timeEntry);
        if (entry == null)
            return new ResponseEntity(entry, HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(entry);
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete( @PathVariable  long id) {
        timeEntryRepository.delete(id);

            return new ResponseEntity(null, HttpStatus.NO_CONTENT);

    }
}
