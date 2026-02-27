package net.engineeringdigest.journalApp.controller;


import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping("/{userName}")
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser(@PathVariable String userName){
        User user =  userService.findByUserName(userName);
        if(user == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        List<JournalEntry> all= user.getJournalEntries();
        if(all!=null && !all.isEmpty()) return new ResponseEntity<>(all,HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{userName}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry entry,@PathVariable String userName){
        try {

            journalEntryService.saveEntry(entry,userName);
            return new ResponseEntity<>(entry, HttpStatus.CREATED);
        }catch (Exception e){
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> getEntryById(@PathVariable ObjectId myId){
        Optional<JournalEntry> curr =  journalEntryService.findById(myId);
        if(curr.isPresent()){
            return new ResponseEntity<>(curr.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{userName}/{myId}")
    public ResponseEntity<JournalEntry> deleteEntryById(@PathVariable ObjectId myId,@PathVariable String userName){

        try{
            journalEntryService.deleteById(myId,userName);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/id/{userName}/{id}")
    public ResponseEntity<JournalEntry> updateJournalById(@PathVariable ObjectId id,@RequestBody JournalEntry entry,@PathVariable String userName){
        JournalEntry old = journalEntryService.findById(id).orElse(null);
        if(old!=null){
            old.setName(entry.getName()!=null && !entry.getName().equals("")? entry.getName() : old.getName());
            old.setContent(entry.getContent()!=null && !entry.getContent().equals("") ? entry.getContent() : old.getContent());
            journalEntryService.saveEntry(old);
            return new ResponseEntity<>(old,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
