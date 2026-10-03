package SpringBoot.Billwise.dto;

public class DashboardSummary {

    private long totalCustomers;
    private long totalInvoices;
    private long paidInvoices;
    private long pendingInvoices;
    private long overdueInvoices;
    private long totalReminderAttempts;
    private long successfulReminders;
    private long failedReminders;

    public long getTotalCustomers() {
        return totalCustomers;
    }

    public void setTotalCustomers(long totalCustomers) {
        this.totalCustomers = totalCustomers;
    }

    public long getTotalInvoices() {
        return totalInvoices;
    }

    public void setTotalInvoices(long totalInvoices) {
        this.totalInvoices = totalInvoices;
    }

    public long getPaidInvoices() {
        return paidInvoices;
    }

    public void setPaidInvoices(long paidInvoices) {
        this.paidInvoices = paidInvoices;
    }

    public long getPendingInvoices() {
        return pendingInvoices;
    }

    public void setPendingInvoices(long pendingInvoices) {
        this.pendingInvoices = pendingInvoices;
    }

    public long getOverdueInvoices() {
        return overdueInvoices;
    }

    public void setOverdueInvoices(long overdueInvoices) {
        this.overdueInvoices = overdueInvoices;
    }

    public long getTotalReminderAttempts() {
        return totalReminderAttempts;
    }

    public void setTotalReminderAttempts(long totalReminderAttempts) {
        this.totalReminderAttempts = totalReminderAttempts;
    }

    public long getSuccessfulReminders() {
        return successfulReminders;
    }

    public void setSuccessfulReminders(long successfulReminders) {
        this.successfulReminders = successfulReminders;
    }

    public long getFailedReminders() {
        return failedReminders;
    }

    public void setFailedReminders(long failedReminders) {
        this.failedReminders = failedReminders;
    }
}