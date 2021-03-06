package features.glue.integration;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import nl.smartworkx.admin.FinancialFactMvcTestHelper;
import nl.smartworkx.admin.InboxFinancialFactsMvcResult;
import features.glue.shared.KnowsTheFinancialFact;
import nl.smartworkx.admin.model.Amount;
import nl.smartworkx.admin.model.DebitCredit;
import nl.smartworkx.admin.model.ledger.Ledger;
import nl.smartworkx.admin.model.ledger.LedgerRepository;

/**
 *
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
public class FinancialFactInboxStepdefs extends AbstractIntegrationStepdefs {
    private InboxFinancialFactsMvcResult inboxFinancialFactsMvcResult;

    @Autowired
    private FinancialFactMvcTestHelper mvcTestHelper;

    @Autowired
    private KnowsTheFinancialFact knowsTheFinancialFact;

    @Autowired
    private LedgerRepository ledgerRepository;


    @When("^the entrepreneur asks for the journalization inbox$")
    public void theEntrepreneurAsksForTheJournalizationInbox() throws Throwable {
        inboxFinancialFactsMvcResult = mvcTestHelper.getInboxFinancialFacts();
    }

    @Then("^(\\d+) financial facts? (is|are) shown in the journalization inbox$")
    public void financialFactIsShownInTheJournalizationInbox(int numberOfItems, String bla) throws Throwable {
        inboxFinancialFactsMvcResult.returnsOk();
        inboxFinancialFactsMvcResult.arrayHasSizeOf(numberOfItems);
    }

    @Then("^the financial fact has a record proposal for \"([^\"]*)\" \"([^\"]*)\" of (\\d+.\\d+)$")
    public void theFinancialFactHasARecordProposalForOf(String ledgerCode, DebitCredit debitCredit, String amount) throws Throwable {
        Ledger ledger = ledgerRepository.findByCode(ledgerCode);
        final UUID uuid = knowsTheFinancialFact.getCurrent().getOrigin().getUuid();
        inboxFinancialFactsMvcResult.hasRecord(uuid, debitCredit, new Amount(amount), ledger);
    }
}
