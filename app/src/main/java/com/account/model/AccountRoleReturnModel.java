package com.account.model;

import java.net.URI;
import java.util.Objects;
import com.account.model.AccountRoleReturnModelResult;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.*;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * AccountRoleReturnModel
 */

@JsonTypeName("accountRoleReturnModel")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class AccountRoleReturnModel {

  private Boolean ok;

  private AccountRoleReturnModelResult result;

  public AccountRoleReturnModel ok(Boolean ok) {
    this.ok = ok;
    return this;
  }

  /**
   * Get ok
   * @return ok
  */
  
  @JsonProperty("ok")
  public Boolean isOk() {
    return ok;
  }

  public void setOk(Boolean ok) {
    this.ok = ok;
  }

  public AccountRoleReturnModel result(AccountRoleReturnModelResult result) {
    this.result = result;
    return this;
  }

  /**
   * Get result
   * @return result
  */
  @Valid 
  @JsonProperty("result")
  public AccountRoleReturnModelResult getResult() {
    return result;
  }

  public void setResult(AccountRoleReturnModelResult result) {
    this.result = result;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AccountRoleReturnModel accountRoleReturnModel = (AccountRoleReturnModel) o;
    return Objects.equals(this.ok, accountRoleReturnModel.ok) &&
        Objects.equals(this.result, accountRoleReturnModel.result);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ok, result);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AccountRoleReturnModel {\n");
    sb.append("    ok: ").append(toIndentedString(ok)).append("\n");
    sb.append("    result: ").append(toIndentedString(result)).append("\n");
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

