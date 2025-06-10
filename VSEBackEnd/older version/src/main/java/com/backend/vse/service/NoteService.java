package com.backend.vse.service;

import com.backend.vse.entity.Note;
import com.backend.vse.mapper.NoteMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class NoteService extends ServiceImpl<NoteMapper, Note> {

    // 根据 noteId 获取笔记信息
    public Note getNoteById(Long noteId) {
        return this.getById(noteId);
    }
}