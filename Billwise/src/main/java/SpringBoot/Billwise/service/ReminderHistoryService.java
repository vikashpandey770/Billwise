package SpringBoot.Billwise.service;

import java.util.List;

import SpringBoot.Billwise.entity.ReminderAttempt;

public interface ReminderHistoryService {

	List<ReminderAttempt> getAllHistory();
	ReminderAttempt getHistoryById(Long id);
	List<ReminderAttempt> getHistoryByInvoice(Long invoiceId);
}
