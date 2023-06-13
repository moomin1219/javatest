public class Message {
	
	private String queueName;
	private String queueMessage;
	private String messageId;
	private boolean receivable = true;
	
	public Message(String queueName, String queueMessage) {
		super();
		this.queueName = queueName;
		this.queueMessage = queueMessage;
		this.messageId = queueName + System.currentTimeMillis();
	}
	
	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public String getQueueMessage() {
		return queueMessage;
	}

	public void setQueueMessage(String queueMessage) {
		this.queueMessage = queueMessage;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public boolean isReceivable() {
		return receivable;
	}

	public void setReceivable(boolean receivable) {
		this.receivable = receivable;
	}

}
