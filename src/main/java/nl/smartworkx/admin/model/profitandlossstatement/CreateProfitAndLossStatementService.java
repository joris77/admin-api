package nl.smartworkx.admin.model.profitandlossstatement;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import nl.smartworkx.admin.model.journal.JournalEntryCalculator;
import nl.smartworkx.admin.model.journal.JournalEntryFinancialFact;
import nl.smartworkx.admin.model.journal.JournalEntryRepository;
import nl.smartworkx.admin.model.journal.Record;
import nl.smartworkx.admin.model.ledger.Ledger;
import nl.smartworkx.admin.model.ledger.LedgerRepository;
import nl.smartworkx.admin.model.time.DatePeriod;

/**
 * Created by joris on 16-5-17.
 */
@Service
@AllArgsConstructor
public class CreateProfitAndLossStatementService {

    private final LedgerRepository ledgerRepository;

    private final JournalEntryRepository journalEntryRepository;

    private final ProfitAndLossStatementRepository profitAndLossStatementRepository;

    public ProfitAndLossStatement create(DatePeriod period) {
        final Set<JournalEntryFinancialFact> journalEntryFinancialFacts = journalEntryRepository.findJournalEntriesByDate(period);

        JournalEntryCalculator calculator = new JournalEntryCalculator(ledgerRepository, journalEntryFinancialFacts);

        List<ProfitAndLossHeading> profitAndLossHeadings = ledgerRepository.findAllBy().filter(ledger -> ledger.shouldShowOnProfitAndLossStatement())
                .collect(groupingBy(ledger -> ledger.getProfitAndLossHeading())).entrySet().stream().map(entry -> {
                    final Map<Ledger, List<Record>> recordsByLedger = calculator.getRecordsByLedger();
                    return new ProfitAndLossHeading(entry.getKey(), entry.getValue().stream().flatMap(ledger -> {
                        final List<Record> records = recordsByLedger.get(ledger);
                        return records != null ? records.stream() : null;
                    }).collect
                            (toList()));
                }).collect(toList());

        final ProfitAndLossStatement profitAndLossStatement = new ProfitAndLossStatement(period, profitAndLossHeadings, "");

        profitAndLossStatementRepository.save(profitAndLossStatement);

        return profitAndLossStatement;
    }
}
