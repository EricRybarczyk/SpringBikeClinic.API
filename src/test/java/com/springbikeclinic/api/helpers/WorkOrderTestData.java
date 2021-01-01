package com.springbikeclinic.api.helpers;

import com.github.javafaker.Faker;
import com.springbikeclinic.api.domain.Bike;
import com.springbikeclinic.api.domain.Customer;
import com.springbikeclinic.api.domain.WorkOrder;
import com.springbikeclinic.api.domain.WorkOrderStatus;
import java.time.LocalDateTime;

public class WorkOrderTestData {

    private static final Faker faker = new Faker();

    public static WorkOrder generatePendingWorkOrder() {
        Customer customer = CustomerTestData.generateCustomer();
        customer.setId(1L);
        Bike bike = BikeTestData.generateBike();
        bike.setId(1L);

        WorkOrder workOrder = new WorkOrder();
        workOrder.setCreatedDateTime(LocalDateTime.now());
        workOrder.setSubmittedDateTime(LocalDateTime.now());
        workOrder.setCustomerNotes(faker.lorem().sentence(12));
        workOrder.setStatus(WorkOrderStatus.PENDING);
        workOrder.setBike(bike);
        workOrder.setCustomer(customer);

        return workOrder;
    }

}
