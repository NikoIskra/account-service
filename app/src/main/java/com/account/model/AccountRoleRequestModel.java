package com.account.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.*;
import java.util.Objects;
import org.hibernate.validator.constraints.*;

/** AccountRoleRequestModel */
@JsonTypeName("accountRoleRequestModel")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class AccountRoleRequestModel {

  private RoleEnum role;

  /** Gets or Sets status */
  public enum StatusEnum {
    ACTIVE("active"),

    REVOKED("revoked");

    private String value;

    StatusEnum(String value) {
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
    public static StatusEnum fromValue(String value) {
      for (StatusEnum b : StatusEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private StatusEnum status = StatusEnum.ACTIVE;

  /**
   * Default constructor
   *
   * @deprecated Use {@link AccountRoleRequestModel#AccountRoleRequestModel(RoleEnum)}
   */
  @Deprecated
  public AccountRoleRequestModel() {
    super();
  }

  /** Constructor with only required parameters */
  public AccountRoleRequestModel(RoleEnum role) {
    this.role = role;
  }

  public AccountRoleRequestModel role(RoleEnum role) {
    this.role = role;
    return this;
  }

  /**
   * Get role
   *
   * @return role
   */
  @NotNull
  @Valid
  @JsonProperty("role")
  public RoleEnum getRole() {
    return role;
  }

  public void setRole(RoleEnum role) {
    this.role = role;
  }

  public AccountRoleRequestModel status(StatusEnum status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   *
   * @return status
   */
  @JsonProperty("status")
  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AccountRoleRequestModel accountRoleRequestModel = (AccountRoleRequestModel) o;
    return Objects.equals(this.role, accountRoleRequestModel.role)
        && Objects.equals(this.status, accountRoleRequestModel.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(role, status);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AccountRoleRequestModel {\n");
    sb.append("    role: ").append(toIndentedString(role)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
