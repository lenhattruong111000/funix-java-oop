package asm04.common;

import asm04.model.Account;

public interface ITransfer {
    boolean transfer(Account recieveAccount, double amount);
}
