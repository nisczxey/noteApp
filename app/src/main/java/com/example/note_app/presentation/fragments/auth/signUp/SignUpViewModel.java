package com.example.note_app.presentation.fragments.auth.signUp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.note_app.domain.usecases.authUseCases.UserRegisterUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.disposables.Disposable;

@HiltViewModel
public class SignUpViewModel extends ViewModel {

    private  final UserRegisterUseCase userRegisterUseCase;
    private final MutableLiveData<Boolean> registrationResult = new MutableLiveData<>();
    private Disposable disposable;

    @Inject
    public SignUpViewModel(UserRegisterUseCase userRegisterUseCase) {
        this.userRegisterUseCase = userRegisterUseCase;
    }

    public LiveData<Boolean> getRegistrationResult() {
        return registrationResult;
    }

    public void registerUser(String email, String password){
       disposable = userRegisterUseCase.execute(email, password)
                .subscribe(registrationResult::postValue,
                        throwable -> registrationResult.postValue(false));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
