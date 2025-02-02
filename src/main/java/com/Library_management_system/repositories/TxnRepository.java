package com.Library_management_system.repositories;

import com.Library_management_system.model.Txn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TxnRepository extends JpaRepository<Txn, Integer>
{
    Txn findByTxnId(String txnId);
}
