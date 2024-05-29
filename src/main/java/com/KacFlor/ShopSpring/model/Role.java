package com.KacFlor.ShopSpring.model;

import lombok.experimental.FieldNameConstants;

@FieldNameConstants(onlyExplicitlyIncluded = true)
public enum Role{
    @FieldNameConstants.Include ADMIN,
    @FieldNameConstants.Include USER
}
