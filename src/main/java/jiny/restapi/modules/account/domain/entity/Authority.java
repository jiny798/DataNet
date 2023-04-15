package jiny.restapi.modules.account.domain.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Authority {

    @Id
    private String authorityName;
}
