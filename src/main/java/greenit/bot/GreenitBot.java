package greenit.bot;

import greenit.config.BotProperties;
import greenit.service.CsvSearchService;
import greenit.service.MatchSearchService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class GreenitBot extends TelegramLongPollingBot {

    private final BotProperties botProperties;
    private final CsvSearchService csvSearchService;
    private final MatchSearchService matchSearchService;

    public GreenitBot(
            BotProperties botProperties,
            CsvSearchService csvSearchService,
            MatchSearchService matchSearchService
    ) {
        this.botProperties = botProperties;
        this.csvSearchService = csvSearchService;
        this.matchSearchService = matchSearchService;
    }

    @Override
    public String getBotUsername() {
        return "greenit_price_bot";
    }

    @Override
    public String getBotToken() {
        return botProperties.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }

        String text = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();

        System.out.println("Получено сообщение: " + text);

        if (text.equals("/start")) {

            sendText(chatId,
                    "👋 Привет!\n\n" +
                            "Я GreenIT Bot\n\n" +
                            "Доступные команды:\n" +
                            "/help - помощь\n" +
                            "/find товар\n" +
                            "/compare товар");

        } else if (text.equals("/help")) {

            sendText(chatId,
                    "Примеры использования:\n\n" +
                            "/find шампунь\n" +
                            "/find крем\n" +
                            "/compare elseve\n" +
                            "/compare прокладки");

        } else if (text.startsWith("/find ")) {

            String product = text.substring(6).trim();

            String result =
                    csvSearchService.findProduct(product);

            sendText(chatId, result);

        } else if (text.startsWith("/compare ")) {

            String product = text.substring(9).trim();

            String result =
                    matchSearchService.compareProduct(product);

            sendText(chatId, result);

        } else {

            sendText(chatId,
                    "Неизвестная команда.\nИспользуй /help");
        }
    }

    private void sendText(Long chatId, String text) {

        SendMessage message = new SendMessage();

        message.setChatId(chatId.toString());
        message.setText(text);

        try {
            execute(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}