package com.account.model;

import java.net.URI;
import java.util.Objects;
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
 * RequestModel
 */

@JsonTypeName("requestModel")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class RequestModel {

  private String email;

  private String password;

  private String username;

  /**
   * Default constructor
   * @deprecated Use {@link RequestModel#RequestModel(String, String)}
   */
  @Deprecated
  public RequestModel() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public RequestModel(String email, String password) {
    this.email = email;
    this.password = password;
  }

  public RequestModel email(String email) {
    this.email = email;
    return this;
  }

  /**
   * Get email
   * @return email
  */
  @NotNull @jakarta.validation.constraints.Email
  @JsonProperty("email")
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public RequestModel password(String password) {
    this.password = password;
    return this;
  }

  /**
   * Get password
   * @return password
  */
  @NotNull @Pattern(regexp = "^[a-zA-Z0-9]*$") @Size(min = 8) 
  @JsonProperty("password")
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public RequestModel username(String username) {
    this.username = username;
    return this;
  }

  /**
   * Get username
   * @return username
  */
  @Size(min = 5) 
  @JsonProperty("username")
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RequestModel requestModel = (RequestModel) o;
    return Objects.equals(this.email, requestModel.email) &&
        Objects.equals(this.password, requestModel.password) &&
        Objects.equals(this.username, requestModel.username);
  }

  @Override
  public int hashCode() {
    return Objects.hash(email, password, username);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RequestModel {\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
    sb.append("    username: ").append(toIndentedString(username)).append("\n");
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

