package com.account.model;

import java.net.URI;
import java.util.Objects;
import com.account.model.ReturnModelResult;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.time.OffsetDateTime;
import javax.validation.constraints.NotNull;


import java.util.*;
import javax.annotation.Generated;

/**
 * ReturnModel
 */

@JsonTypeName("returnModel")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class ReturnModel {

  private Boolean ok;

  private ReturnModelResult result;

  public ReturnModel ok(Boolean ok) {
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

  public ReturnModel result(ReturnModelResult result) {
    this.result = result;
    return this;
  }

  /**
   * Get result
   * @return result
  */
  
  @JsonProperty("result")
  public ReturnModelResult getResult() {
    return result;
  }

  public void setResult(ReturnModelResult result) {
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
    ReturnModel returnModel = (ReturnModel) o;
    return Objects.equals(this.ok, returnModel.ok) &&
        Objects.equals(this.result, returnModel.result);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ok, result);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReturnModel {\n");
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

