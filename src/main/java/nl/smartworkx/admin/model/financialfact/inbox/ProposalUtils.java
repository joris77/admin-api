package nl.smartworkx.admin.model.financialfact.inbox;

import static nl.smartworkx.admin.model.DebitCredit.CREDIT;
import static nl.smartworkx.admin.model.DebitCredit.DEBIT;
import static nl.smartworkx.admin.model.financialfact.inbox.RecordsBuilder.builder;

import java.util.List;

import nl.smartworkx.admin.interfaces.web.journal.RecordFormLine;
import nl.smartworkx.admin.model.Amount;
import nl.smartworkx.admin.model.journal.LedgerRepository;

public class ProposalUtils {
    static List<RecordFormLine> createRecordsFromBankWithVat(LedgerRepository ledgerRepository, Amount amountVatIncluded, double taxRate, String ledgerCode) {
        final Amount amountVat = amountVatIncluded.calculateIncVat(taxRate);
        final Amount amountExVat = amountVatIncluded.subtract(amountVat);
        return RecordsBuilder.builder(ledgerRepository)
                .add(ledgerCode, DEBIT, amountExVat)
                .add("DVAT", DEBIT, amountVat)
                .add("BANK", CREDIT, amountVatIncluded)
                .build();
    }

    static List<RecordFormLine> createRecordsFromBank(LedgerRepository ledgerRepository, Amount amount, String ledgerCode) {
        return builder(ledgerRepository)
                .add(ledgerCode, DEBIT, amount)
                .add("BANK", CREDIT, amount)
                .build();
    }
}