package com.account.persistence.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.account.persistence.entity.AccountRole;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Propagation;

@DataJpaTest
@org.springframework.transaction.annotation.Transactional(propagation = Propagation.NOT_SUPPORTED)
@ActiveProfiles("test")
public class AccountRoleRepositoryTest {

  @Autowired AccountRoleRepository accountRoleRepository;

  @Test
  @DirtiesContext
  public void expectEmptyList() {
    List<AccountRole> accounts = accountRoleRepository.findAll();
    assertEquals(0, accounts.size());
  }

  @Test
  @DirtiesContext
  public void testInsertToDB() {
    AccountRole accountRole =
        new AccountRole(
            UUID.fromString("26bea1b4-ea70-4df2-836b-3ee2f5cece13"), "client", "active");
    accountRoleRepository.save(accountRole);
    List<AccountRole> accountRoles = accountRoleRepository.findAll();
    assertEquals(1, accountRoles.size());
  }

  @Test
  @DirtiesContext
  public void testDataPersists() {
    AccountRole accountRole =
        new AccountRole(
            UUID.fromString("26bea1b4-ea70-4df2-836b-3ee2f5cece13"), "client", "active");
    accountRoleRepository.save(accountRole);
    AccountRole accountFromDB = accountRoleRepository.findById(accountRole.getId()).get();
    assertEquals(accountRole.getId(), accountFromDB.getId());
    assertEquals(accountRole.getRole(), accountFromDB.getRole());
    assertEquals(accountRole.getStatus(), accountFromDB.getStatus());
  }

  @Test
  public void testInsertEmptyAccountRole() {
    Exception thrown =
        assertThrows(DataIntegrityViolationException.class, () -> insertEmptyAccountRole());
  }

  public void insertEmptyAccountRole() {
    AccountRole accountRole = new AccountRole();
    accountRoleRepository.save(accountRole);
  }
}
