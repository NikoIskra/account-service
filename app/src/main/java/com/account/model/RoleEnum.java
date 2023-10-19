package com.account.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.*;
import java.util.*;
import org.hibernate.validator.constraints.*;

/** Gets or Sets roleEnum */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public enum RoleEnum {
  CLIENT("client"),

  PROVIDER("provider"),

  DELIVERY("delivery"),

  MANAGER("manager");

  private String value;

  RoleEnum(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static RoleEnum fromValue(String value) {
    for (RoleEnum b : RoleEnum.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}
