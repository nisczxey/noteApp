package com.example.note_app.di;

import com.example.note_app.data.repo.NoteRepositoryImpl;
import com.example.note_app.data.repo.UserAuthRepositoryImpl;
import com.example.note_app.domain.repo.NoteRepository;
import com.example.note_app.domain.repo.UserAuthRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DataModule {

    @Provides
    @Singleton
    public FirebaseAuth provideFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }

    @Provides
    @Singleton
    public FirebaseDatabase provideFirebaseDatabase() {
        return FirebaseDatabase.getInstance();
    }

    @Provides
    @Singleton
    public NoteRepository provideNoteRepository(NoteRepositoryImpl noteRepository) {
        return noteRepository;
    }

    @Provides
    @Singleton
    public UserAuthRepository provideUserAuthRepository(UserAuthRepositoryImpl userAuthRepository) {
        return userAuthRepository;
    }

}
