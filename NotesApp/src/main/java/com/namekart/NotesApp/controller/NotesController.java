package com.namekart.NotesApp.controller;

import com.namekart.NotesApp.entity.Note;
import com.namekart.NotesApp.entity.User;
import com.namekart.NotesApp.repository.NoteRepository;
import com.namekart.NotesApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/notes")
@CrossOrigin(origins = "http://localhost:3000")
public class NotesController {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<?> getNotes(Authentication auth) {
        User user = userRepository.findByUsername(auth.getName()).orElseThrow();
        return ResponseEntity.ok(Map.of("notes", noteRepository.findByUserIdOrderByUpdatedAtDesc(user.getId())));
    }

    @PostMapping
    public ResponseEntity<?> createNote(@RequestBody Map<String, String> request, Authentication auth) {
        User user = userRepository.findByUsername(auth.getName()).orElseThrow();
        Note note = new Note(request.get("title"), request.get("content"), user);
        noteRepository.save(note);
        return ResponseEntity.ok(note);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateNote(@PathVariable Long id, @RequestBody Map<String, Object> request, Authentication auth) {
        User user = userRepository.findByUsername(auth.getName()).orElseThrow();
        Optional<Note> noteOpt = noteRepository.findById(id);

        if (noteOpt.isEmpty() || !noteOpt.get().getUser().getId().equals(user.getId())) {
            return ResponseEntity.notFound().build();
        }

        Note note = noteOpt.get();
        Long requestVersion = Long.valueOf(request.get("version").toString());

        if (!note.getVersion().equals(requestVersion)) {
            return ResponseEntity.status(409).body(Map.of("error", "Version conflict", "current", note));
        }

        note.setTitle((String) request.get("title"));
        note.setContent((String) request.get("content"));
        noteRepository.save(note);
        return ResponseEntity.ok(note);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable Long id, Authentication auth) {
        User user = userRepository.findByUsername(auth.getName()).orElseThrow();
        Optional<Note> noteOpt = noteRepository.findById(id);

        if (noteOpt.isEmpty() || !noteOpt.get().getUser().getId().equals(user.getId())) {
            return ResponseEntity.notFound().build();
        }

        noteRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
