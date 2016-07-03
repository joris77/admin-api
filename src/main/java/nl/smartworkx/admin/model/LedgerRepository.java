package nl.smartworkx.admin.model;

import org.springframework.data.repository.CrudRepository;

/**
 * @author Joris Wijlens
 * @version 1.0
 * @since 1.0
 */
public interface LedgerRepository extends CrudRepository<Ledger, LedgerId> {
}