package org.example.classBulder;

import java.util.Date;

import org.example.interfacesBuilder.ItemBuilderInterf;
import org.example.model.Item;
import org.example.model.User;

public class ItemBuilder implements ItemBuilderInterf {
    private String name;
    private Date dayDeadLine;
    private int status; 
    private String comment; 
    private int priority;
    private User user;
    private Long chatId;

    public ItemBuilder() {
        super();
    }
    
    public String getName() {
        return this.name;
    }

    public Date getDayDeadLine() {
        return dayDeadLine;
    }
    
    public int getStatus() {
        return status;
    }
    
    public String getComment() {
        return comment;
    }
    
    public int getPriority() {
        return priority;
    }

    public Long getChatId() {
        return chatId;
    }

    public User geyUser() {
        return this.user;
    }

    @Override
    public ItemBuilderInterf withName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public ItemBuilderInterf withDayDeadLine(Date date) {
        this.dayDeadLine = date;
        return this;
    }
    
    @Override
    public ItemBuilderInterf withStatuse(int status) {
        this.status = status;
        return this;
    }

    @Override
    public ItemBuilderInterf withComment(String comment) {
        this.comment = comment;
        return this;
    }

    @Override
    public ItemBuilderInterf withPriority(int priority) {
        this.priority = priority;
        return this;
    }

    @Override
    public ItemBuilderInterf withUser(User user) {
        this.user = user;
        return this;
    }

    @Override
    public ItemBuilderInterf withChatId(Long chatId) {
        this.chatId = chatId;
        return this;
    }

    @Override
    public Item build() {
        return new Item(this);
    }
}
