# to-do-list

## 📌 Описание проекта

Данный проект реализует систему управления задачами (items) с использованием паттерна Builder, ORM-решения Hibernate и DAO-архитектуры. Каждая задача может быть связана с конкретным пользователем, имеет дедлайн, статус, комментарий и приоритет.

---

## 🛠️ Стек технологий

- Java 17+
- Hibernate ORM
- PostgreSQL / любая JDBC-совместимая БД
- Maven
- JPA (Jakarta Persistence API)
- DAO-паттерн
- Builder-паттерн
- Swing (JOptionPane для уведомлений)

---

## 🧩 Структура проекта

```
src/
└── org.example/
    ├── model/                # Модели данных (User, Item)
    ├── dao/                  # Реализации DAO для Item и User
    ├── interfaces/           # Интерфейс DefaultDao
    ├── interfacesBuilder/    # Интерфейс билдера Item
    ├── classBuilder/         # Класс ItemBuilder
    └── util/                 # HibernateUtil (фабрика сессий)
```

---

## 📦 Возможности

- Создание задач через `ItemBuilder`
- CRUD-операции над `Item` и `User` через DAO
- Привязка задач к пользователю
- Хранение и извлечение из БД через Hibernate

---

## 🧱 Пример использования

```java
User user = new User("login", "password");
Item item = new ItemBuilder()
    .withName("Сделать ДЗ")
    .withDayDeadLine(new Date())
    .withStatuse(1)
    .withComment("Важно закончить до выходных")
    .withPriority(3)
    .withUser(user)
    .withChatId(123456789L)
    .build();

ItemDaoImpl itemDao = new ItemDaoImpl();
itemDao.save(item);
```

---

## 🗃️ Описание полей `Item`

- `status`:
  - `1` — Не начато
  - `2` — В процессе
  - `3` — Завершено
- `priority`:
  - `0` — Без приоритета
  - `1` — Низкий
  - `2` — Средний
  - `3` — Высокий

---

## ⚙️ Настройка Hibernate

Убедитесь, что в `hibernate.cfg.xml` указаны корректные параметры подключения к вашей БД:

```xml
<property name="hibernate.connection.url">jdbc:postgresql://localhost:5432/your_database</property>
<property name="hibernate.connection.username">your_username</property>
<property name="hibernate.connection.password">your_password</property>
<property name="hibernate.dialect">org.hibernate.dialect.PostgreSQLDialect</property>
```

---

## ❗ Возможные доработки

- Добавить уровни доступа (админ/пользователь)
- Реализовать UI или Telegram-бота

---

## 🧑‍💻 Автор

Разработчик: Миронов Кирилл  
Контакты: mironovkirill259@gmail.com
