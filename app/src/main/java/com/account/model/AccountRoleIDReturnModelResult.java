package com.account.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.UUID;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.*;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * AccountRoleIDReturnModelResult
 */

@JsonTypeName("accountRoleIDReturnModel_result")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class AccountRoleIDReturnModelResult {

  private UUID roleId;

  public AccountRoleIDReturnModelResult roleId(UUID roleId) {
    this.roleId = roleId;
    return this;
  }

  /**
   * Get roleId
   * @return roleId
  */
  @Valid 
  @JsonProperty("roleId")
  public UUID getRoleId() {
    return roleId;
  }

  public void setRoleId(UUID roleId) {
    this.roleId = roleId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AccountRoleIDReturnModelResult accountRoleIDReturnModelResult = (AccountRoleIDReturnModelResult) o;
    return Objects.equals(this.roleId, accountRoleIDReturnModelResult.roleId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(roleId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AccountRoleIDReturnModelResult {\n");
    sb.append("    roleId: ").append(toIndentedString(roleId)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

