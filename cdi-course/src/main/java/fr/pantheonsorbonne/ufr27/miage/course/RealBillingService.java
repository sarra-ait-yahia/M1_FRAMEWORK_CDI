package fr.pantheonsorbonne.ufr27.miage.course;



public class RealBillingService implements BillingService {
	  public Receipt chargeOrder(PizzaOrder order, CreditCard creditCard) {
	    CreditCardProcessor processor = CreditCardProcessorFactory.getInstance();
	    TransactionLog transactionLog = TransactionLogFactory.getInstance();

	    try {
	      ChargeResult result = processor.charge(creditCard, order.getAmount());
	      transactionLog.logChargeResult(result);

	      return result.wasSuccessful()
	          ? Receipt.forSuccessfulCharge(order.getAmount())
	          : Receipt.forDeclinedCharge(result.getDeclineMessage());
	     } catch (UnreachableException e) {
	      transactionLog.logConnectException(e);
	      return Receipt.forSystemFailure(e.getMessage());
	    }
	  }
	}