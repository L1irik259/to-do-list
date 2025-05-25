package org.example.interfacesBuilder;

import java.util.Date;

import org.example.model.Item;
import org.example.model.User;

public interface ItemBuilderInterf {
    public ItemBuilderInterf withName(String name);

    public ItemBuilderInterf withDayDeadLine(Date date);
    
    public ItemBuilderInterf withStatuse(int status);

    public ItemBuilderInterf withComment(String comment);

    public ItemBuilderInterf withPriority(int priority);

    public ItemBuilderInterf withUser(User user);

    public ItemBuilderInterf withChatId(Long chatId);

    public Item build();
}   