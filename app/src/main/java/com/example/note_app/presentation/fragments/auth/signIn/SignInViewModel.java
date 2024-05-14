package com.example.note_app.presentation.fragments.auth.signIn;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.note_app.domain.models.NoteModel;
import com.example.note_app.domain.usecases.authUseCases.IsUserLoggedInUseCase;
import com.example.note_app.domain.usecases.authUseCases.UserLogInUseCase;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.disposables.Disposable;

@HiltViewModel
public  class SignInViewModel extends ViewModel {

    private final UserLogInUseCase userLogInUseCase;
    private final IsUserLoggedInUseCase isUserLoggedInUseCase;
    private Disposable disposable;

    private MutableLiveData<Boolean> logInLiveData;

    @Inject
	public SignInViewModel(
            UserLogInUseCase userLogInUseCase,
            IsUserLoggedInUseCase isUserLoggedInUseCase
    ) {
		this.userLogInUseCase = userLogInUseCase;
		this.isUserLoggedInUseCase = isUserLoggedInUseCase;
        logInLiveData = new MutableLiveData<>();
	}


	public LiveData<Boolean> logIn(){
       return logInLiveData;
    }

    public void userLogIn(String email, String password){
        disposable = userLogInUseCase.execute(email, password)
                .subscribe(
                        result -> {
                            logInLiveData.setValue(result);
                        }
                );
    }

    public  boolean isUserLoggedIn(){
        return isUserLoggedInUseCase.execute();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
