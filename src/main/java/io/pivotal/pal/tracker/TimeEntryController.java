package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.sql.Time;
import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {


    TimeEntryRepository timeEntryRepository;

    @Autowired
    public TimeEntryController(TimeEntryRepository timeEntryRepository){
        this.timeEntryRepository=timeEntryRepository;
    }


    @PostMapping
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntry) throws Exception {

       TimeEntry createdTimeEntry=timeEntryRepository.create(timeEntry);
       return new ResponseEntity<TimeEntry>(createdTimeEntry,HttpStatus.CREATED);

    }

    @GetMapping("{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable long id) throws Exception {


        TimeEntry timeEntryResponse = timeEntryRepository.find(id);

        if (timeEntryResponse != null){
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
           return new ResponseEntity<TimeEntry>(timeEntryResponse,HttpStatus.OK);

        }
        else{
            return  new ResponseEntity<TimeEntry>(HttpStatus.NOT_FOUND);
        }



    }

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() throws Exception{


        return new ResponseEntity<List<TimeEntry>>(timeEntryRepository.list(),HttpStatus.OK);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<TimeEntry> delete(@PathVariable Long id) throws Exception{

        TimeEntry timeEntryResponse = timeEntryRepository.delete(id);
        return new ResponseEntity<TimeEntry>(HttpStatus.NO_CONTENT);

    }



}
