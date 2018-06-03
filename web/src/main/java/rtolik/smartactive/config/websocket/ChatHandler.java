package rtolik.smartactive.config.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import rtolik.smartactive.config.websocket.utils.errors.NullPointerCategory;
import rtolik.smartactive.config.websocket.utils.model.CategoryMessage;
import rtolik.smartactive.config.websocket.utils.model.Message;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import rtolik.smartactive.models.Category;
import rtolik.smartactive.service.CategoryService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;


@Component
public class ChatHandler extends TextWebSocketHandler {

    public static final List<CategoryMessage> categoryMessages = new ArrayList<>();

    public static final List<WebSocketSession> sessions = new ArrayList<>();


    @Autowired
    private CategoryService categoryService;

    static {
        List<Message> messageList = new ArrayList<>();
        ChatHandler.categoryMessages.add(new CategoryMessage().setId(0).setMessages(messageList)
                .setName("tes").setTime(LocalDateTime.now()));
    }

    public void sendMessage(Message message, WebSocketSession socketSession) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            socketSession.sendMessage(new TextMessage(mapper.writeValueAsString(message)));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        if (session.getPrincipal() != null && session.getPrincipal().getName() != null)
            if (!sessions.stream().map(socketSession -> socketSession.getPrincipal().getName()).collect(toList())
                    .contains(session.getPrincipal().getName())) {
                sessions.add(session);
                System.err.println(session.getId());
            } else {

            }
    }

    @Scheduled(fixedDelay = 2000)
    public void deleteCloseSession() {
        try {
            sessions.stream().filter(webSocketSession -> !webSocketSession.isOpen()).forEach(webSocketSession -> {
                sessions.remove(sessions.indexOf(webSocketSession));
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Scheduled(fixedDelay = 2000)
    private void  createChatrooms(){
        List<Message> messageList = new ArrayList<>();
        List<Category> categories = categoryService.findAll();
        if (categoryMessages.size()!=categories.size()){
            for (Category category:categories) {
                if(categoryMessages.stream().noneMatch(categoryMessage -> categoryMessage.getName().equals(category.getName()))){
                    categoryMessages.add(new CategoryMessage().setId(category.getId()).setName(category.getName())
                            .setTime(LocalDateTime.now()).setMessages(messageList));
                }
            }
        }
    }

    private List<WebSocketSession> getWebSocketSessionFromCategory(CategoryMessage category) throws NullPointerCategory {
        CategoryMessage obj = categoryMessages.stream().filter(categoryMessage -> categoryMessage.equals(category))
                .findFirst().orElseThrow(new Supplier<NullPointerCategory>() {
                    @Override
                    public NullPointerCategory get() {
                        return new NullPointerCategory();
                    }
                });

        Set mes = obj.getMessages().stream().map(Message::getUserName).collect(toSet());
        return sessions.stream().filter(socketSession -> mes.contains(socketSession.getPrincipal().getName())).collect(toList());
    }

    private Integer getIndexCategoryMessage(CategoryMessage categoryMessage) {
        System.err.println("--------------------------------------categoryMessages.size() " + categoryMessages.size());
        for (int i = 0; i < categoryMessages.size(); i++) {
            if (categoryMessage.getId().equals(categoryMessages.get(i).getId())) {
                return i;
            }
        }
        return null;

    }

    private List<Message> getMessageFromCategory(CategoryMessage categoryMessage) throws NullPointerCategory {
        return categoryMessages.stream().filter(categoryMessage1 -> categoryMessage1.equals(categoryMessage))
                .findFirst().orElseThrow(new Supplier<NullPointerCategory>() {
                    @Override
                    public NullPointerCategory get() {
                        return new NullPointerCategory();
                    }
                }).getMessages();
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        try {
            System.err.println("-=-=-=-=-=-="/*+message.getPayload()*/);
            ObjectMapper mapper = new ObjectMapper();
            Message mes = mapper.readValue(message.getPayload(), Message.class);
            if (mes.getMessage() == null || mes.getMessage().isEmpty()) {
                try {
                    getMessageFromCategory(mes.getCategoryMessage()).stream().forEach(message1 -> {
                        this.sendMessage(message1, session);
                        System.err.println("-=-=-=-=-=-=id " + session.getId());
                        System.err.println("-=-=-=-=-=-=mes " + message1);
                    });
                } catch (NullPointerCategory nullPointerCategory) {
                    nullPointerCategory.printStackTrace();
//todo
                }
            } else {

                try {
                    mes.setUserName(session.getPrincipal().getName()).setDate(LocalDateTime.now());
                    System.err.println("mes : " + mes.getCategoryMessage().getId() + " " + mes.getMessage());
                    categoryMessages.get(getIndexCategoryMessage(mes.getCategoryMessage())).getMessages()
                            .add(mes.setCategoryMessage(categoryMessages.get(getIndexCategoryMessage(mes.getCategoryMessage()))));
                    List<WebSocketSession> s = getWebSocketSessionFromCategory(mes.getCategoryMessage());
                    if (!s.contains(session))
                        s.add(session);
                    s.stream().forEach(socketSession -> {
                        this.sendMessage(mes, socketSession);
                    });
//                    categoryMessages.add(mes.setDate(LocalDateTime.now()).setUserName(sessions.getPrincipal().getName()).setCategoryMessage(mes))
                } catch (NullPointerCategory nullPointerCategory) {
                    nullPointerCategory.printStackTrace();
                    System.err.println(nullPointerCategory.getMessage());
//todo
                }

            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }

}