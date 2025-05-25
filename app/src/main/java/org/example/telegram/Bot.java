package org.example.telegram;

import org.apache.commons.lang3.time.DateUtils;
import org.example.classBulder.ItemBuilder;
import org.example.dao.ItemDaoImpl;
import org.example.interfaces.DefaultDao;
import org.example.model.Item;
import org.example.telegram.state.UserState;
import org.example.util.DateUtil;
import org.example.util.IntUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import jakarta.annotation.PostConstruct;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class Bot extends TelegramLongPollingBot {
    @Value("${telegram.bot.username}")
    private String botUsername;
    
    @Value("${telegram.bot.token}")
    private String botToken;

    private final Map<Long, UserState> userStates = new HashMap<>();

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @PostConstruct
    public void init() {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(this);
            System.out.println("Бот запущен успешно");
        } catch (TelegramApiException e) {
            System.err.println("Ошибка при запуске бота: " + e.getMessage());
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }

        Long chatId = update.getMessage().getChatId();
        String messageText = update.getMessage().getText();

        try {
            switch (messageText) {
                case "/start":
                    sendMessage(chatId, "Привет! Я бот для управления задачами. Используй /registerItem чтобы добавить новую задачу.");
                    break;
                case "/registerItem":
                    userStates.put(chatId, new UserState());
                    sendMessage(chatId, "Введите название задачи:");
                    break;
                case "/reset":
                    userStates.remove(chatId);
                    sendMessage(chatId, "Состояние сброшено. Можете начать заново с /registerItem");
                    break;
                default:
                    handleUserInput(chatId, messageText);
            }
        } catch (TelegramApiException e) {
            System.err.println("Ошибка обработки сообщения: " + e.getMessage());
        }
    }

    private void handleUserInput(Long chatId, String messageText) throws TelegramApiException {
        UserState state = userStates.get(chatId);
        if (state == null) {
            sendMessage(chatId, "Пожалуйста, начните с команды /registerItem");
            return;
        }

        String reply = processState(chatId, state, messageText);
        sendMessage(chatId, reply);
    }

    private String processState(Long chatId, UserState state, String messageText) {
        switch (state.getCurrentStep()) {
            case NAME:
                state.saveAnswer("Имя задачи", messageText);
                state.nextStep();
                return "Укажи крайний срок (в формате: 2025-05-20)";
            case DAY_DEAD_LINE:
                state.saveAnswer("Срок", messageText);
                state.nextStep();
                return "Какой статус задачи? (например: В процессе, Завершена, Отложена)";
            case STATUS:
                state.saveAnswer("Статус", messageText);
                state.nextStep();
                return "Есть ли комментарии к задаче?";
            case COMMENT:
                state.saveAnswer("Комментарий", messageText);
                state.nextStep();
                return "Приоритет задачи? (низкий, средний, высокий)";
            case PRIORITY:
                state.saveAnswer("Приоритет", messageText);
                state.nextStep();
                return generateSummary(chatId, state);
            case DONE:
                return "Задача уже сохранена ✅. Хочешь начать заново? Напиши /reset.";
            default:
                return "Неизвестная команда. Используйте /registerItem для создания новой задачи.";
        }
    }

    private String generateSummary(Long chatId, UserState state) {
        DefaultDao<Item> itemDao = new ItemDaoImpl();

        DateUtil dateUtils = new DateUtil();
        IntUtil intUtil = new IntUtil();

        Item item = new ItemBuilder()
        .withName(state.getAnswers().get("Имя задачи"))
        .withDayDeadLine(dateUtils.parseStringToDate(state.getAnswers().get("Срок")))
        .withStatuse(intUtil.parseStatus(state.getAnswers().get("Статус")))
        .withComment(state.getAnswers().get("Комментарий"))
        .withPriority(intUtil.parsePriority(state.getAnswers().get("Приоритет")))
        .withChatId(chatId)
        .build();

        itemDao.save(item);

        System.out.println(chatId);

        return """
            ✅ Задача добавлена:
            Название: %s
            Срок: %s
            Статус: %s
            Комментарий: %s
            Приоритет: %s
            """.formatted(
            state.getAnswers().get("Имя задачи"),
            state.getAnswers().get("Срок"),
            state.getAnswers().get("Статус"),
            state.getAnswers().get("Комментарий"),
            state.getAnswers().get("Приоритет")
        );
    }

    private void sendMessage(long chatId, String textToSend) throws TelegramApiException {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        execute(message);
    }
}