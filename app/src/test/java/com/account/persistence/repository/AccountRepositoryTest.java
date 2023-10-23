package com.account.persistence.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.account.persistence.entity.Account;
import java.util.List;
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
public class AccountRepositoryTest {

  @Autowired private AccountRepository accountRepository;

  @Test
  @DirtiesContext
  public void expectEmptyList() {
    List<Account> accounts = accountRepository.findAll();
    assertEquals(0, accounts.size());
  }

  @Test
  @DirtiesContext
  public void testInsertToDB() {
    Account account = new Account("testmail", "testusername", "testpassword", "teststatus");
    accountRepository.save(account);
    List<Account> accounts = accountRepository.findAll();
    assertEquals(1, accounts.size());
  }

  @Test
  @DirtiesContext
  public void testDataPersists() {
    Account account = new Account("testmail", "testusername", "testpassword", "teststatus");
    accountRepository.save(account);
    Account accountFromDB = accountRepository.findById(account.getId()).get();
    assertEquals(account.getId(), accountFromDB.getId());
    assertEquals(account.getUsername(), accountFromDB.getUsername());
    assertEquals(account.getPassword(), accountFromDB.getPassword());
    assertEquals(account.getStatus(), accountFromDB.getStatus());
    assertEquals(account.getEmail(), accountFromDB.getEmail());
  }

  @Test
  public void testInsertEmptyAccount() {
    Exception thrown =
        assertThrows(DataIntegrityViolationException.class, () -> insertEmptyAccount());
  }

  public void insertEmptyAccount() {
    Account account = new Account();
    accountRepository.save(account);
  }
}
