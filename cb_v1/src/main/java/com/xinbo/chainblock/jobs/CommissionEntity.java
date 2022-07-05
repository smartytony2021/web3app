package com.xinbo.chainblock.jobs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommissionEntity {

    private Integer uid;
    private Double betMoney;
    private Integer rebate;

}