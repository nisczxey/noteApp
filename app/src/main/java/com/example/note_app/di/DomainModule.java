package com.example.note_app.di;

import com.example.note_app.domain.repo.NoteRepository;
import com.example.note_app.domain.repo.UserAuthRepository;
import com.example.note_app.domain.usecases.authUseCases.IsUserLoggedInUseCase;
import com.example.note_app.domain.usecases.authUseCases.UserLogInUseCase;
import com.example.note_app.domain.usecases.authUseCases.UserLogOutUseCase;
import com.example.note_app.domain.usecases.authUseCases.UserRegisterUseCase;
import com.example.note_app.domain.usecases.notesUseCases.AddNoteItemUseCase;
import com.example.note_app.domain.usecases.notesUseCases.DeleteNoteItemUseCase;
import com.example.note_app.domain.usecases.notesUseCases.EditNoteItemUseCase;
import com.example.note_app.domain.usecases.notesUseCases.GetAllNotesUseCase;
import com.example.note_app.domain.usecases.notesUseCases.GetNoteItemUseCase;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;

@Module
@InstallIn(ViewModelComponent.class)
public class DomainModule {

    @Provides
    public IsUserLoggedInUseCase provideIsUserLoggedInUseCase(UserAuthRepository userAuthRepository) {
        return new IsUserLoggedInUseCase(userAuthRepository);
    }

    @Provides
    public UserLogInUseCase provideUserLogInUseCase(UserAuthRepository userAuthRepository) {
        return new UserLogInUseCase(userAuthRepository);
    }

    @Provides
    public UserRegisterUseCase provideUserRegisterUseCase(UserAuthRepository userAuthRepository) {
        return new UserRegisterUseCase(userAuthRepository);
    }

    @Provides
    public UserLogOutUseCase provideUserLogOutUseCase(UserAuthRepository userAuthRepository){
        return  new UserLogOutUseCase(userAuthRepository);
    }

    @Provides
    public AddNoteItemUseCase provideAddNoteItemUseCase(NoteRepository noteRepository) {
        return new AddNoteItemUseCase(noteRepository);
    }

    @Provides
    public DeleteNoteItemUseCase provideDeleteNoteItemUseCase(NoteRepository noteRepository){
        return  new DeleteNoteItemUseCase(noteRepository);
    }
    @Provides
    public EditNoteItemUseCase provideEditNoteItemUseCase(NoteRepository noteRepository){
        return  new EditNoteItemUseCase(noteRepository);
    }
    @Provides
    public GetAllNotesUseCase provideGetAllNotesUseCase(NoteRepository noteRepository){
        return  new GetAllNotesUseCase(noteRepository);
    }
    @Provides
    public GetNoteItemUseCase provideGetNoteItemUseCase(NoteRepository noteRepository){
        return  new GetNoteItemUseCase(noteRepository);
    }

}
