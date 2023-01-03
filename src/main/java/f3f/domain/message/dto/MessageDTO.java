package f3f.domain.message.dto;

public class MessageDTO {

    public static class TransferMessageDto{
        private long id;
        private String content;
        private String senderEmail;
        private String recipientEmail;
    }
}
