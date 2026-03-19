package phammenungdungj2ee.bai4_qlsp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;
import phammenungdungj2ee.bai4_qlsp.model.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    @Query("SELECT a FROM Account a WHERE a.login_name = :username")
    Optional<Account> findByLoginName(@Param("username") String username);
}