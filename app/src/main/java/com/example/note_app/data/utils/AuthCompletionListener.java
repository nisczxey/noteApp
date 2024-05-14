package com.example.note_app.data.utils;

public interface AuthCompletionListener {
    void onComplete(boolean success);
    void onError(Exception e);
}
