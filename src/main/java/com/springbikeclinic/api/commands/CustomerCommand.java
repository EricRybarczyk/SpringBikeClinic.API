package com.springbikeclinic.api.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class CustomerCommand extends PersonCommand {

    @PastOrPresent
    public LocalDate createdDate;
}
