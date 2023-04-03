package com.transaction.flightserviceexample.service;

import com.transaction.flightserviceexample.dto.FlightBookingAcknowledgement;
import com.transaction.flightserviceexample.dto.FlightBookingRequest;
import com.transaction.flightserviceexample.entity.PassengerInfo;
import com.transaction.flightserviceexample.entity.PaymentInfo;
import com.transaction.flightserviceexample.repository.PassengerInfoRepository;
import com.transaction.flightserviceexample.repository.PaymentInformationRepository;
import com.transaction.flightserviceexample.utils.PaymentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class FlightBookingService {

    @Autowired
    private PassengerInfoRepository passengerInfoRepository;

    @Autowired
    private PaymentInformationRepository paymentInformationRepository;

    @Transactional//(readOnly = false, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public FlightBookingAcknowledgement bookFlightTicket(FlightBookingRequest request) {
        PassengerInfo passengerInfo = request.getPassengerInfo();
        passengerInfo = passengerInfoRepository.save(passengerInfo);

        PaymentInfo paymentInfo = request.getPaymentInfo();
        PaymentUtils.validateCreditLimit(paymentInfo.getAccountNo(), passengerInfo.getFare());

        paymentInfo.setPassengerId(passengerInfo.getPId());
        paymentInfo.setAmount(passengerInfo.getFare());

        passengerInfoRepository.save(passengerInfo);

        return new FlightBookingAcknowledgement("Success",
                passengerInfo.getFare(),
                UUID.randomUUID().toString().split("-")[0],
                passengerInfo
        );
    }
}
