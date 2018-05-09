package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {


    private TimeEntryRepository timeEntryRepository;
    private final CounterService counter;
    private final GaugeService gauge;


    public TimeEntryController(TimeEntryRepository timeEntryRepository, CounterService counter, GaugeService gauge){
        this.timeEntryRepository=timeEntryRepository;
        this.counter = counter;
        this.gauge = gauge;
    }


    @PostMapping
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntry) throws Exception {

       TimeEntry createdTimeEntry=timeEntryRepository.create(timeEntry);
        counter.increment("TimeEntry.created");
        gauge.submit("timeEntries.count", timeEntryRepository.list().size());
       return new ResponseEntity<TimeEntry>(createdTimeEntry,HttpStatus.CREATED);

    }

    @GetMapping("{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable long id) throws Exception {


        TimeEntry timeEntryResponse = timeEntryRepository.find(id);

        if (timeEntryResponse != null){
            counter.increment("TimeEntry.read");
            return new ResponseEntity<TimeEntry>(timeEntryResponse,HttpStatus.OK);

        }
        else{
            return  new ResponseEntity<TimeEntry>(HttpStatus.NOT_FOUND);
        }





    }

    @PutMapping("{id}")
    public ResponseEntity<TimeEntry> update(@PathVariable long id,@RequestBody TimeEntry timeEntry) throws Exception{

        TimeEntry timeEntryResponse = timeEntryRepository.update(id, timeEntry);

        if (timeEntryResponse != null){
            counter.increment("TimeEntry.updated");
           return new ResponseEntity<TimeEntry>(timeEntryResponse,HttpStatus.OK);

        }
        else{
            return  new ResponseEntity<TimeEntry>(HttpStatus.NOT_FOUND);
        }



    }

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() throws Exception{

        counter.increment("TimeEntry.listed");
        return new ResponseEntity<List<TimeEntry>>(timeEntryRepository.list(),HttpStatus.OK);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<TimeEntry> delete(@PathVariable Long id) throws Exception{

        TimeEntry timeEntryResponse = timeEntryRepository.delete(id);
        counter.increment("TimeEntry.deleted");
        gauge.submit("timeEntries.count", timeEntryRepository.list().size());

        return new ResponseEntity<TimeEntry>(HttpStatus.NO_CONTENT);

    }



}
