package com.applock.singleapplock;

public interface FingerprintHandlerInterface {
    void authenticationErrorOccurred(String str);

    void authenticationFailed();

    void authenticationSucceeded();

    void fatalErrorOccurred(String str);
}
