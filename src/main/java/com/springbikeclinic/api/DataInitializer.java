package com.springbikeclinic.api;

import com.springbikeclinic.api.domain.WorkOrder;
import com.springbikeclinic.api.repositories.WorkOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.sql.Timestamp;
import java.time.LocalDateTime;


@Component
@Slf4j
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final WorkOrderRepository workOrderRepository;


    @Override
    public void run(String... args) throws Exception {
        if (workOrderRepository.count() > 0) {
            log.warn("******* Data already exists, data initializer will not continue.");
            return;
        }

        log.info("******* Starting data initializer process...");

        workOrderRepository.save(
                new WorkOrder(
                        1L,
                        Timestamp.valueOf(LocalDateTime.now()),
                        "Nice Bike",
                        "Tune-Up",
                        "open",
                        "Needs work",
                        false
                )
        );

        log.info("******* Completed data initializer process.");
    }

}
