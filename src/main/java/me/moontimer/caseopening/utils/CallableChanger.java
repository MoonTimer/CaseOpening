package me.moontimer.caseopening.utils;

public interface CallableChanger<T> {

    T call();
    void change(T t);

}
