package com.codlin.cardiai.di

import com.codlin.cardiai.data.repo.UserRepoImpl
import com.codlin.cardiai.domain.repo.UserRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {

    @Binds
    @Singleton
    abstract fun bindUserRepo(userRepoImpl: UserRepoImpl): UserRepo
}