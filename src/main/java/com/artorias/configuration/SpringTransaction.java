package com.artorias.configuration;

/**
 * Created by devin on 12/29/16.
 */
import org.jooq.Transaction;
import org.springframework.transaction.TransactionStatus;

class SpringTransaction implements Transaction {
    final TransactionStatus tx;

    SpringTransaction(TransactionStatus tx) {
        this.tx = tx;
    }
}
