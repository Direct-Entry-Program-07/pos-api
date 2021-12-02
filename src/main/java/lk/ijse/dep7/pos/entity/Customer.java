package lk.ijse.dep7.pos.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer")
@Entity
public class Customer implements SuperEntity {
    @Id
    private String id;
    private String name;
    private String address;
}
