package com.splitwiser.SplitWiser.payment;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("group")
public class GroupPayment extends Payment{

}
