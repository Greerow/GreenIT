package greenit.bot;

import greenit.config.BotProperties;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import greenit.service.CsvSearchService;

@Component
public class GreenitBot extends TelegramLongPollingBot {

    private final BotProperties botProperties;
    private final CsvSearchService csvSearchService;

    public GreenitBot(BotProperties botProperties, CsvSearchService csvSearchService) {
        this.botProperties = botProperties;
        this.csvSearchService = csvSearchService;
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

        if (update.hasMessage() && update.getMessage().hasText()) {

            String text = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();

            System.out.println("Получено сообщение: " + text);

            if (text.equals("/start")) {

                sendText(chatId,
                        "👋 Привет!\n\n" +
                                "Я GreenIT Bot\n\n" +
                                "Доступные команды:\n" +
                                "/help - помощь\n" +
                                "/find товар");

            } else if (text.equals("/help")) {

                sendText(chatId,
                        "Примеры использования:\n\n" +
                                "/find шампунь\n" +
                                "/find крем\n" +
                                "/find зубная паста");

            } else if (text.startsWith("/find ")) {

                String product = text.substring(6);

                String result =
                        csvSearchService.findProduct(product);

                sendText(chatId, result);
            } else if (text.startsWith("/compare ")) {

                String product = text.substring(9);

                String result = csvSearchService.compareProduct(product);

                sendText(chatId, result);

            } else {

                sendText(chatId,
                        "Неизвестная команда.\nИспользуй /help");
            }
        }
    }


        private void sendText (Long chatId, String text){

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
