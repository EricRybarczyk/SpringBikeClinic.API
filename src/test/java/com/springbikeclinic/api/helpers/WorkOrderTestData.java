package com.springbikeclinic.api.helpers;

import com.springbikeclinic.api.domain.Bike;
import com.springbikeclinic.api.domain.Customer;
import com.springbikeclinic.api.domain.WorkOrder;
import com.springbikeclinic.api.domain.WorkOrderStatus;
import com.springbikeclinic.api.dto.BikeDto;
import com.springbikeclinic.api.dto.CustomerDto;
import com.springbikeclinic.api.dto.WorkOrderDto;
import java.time.LocalDateTime;

public class WorkOrderTestData {

    private static final String CUSTOMER_NOTES = "Bike needs work to get back on the trail.";
    private static final LocalDateTime CREATED_DATE = LocalDateTime.of(2021, 1, 13, 8, 0,0);
    private static final LocalDateTime SUBMITTED_DATE = LocalDateTime.of(2021, 1, 13, 8, 5,0);

    public static WorkOrder generatePendingWorkOrder() {
        Customer customer = CustomerTestData.generateCustomer();
        customer.setId(1L);
        Bike bike = BikeTestData.generateBike();
        bike.setId(1L);

        WorkOrder workOrder = new WorkOrder();
        workOrder.setCreatedDateTime(CREATED_DATE);
        workOrder.setSubmittedDateTime(SUBMITTED_DATE);
        workOrder.setCustomerNotes(CUSTOMER_NOTES);
        workOrder.setStatus(WorkOrderStatus.PENDING);
        workOrder.setBike(bike);
        workOrder.setCustomer(customer);

        return workOrder;
    }

    public static WorkOrderDto generatePendingWorkOrderDto() {
        CustomerDto customer = CustomerTestData.generateCustomerDto();
        customer.setId(1L);
        BikeDto bike = BikeTestData.generateBikeDto();
        bike.setId(1L);

        WorkOrderDto workOrder = new WorkOrderDto();
        workOrder.setCreatedDateTime(CREATED_DATE);
        workOrder.setSubmittedDateTime(SUBMITTED_DATE);
        workOrder.setCustomerNotes(CUSTOMER_NOTES);
        workOrder.setStatus(com.springbikeclinic.api.dto.WorkOrderStatus.PENDING);
        workOrder.setBike(bike);
        workOrder.setCustomer(customer);

        return workOrder;
    }

}
