package pl.slabonart.module_6.sender;

public class MailServer implements MessageSender {


    @Override
    public void send(String destination, String message) {
        System.out.println("Sending email to " + destination);
        System.out.println("Message:");
        System.out.println(message);
    }
}
