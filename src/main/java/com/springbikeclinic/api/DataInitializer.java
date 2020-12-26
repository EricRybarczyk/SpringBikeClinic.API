package com.springbikeclinic.api;

import com.github.javafaker.Faker;
import com.springbikeclinic.api.domain.*;
import com.springbikeclinic.api.repositories.BikeRepository;
import com.springbikeclinic.api.repositories.CustomerRepository;
import com.springbikeclinic.api.repositories.MechanicRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Component
public class DataInitializer implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final MechanicRepository mechanicRepository;
    private final BikeRepository bikeRepository;

    public DataInitializer(CustomerRepository customerRepository, MechanicRepository mechanicRepository, BikeRepository bikeRepository) {
        this.customerRepository = customerRepository;
        this.mechanicRepository = mechanicRepository;
        this.bikeRepository = bikeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("******* Starting data initializer process...");

        Faker faker = new Faker();

        // Customers
        for (int i = 0; i < 10; i++) {
            Customer customer = new Customer();
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            int daysBack = ThreadLocalRandom.current().nextInt(180, 730);
            customer.setFirstName(firstName);
            customer.setLastName(lastName);
            customer.setEmailAddress(String.format("%s.%s@domain.com", firstName, lastName));
            customer.setPhoneNumber(faker.phoneNumber().cellPhone());
            customer.setCreatedDate(LocalDate.now().minusDays(daysBack));

            Customer savedCustomer = customerRepository.save(customer);

            // add a bike for each customer
            Bike bike = new Bike();
            bike.setBikeType( i % 2 == 0 ? BikeType.MOUNTAIN : BikeType.ROAD);
            bike.setDescription(bike.getBikeType().toString().toLowerCase() + " bike");
            bike.setManufacturerName(faker.starTrek().specie());
            bike.setModelName(faker.space().constellation());
            bike.setModelYear(ThreadLocalRandom.current().nextInt(2003, 2021));
            bike.setOwner(savedCustomer);
            bikeRepository.save(bike);
        }

        // Mechanics
        for (int i = 0; i < 5; i++) {
            Mechanic mechanic = new Mechanic();String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            int daysBack = ThreadLocalRandom.current().nextInt(180, 365);
            int monthsBack = ThreadLocalRandom.current().nextInt(9,18);
            mechanic.setFirstName(firstName);
            mechanic.setLastName(lastName);
            mechanic.setEmailAddress(String.format("%s.%s@domain.com", firstName, lastName));
            mechanic.setPhoneNumber(faker.phoneNumber().cellPhone());
            mechanic.setHireDate(LocalDate.now().minusMonths(monthsBack).minusDays(daysBack));
            mechanic.setEmploymentStatus(EmploymentStatus.ACTIVE);
            mechanicRepository.save(mechanic);
        }


        log.info("******* Completed data initializer process.");
    }

}
