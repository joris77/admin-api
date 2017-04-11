package nl.smartworkx.admin.web;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import nl.smartworkx.admin.application.financialfact.inbox.InboxFinancialFact;
import nl.smartworkx.admin.application.financialfact.inbox.InboxFinancialFactService;

/**
 *
 */
@RestController
@RequestMapping("/inbox-financial-facts")
public class InboxFinancialFactsController {

    private final InboxFinancialFactService service;

    public InboxFinancialFactsController(InboxFinancialFactService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<InboxFinancialFact> getInboxFinancialFacts(){
        return service.getInboxFinancialFacts();
    }
}
