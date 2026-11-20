package com.apex.wallet.service;

import com.apex.wallet.exception.InsufficientFundsException;
import com.apex.wallet.exception.InvalidTransferException;
import com.apex.wallet.exception.WalletInactiveException;
import com.apex.wallet.model.*;
import com.apex.wallet.repository.LedgerEntryRepository;
import com.apex.wallet.repository.TransactionRepository;
import com.apex.wallet.repository.WalletRepository;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class PaymentService {
    public static final String TREASURY_WALLET_NUMBER = "TREASURY_USD";
    private final WalletRepository walletRepository;
    private final WalletService walletService;
    private final TransactionRepository transactionRepository;
    private final LedgerEntryRepository ledgerEntryRepository;

    @Transactional
    public Transaction transfer(String sourceWalletNumber, String targetWalletNumber, BigDecimal amount, String referenceNote){
        if(amount == null || amount.compareTo(BigDecimal.ZERO) <= 0){
           throw new InvalidTransferException("Transfer amount must be strictly greater than zero");
        }
        if(sourceWalletNumber.equalsIgnoreCase(targetWalletNumber)){
            throw new InvalidTransferException("Cannot transfer funds to the same wallet");
        }
        Wallet sourceWallet = walletService.getWalletByNumber(sourceWalletNumber);
        Wallet targetWallet = walletService.getWalletByNumber(targetWalletNumber);
        if(sourceWallet.getStatus() != WalletStatus.ACTIVE){
            throw new WalletInactiveException("Wallet is not active Number :" + sourceWalletNumber);
        }
        if(targetWallet.getStatus() != WalletStatus.ACTIVE){
            throw new WalletInactiveException("Wallet is not active Number :" + targetWalletNumber);
        }
        if(!sourceWallet.getCurrency().equalsIgnoreCase(targetWallet.getCurrency())){
            throw new InvalidTransferException("Cross-currency transfers not supported");
        }
        if(sourceWallet.getBalance().compareTo(amount) < 0){
            throw new InsufficientFundsException("Insufficient balance in wallet: " + sourceWalletNumber + ". Available. "
                    + sourceWallet.getBalance() + ", Requested: " + amount);
        }
        BigDecimal newSourceBalance = sourceWallet.getBalance().subtract(amount);
        BigDecimal newTargetBalance = targetWallet.getBalance().add(amount);

        sourceWallet.setBalance(newSourceBalance);
        sourceWallet.setUpdatedAt(OffsetDateTime.now());

        targetWallet.setBalance(newTargetBalance);
        targetWallet.setUpdatedAt(OffsetDateTime.now());

        Transaction transaction = createTransaction(sourceWalletNumber, targetWalletNumber, referenceNote);
        transactionRepository.save(transaction);

        //Ledger
        LedgerEntry debit = new LedgerEntry();
        debit.setEntryType(EntryType.DEBIT);
        debit.setTransaction(transaction);
        debit.setWallet(sourceWallet);
        debit.setAmount(amount);
        debit.setRunningBalance(newSourceBalance);

        LedgerEntry credit = new LedgerEntry();
        credit.setEntryType(EntryType.CREDIT);
        credit.setTransaction(transaction);
        credit.setWallet(targetWallet);
        credit.setAmount(amount);
        credit.setRunningBalance(newTargetBalance);

        walletRepository.save(sourceWallet);
        walletRepository.save(targetWallet);
        ledgerEntryRepository.save(debit);
        ledgerEntryRepository.save(credit);
        return transaction;
    }

    @Transactional
    public Transaction deposit(String targetWalletNumber, BigDecimal amount, String referenceNote){
        return transfer(TREASURY_WALLET_NUMBER,targetWalletNumber,amount,referenceNote);
    }
    @Transactional
    public Transaction withdraw(String sourceWalletNumber, BigDecimal amount, String referenceNote){
        return transfer(sourceWalletNumber,TREASURY_WALLET_NUMBER,amount,referenceNote);
    }
    public List<LedgerEntry> getWalletStatement(String walletNumber){
        Wallet wallet = walletService.getWalletByNumber(walletNumber);
        return ledgerEntryRepository.findByWallet_IdOrderByCreatedAtDesc(wallet.getId());
    }
    private static @NonNull Transaction createTransaction(String sourceWalletNumber, String targetWalletNumber, String referenceNote) {
        Transaction transaction = new Transaction();
        if(sourceWalletNumber.equalsIgnoreCase(TREASURY_WALLET_NUMBER)){
            transaction.setTransactionType(TransactionType.DEPOSIT);
        }
        else if (targetWalletNumber.equalsIgnoreCase(TREASURY_WALLET_NUMBER)){
            transaction.setTransactionType(TransactionType.WITHDRAWAL);
        }
        else{
            transaction.setTransactionType(TransactionType.TRANSFER);
        }
        transaction.setTransactionStatus(TransactionStatus.SUCCESS);
        transaction.setReferenceNote(referenceNote);
        return transaction;
    }
}
