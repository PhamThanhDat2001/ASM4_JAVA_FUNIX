package vn.funix.FX38455.java.asm04.service;

import vn.funix.FX38455.java.asm04.models.Account;
import vn.funix.FX38455.java.asm04.models.TransactionType;

public interface IReport {
    void log(double amount, TransactionType type, Account receiveAccount);
}
