import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class NewBot extends TelegramLongPollingBot {
    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(new NewBot());
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            String messageText = update.getMessage().getText();

            if (messageText.equalsIgnoreCase("/start")) {
                sendTextMessage(chatId, "Я бот для поиска сигм");
            } else {
                sendTextMessage(chatId, "Подождите  идет поиск вашего слова в нашей базе сигм ");

                new Thread(() -> {
                    try {
                        Thread.sleep(5000);
                        sendTextMessage(chatId, "Да  ваше слово есть в рядах сигм 💔Хотите еще поиграть? Напишите /start");
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }).start();
            }
        }
    }

    private void sendTextMessage(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBotUsername() {
        return"MyFirtsBotForJava";
    }

    @Override
    public String getBotToken() {
        return "7868973521:AAFbf-n0W6MUt6C6rG_FAMcjJsDcNsX1n0E";
    }
}
