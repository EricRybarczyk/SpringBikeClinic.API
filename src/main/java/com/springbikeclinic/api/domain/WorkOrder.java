package com.springbikeclinic.api.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Table(name = "work_orders")
@SQLDelete(sql = "UPDATE work_orders SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkOrder {

    @Id
    @GeneratedValue
    private Long id;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Timestamp createdDateTime;

    private String bikeDescription;

    private String workDescription;

    private String status;

    private String mechanicNotes;

    private boolean deleted = Boolean.FALSE;

    @PreRemove
    private void preRemove() {
        /*  Lifecycle callback, implementing soft-delete.
            The @Where annotation on this entity only updates the database, it does not modify the object itself
            because there is no connection between the @Where clause and the deleted field. This @PreRemove hook
            updates the object - and this includes updating the object in Hibernate cache if applicable.
         */
        this.deleted = true;
    }
}
