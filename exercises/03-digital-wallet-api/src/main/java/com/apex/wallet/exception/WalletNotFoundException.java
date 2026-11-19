package com.apex.wallet.exception;

public class WalletNotFoundException  extends RuntimeException{
    public WalletNotFoundException(String message){
        super(message);
    }
}
