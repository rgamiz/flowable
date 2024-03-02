package com.mimacom.demo.loan.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Schema(description = "Customer representation")
public class Customer {
    @Schema(description = "Customer ID", example = "00000000T")
    @NonNull
    private String id;
    @Schema(description = "Customer Name", example = "Lidia")
    @NonNull
    private String name;
    @Schema(description = "Customer lastname", example = "Soriano Perez")
    @NonNull
    private String lastname;
    @Schema(description = "Customer date of birth", example = "2020-01-01")
    @NonNull
    private Date birthdate;
    @Schema(description = "Customer email", example = "lidia@gmail.com")
    @NonNull
    private String email;

    @Schema(description = "Customer payment status. Posibles values Standar, Defaulter or Reliable ", example = "lidia@gmail.com")
    private String paymentstatus = "Standard";

    public Customer(String id) {
        this.id = id;
    }
}
