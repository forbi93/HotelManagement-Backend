package com.inn.hotel.POJO;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;

@NamedQuery(name = "Customer.getAllCustomer", query = "select c from Customer c")

@NamedQuery(name = "Product.getCustomerById", query = "select new com.inn.hotel.wrapper.CustomerWrapper(c.id,c.name,c.lastName,c.contactNumber,c.dni) from Customer c where c.id=:id")


@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "customer")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "contactNumber")
    private String contactNumber;

    @Column(name = "dni")
    private String dni;

}
