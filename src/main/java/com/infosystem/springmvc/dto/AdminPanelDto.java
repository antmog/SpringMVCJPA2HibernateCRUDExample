package com.infosystem.springmvc.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class AdminPanelDto {
    private List<UserDto> users;
    private List<TariffDto> tariffs;
    private Set<TariffOptionDto> tariffOptions;

    public AdminPanelDto(List<UserDto> users, List<TariffDto> tariffs, Set<TariffOptionDto> tariffOptions) {
        this.users = users;
        this.tariffs = tariffs;
        this.tariffOptions = tariffOptions;
    }
}
