package ru.geekbrains.applicationnotesvm.domain;

public interface Callback<T> {
    void onResult(T value);
}
