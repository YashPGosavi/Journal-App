package com.hasher.journal.controller;

import com.hasher.journal.entity.JournalEntry;
import com.hasher.journal.service.JournalEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @PostMapping
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry myEntry){
        try{
            journalEntryService.saveEntry(myEntry);
            return new ResponseEntity<>(myEntry,HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        List<JournalEntry> allEntirs = journalEntryService.getALl();

        if(!allEntirs.isEmpty()){
            return new ResponseEntity<>(allEntirs,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable Long myId){
        Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);

        return journalEntry.map(entry -> new ResponseEntity<>(entry, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/id/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable Long myId){
        Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);

        if(journalEntry.isPresent()) {
            journalEntryService.deleteById(myId);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/id/{myId}")
    public ResponseEntity<?> updateJournalEntry(@PathVariable Long myId, @RequestBody JournalEntry newEntry){
        JournalEntry old = journalEntryService.findById(myId).orElse(null);

        if(old != null){
            old.setTitle((newEntry.getTitle() != null) && (!newEntry.getTitle().isEmpty())? newEntry.getTitle() : old.getTitle());

            old.setContent((newEntry.getContent() != null) && (!newEntry.getContent().isEmpty())? newEntry.getContent() : old.getContent());
            
            journalEntryService.saveEntry(old);
            return new ResponseEntity<>(old,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
