package co.zelez.core.chat.usecase;

public class ChatService {
    public IManager service(int i) {
        return switch (i) {
            default -> new ConsoleManager();
        };
    }
}
