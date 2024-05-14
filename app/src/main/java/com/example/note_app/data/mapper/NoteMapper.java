package com.example.note_app.data.mapper;

import com.example.note_app.data.dto.NoteDto;
import com.example.note_app.domain.models.NoteModel;

import java.util.ArrayList;
import java.util.List;

public class NoteMapper {
    public NoteModel mapDtoToNoteModel(NoteDto noteDto) {
        NoteModel noteModel = new NoteModel();
        noteModel.setId(noteDto.getId());
        noteModel.setNoteTitle(noteDto.getNoteTitle());
        noteModel.setNoteText(noteDto.getNoteText());
        return noteModel;
    }
    public  NoteDto mapNoteModelToDto(NoteModel noteModel){
        NoteDto noteDto = new NoteDto();
        noteDto.setId(noteModel.getId());
        noteDto.setNoteTitle(noteModel.getNoteTitle());
        noteDto.setNoteText(noteModel.getNoteText());
        return  noteDto;
    }

    public List<NoteModel> mapDtoListToNoteModelList(List<NoteDto> noteDtoList) {
        List<NoteModel> noteModelList = new ArrayList<>();
        for (NoteDto noteDto : noteDtoList) {
            noteModelList.add(mapDtoToNoteModel(noteDto));
        }
        return noteModelList;
    }
}


