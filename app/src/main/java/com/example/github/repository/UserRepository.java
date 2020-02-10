package com.example.github.repository;

import androidx.lifecycle.LiveData;

import com.example.github.AppExecutors;
import com.example.github.api.ApiResponse;
import com.example.github.api.WebServiceApi;
import com.example.github.db.UserDao;
import com.example.github.model.User;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserRepository {

    private final UserDao userDao;
    private final WebServiceApi gitHubService;
    private final AppExecutors appExecutors;

    @Inject
    public UserRepository(UserDao userDao, WebServiceApi gitHubService, AppExecutors appExecutors) {
        this.userDao = userDao;
        this.gitHubService = gitHubService;
        this.appExecutors = appExecutors;
    }

    public LiveData<Resource<User>> loadUser(String login){
        return new NetworkBoundResource<User, User>(appExecutors){

            @Override
            protected boolean shouldFetch(User data) {
                return data == null;
            }

            @Override
            protected LiveData<User> loadFromDb() {
                return userDao.findByLogin(login);
            }

            @Override
            protected void saveCallResult(User item) {
                userDao.insert(item);
            }

            @Override
            protected LiveData<ApiResponse<User>> createCall() {
                return gitHubService.getUser(login);
            }
        }.asLiveData();
    }
}
