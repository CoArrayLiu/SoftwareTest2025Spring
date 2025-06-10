package com.backend.vse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplyAndDemandDto {
    @NotNull @Length String StudentId;
    @NotNull @Min(0) Integer money;
    @NotNull Timestamp submit_time;
    @NotNull @Length String quote_type;
}
