package com.backend.vse.controller;

import com.backend.vse.entity.Note;
import com.backend.vse.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoteController {

    private final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    // 根据 noteId 获取指定笔记信息
    @GetMapping("/notes/{note_id}")
    public ResponseEntity<Note> getNoteById(@PathVariable("note_id") Long note_id) {
        Note note = noteService.getNoteById(note_id);
        if (note != null) {
            return ResponseEntity.ok(note);  // 返回笔记信息
        } else {
            return ResponseEntity.notFound().build();  // 如果笔记不存在，返回404
        }
    }
}