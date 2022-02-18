package ee.taltech.insidertransactionsbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ee.taltech.insidertransactionsbackend.model.Issuer;

@Repository
public interface IssuerRepository extends JpaRepository<Issuer, Long> {
    List<Issuer> findByIsActiveTrue();
}
