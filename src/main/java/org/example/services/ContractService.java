package org.example.services;

import org.example.entities.Contract;
import org.example.entities.Installment;

import java.time.LocalDate;

public class ContractService {
    private OnlinePaymentService onlinePaymentService;

        public ContractService(OnlinePaymentService onlinePaymentService){
            this.onlinePaymentService = onlinePaymentService;
        }

        public void processContract(Contract contract, int months){
            double basicQuota = contract.getTotalValue() / months;
            for (int i = 1; i <= months; i++){
                double updatedQuota = basicQuota + onlinePaymentService.interest(basicQuota, i);
                double fullQuota = updatedQuota + onlinePaymentService.paymentFee(updatedQuota);
                LocalDate dueDate = contract.getDate().plusMonths(i);
                contract.getInstallments().add(new Installment(dueDate, fullQuota));
            }

        }
}
