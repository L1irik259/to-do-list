package org.example.model;

import java.util.Date;

import org.example.classBulder.ItemBuilder;

import jakarta.persistence.*;

@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idItem;

    private String name;

    private Date dayDeadLine;
    
    /**
     * 1 -- статус, когда к задаче не приступили 
     * 2 -- в процессе
     * 3 -- выполненно 
     */
    private int status; 
    
    private String comment; 
    
    /**
     * 0 -- без приоритета 
     * 1 -- самая неважная 
     * 2 -- средняя
     * 3 -- важная 
     */
    private int priority;

    private Long chatId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_item")
    private User user;

    public Item () {
        this.name = null;
        this.dayDeadLine = null;
        this.status = 0;
        this.comment = null;
        this.priority = 0;
        this.chatId = null;
    }

    public Item (ItemBuilder itemBuilder) { 
        this.name = itemBuilder.getName();
        this.dayDeadLine = itemBuilder.getDayDeadLine();
        this.status = itemBuilder.getStatus();
        this.comment = itemBuilder.getComment();
        this.priority = itemBuilder.getPriority();
        this.chatId = itemBuilder.getChatId();
    }

    public Item(String name, Date dayDeadLine, int status, String comment, int priority, User User, Long chatId) {
        this.name = name;
        this.dayDeadLine = dayDeadLine;
        this.status = status;
        this.comment = comment;
        this.priority = priority;
        this.user = User;
        this.chatId = chatId;
    }

    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDayDeadLine() {
        return dayDeadLine;
    }

    public void setDayDeadLine(Date dayDeadLine) {
        this.dayDeadLine = dayDeadLine;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

}
