package com.tui.proof.repository;

import com.tui.proof.model.Order;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link Order} entities.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByClientFirstNameContainingIgnoreCaseOrClientLastNameContainingIgnoreCase(String firstName,
                                                                                              String lastName);
}
