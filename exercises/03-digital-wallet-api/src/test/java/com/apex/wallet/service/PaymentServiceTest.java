package com.apex.wallet.service;

import com.apex.wallet.exception.InsufficientFundsException;
import com.apex.wallet.exception.InvalidTransferException;
import com.apex.wallet.exception.WalletInactiveException;
import com.apex.wallet.exception.WalletNotFoundException;
import com.apex.wallet.model.LedgerEntry;
import com.apex.wallet.model.Transaction;
import com.apex.wallet.model.Wallet;
import com.apex.wallet.model.WalletStatus;
import com.apex.wallet.repository.LedgerEntryRepository;
import com.apex.wallet.repository.TransactionRepository;
import com.apex.wallet.repository.WalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {
    @Mock
    private WalletService walletService;
    @Mock
    private WalletRepository walletRepository;
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private LedgerEntryRepository ledgerEntryRepository;

    @InjectMocks
    private PaymentService paymentService;

    private Wallet sourceWallet;
    private Wallet targetWallet;

    @BeforeEach
    void setUp(){
        sourceWallet = new Wallet();
        sourceWallet.setId(UUID.randomUUID());
        sourceWallet.setWalletNumber("WLT_SRC");
        sourceWallet.setCurrency("USD");
        sourceWallet.setBalance(new BigDecimal("100.0000"));
        sourceWallet.setStatus(WalletStatus.ACTIVE);
        targetWallet = new Wallet();
        targetWallet.setId(UUID.randomUUID());
        targetWallet.setWalletNumber("WLT_TGT");
        targetWallet.setCurrency("USD");
        targetWallet.setBalance(new BigDecimal("50.0000"));
        targetWallet.setStatus(WalletStatus.ACTIVE);
    }

    @Test
    void transfer_Successful_ShouldUpdateBalancesAndSaveEntries(){
        when(walletService.getWalletByNumber("WLT_SRC")).thenReturn(sourceWallet);
        when(walletService.getWalletByNumber("WLT_TGT")).thenReturn(targetWallet);

        Transaction tx = paymentService.transfer("WLT_SRC","WLT_TGT",
                BigDecimal.valueOf(30.0000),"Rent");

        assertEquals(new BigDecimal("70.0000"),sourceWallet.getBalance());
        assertEquals(new BigDecimal("80.0000"),targetWallet.getBalance());
        verify(walletRepository).save(sourceWallet);
        verify(walletRepository).save(targetWallet);
        verify(ledgerEntryRepository, times(2)).save(any(LedgerEntry.class));
    }

    @Test
    void transfer_InsufficientFunds_ShouldThrowAndNeverSave(){
        when(walletService.getWalletByNumber("WLT_SRC")).thenReturn(sourceWallet);
        when(walletService.getWalletByNumber("WLT_TGT")).thenReturn(targetWallet);
        assertThrows(InsufficientFundsException.class,() -> {
            paymentService.transfer("WLT_SRC","WLT_TGT",
                    new BigDecimal("3000.000"),"Rent");
        });
        verify(transactionRepository, never()).save(any());
        verify(ledgerEntryRepository, never()).save(any());
    }

    @Test
    void transfer_FrozenSourceWallet_ShouldThrowWalletInactiveException(){
        sourceWallet.setStatus(WalletStatus.FROZEN);
        when(walletService.getWalletByNumber("WLT_SRC")).thenReturn(sourceWallet);
        when(walletService.getWalletByNumber("WLT_TGT")).thenReturn(targetWallet);

        assertThrows(WalletInactiveException.class,() -> {
            paymentService.transfer("WLT_SRC","WLT_TGT",
                    new BigDecimal("30.000"),"Rent");
        });
        verify(transactionRepository, never()).save(any());
        verify(ledgerEntryRepository, never()).save(any());
    }
    @Test
    void transfer_SameWallet_ShouldThrowInvalidTransferException(){
        assertThrows(InvalidTransferException.class,() -> {
            paymentService.transfer("WLT_SRC","WLT_SRC",
                    new BigDecimal("30.000"),"Rent");
        });
        verify(transactionRepository, never()).save(any());
        verify(ledgerEntryRepository, never()).save(any());
    }
}