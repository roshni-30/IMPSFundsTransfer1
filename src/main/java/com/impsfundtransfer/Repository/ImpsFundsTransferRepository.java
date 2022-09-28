package com.impsfundtransfer.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.impsfundtransfer.Entity.ImpsFundsTransfer;


@Repository
public interface ImpsFundsTransferRepository extends JpaRepository<ImpsFundsTransfer, Long> {

}
