package com.transaction.flightserviceexample.repository;

import com.transaction.flightserviceexample.entity.PaymentInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentInformationRepository extends JpaRepository<PaymentInfo, String> {
}
