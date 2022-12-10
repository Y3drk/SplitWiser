package com.splitwiser.SplitWiser.Payment;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("group")
public class GroupPayment extends Payment{

}
