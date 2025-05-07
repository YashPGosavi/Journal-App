package com.hasher.journal.controller;

import com.hasher.journal.entity.JournalEntry;
import com.hasher.journal.entity.User;
import com.hasher.journal.repository.JournalEntryRepository;
import com.hasher.journal.service.JournalEntryService;
import com.hasher.journal.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @GetMapping
    public ResponseEntity<?> getAllJournalEntriesOfUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);

        List<JournalEntry> allEntries = user.getJournalEntries();

        if(allEntries != null && !allEntries.isEmpty()){
            return new ResponseEntity<>(allEntries,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry myEntry){

        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();

            User user = userService.findByUserName(userName);

            journalEntryService.saveEntry(myEntry, user);
            return new ResponseEntity<>(myEntry,HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }



    @GetMapping("/{journalId}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable Long journalId){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);

        List<JournalEntry> collect =  user.getJournalEntries().stream().filter(x -> x.getId().equals(journalId)).toList();

        if(!collect.isEmpty()){
            Optional<JournalEntry> journalEntry = journalEntryService.findById(journalId);

            if(journalEntry.isPresent()) {
                return new ResponseEntity<>(journalEntry, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Transactional
    @DeleteMapping("/{journalId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable Long journalId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        User user = userService.findByUserName(userName);

        boolean removed = user.getJournalEntries().removeIf(x -> x.getId().equals(journalId));

        if(removed) {
            userService.saveUser(user);
            journalEntryRepository.deleteById(journalId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{journalId}")
    public ResponseEntity<?> updateJournalEntry(@PathVariable Long journalId, @RequestBody JournalEntry newEntry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        User user = userService.findByUserName(userName);

        JournalEntry entryInDb = journalEntryService.findById(journalId).orElse(null);

        if(entryInDb != null){
            entryInDb.setTitle((newEntry.getTitle() != null) && (!newEntry.getTitle().isEmpty())? newEntry.getTitle() : entryInDb.getTitle());

            entryInDb.setContent((newEntry.getContent() != null) && (!newEntry.getContent().isEmpty())? newEntry.getContent() : entryInDb.getContent());

            journalEntryService.saveEntry(entryInDb, user);
            return new ResponseEntity<>(entryInDb,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
