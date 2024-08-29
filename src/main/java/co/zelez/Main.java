package co.zelez;

import co.zelez.core.chat.usecase.ChatService;

public class Main {
    public static void main(String[] args) {
        ChatService.chat(1);
        System.out.println("Goofbye");
    }
}