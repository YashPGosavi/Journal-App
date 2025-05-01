package com.hasher.journal.service;

import com.hasher.journal.entity.JournalEntry;
import com.hasher.journal.repository.JournalEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    // saveEntry
    public void saveEntry(JournalEntry myEntry){
        myEntry.setDateTime(LocalDateTime.now());
        journalEntryRepository.save(myEntry);
    }

    // getAll
    public List<JournalEntry> getALl(){
        return journalEntryRepository.findAll();
    }

    // findById
    public Optional<JournalEntry> findById(Long myId) {
        return journalEntryRepository.findById(myId);
    }

    // deleteById
    public void deleteById(Long myId){
        journalEntryRepository.deleteById(myId);
    }
}
