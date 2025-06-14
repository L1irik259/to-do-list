package org.example;

import org.example.classBulder.ItemBuilder;
import org.example.dao.ItemDaoImpl;
import org.example.model.Item;
import org.example.model.User;
import org.example.util.HibernateUtil;
import org.hibernate.SessionFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Date;

public class AppTest {

    private static ItemDaoImpl itemDao;

    @Test 
    public void ItemTest() {
        String name = "Test task";
        Date deadline = new Date(20-9-2005);
        int status = 2;
        String comment = "В процессе";
        int priority = 3;
        Long chatId = 123456789L;

        User user = new User();
        Item item = new Item(name, deadline, status, comment, priority, user, chatId);

        assertEquals(name, item.getName());
        assertEquals(deadline, item.getDayDeadLine());
        assertEquals(status, item.getStatus());
        assertEquals(comment, item.getComment());
        assertEquals(priority, item.getPriority());
        assertEquals(user, item.getUser());
        assertEquals(chatId, item.getChatId());
    }

    @Test
    public void testSettersAndGetters() {
        Item item = new Item("tmp", new Date(), 1, "tmp", 0, null, 0L);

        item.setName("Updated");
        item.setStatus(3);
        item.setComment("Done");
        item.setPriority(2);
        item.setChatId(999L);
        Date newDate = new Date();
        item.setDayDeadLine(newDate);
        User user = new User();
        item.setUser(user);

        assertEquals("Updated", item.getName());
        assertEquals(3, item.getStatus());
        assertEquals("Done", item.getComment());
        assertEquals(2, item.getPriority());
        assertEquals((Long) 999L, item.getChatId());
        assertEquals(newDate, item.getDayDeadLine());
        assertEquals(user, item.getUser());
    }

    @Test
    public void testItemBuilderCreatesCorrectItem() {
        String name = "Build me";
        Date deadline = new Date();
        int status = 1;
        String comment = " ";
        int priority = 2;
        Long chatId = 888L;

        ItemBuilder builder = (ItemBuilder) new ItemBuilder()
                .withName(name)
                .withDayDeadLine(deadline)
                .withStatuse(status)
                .withComment(comment)
                .withPriority(priority)
                .withChatId(chatId);

        Item item = builder.build();

        assertEquals(name, item.getName());
        assertEquals(deadline, item.getDayDeadLine());
        assertEquals(status, item.getStatus());
        assertEquals(comment, item.getComment());
        assertEquals(priority, item.getPriority());
        assertEquals(chatId, item.getChatId());
    }

    @BeforeClass
    public static void setup() {
        itemDao = new ItemDaoImpl();
    }

    @AfterClass
    public static void tearDown() {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        if (factory != null) {
            factory.close();
        }
    }

    @Test
    public void testSaveAndGetById() {
        ItemBuilder builder = (ItemBuilder) new ItemBuilder()
                .withName("Test Item")
                .withDayDeadLine(new Date())
                .withStatuse(1)
                .withComment("Test comment")
                .withPriority(2)
                .withChatId(123L);

        Item item = builder.build();
        itemDao.save(item);

        assertTrue(item.getIdItem() > 0);

        Item fromDb = itemDao.getById(item.getIdItem());

        assertNotNull(fromDb);
        assertEquals("Test Item", fromDb.getName());
        assertEquals("Test comment", fromDb.getComment());
    }

    @Test
    public void testUpdate() {
        Item item = new ItemBuilder()
                .withName("Old Name")
                .withDayDeadLine(new Date())
                .withStatuse(1)
                .withComment("Old comment")
                .withPriority(2)
                .withChatId(999L)
                .build();

        itemDao.save(item);

        item.setName("Updated Name");
        item.setComment("Updated comment");

        Item updated = itemDao.update(item);

        assertEquals("Updated Name", updated.getName());
        assertEquals("Updated comment", updated.getComment());
    }

    @Test
    public void testDeleteById() {
        Item item = new ItemBuilder()
                .withName("To be deleted")
                .withDayDeadLine(new Date())
                .withStatuse(1)
                .withComment("Trash")
                .withPriority(1)
                .withChatId(666L)
                .build();

        itemDao.save(item);
        int id = item.getIdItem();
        itemDao.deleteById(id);

        Item deleted = itemDao.getById(id);
        assertNull(deleted);
    }
}
