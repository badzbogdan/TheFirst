
В проекте реализована основная задача + все бонусные задачи

Созданы отдельные ветки для:
	- валидация email-адреса (ветка EmailChecker (задача №3) смержена с веткой master)
	- бонусная задача №1 (ветка SQLiteSupport) в этой же ветке реализована бонусная задача №2 (с использованием SharedPreferences)

Описание классов:
	Экран №1 (стартовый): MainPage.java
	Экран №2 (регистрация): RegistrationPage.java
	Экран №3 (вход): LoginPage.java
	AccountDB.java - содержит методы для работы с базой данных SQLite (на ветке master реализация через HashMup)
		addAccount(String email, String md5Pass) - добавление новой записи в базу
		getPass(String email) - получение md5 пароля из базы
		containsEmail(String email) - проверка наличия email адреса в базе
	AccaountManager.java - страницы RegistrationPage и LoginPage взаимодействуют с базой через этот класс,
		реализованы методы проверки валидности пароля и email адреса, конвертация в md5 а также хранение текущей сессии

build.apk проверял на устройствах с версией Android v5.0.2 и v6.0.1

ссылка на github: https://github.com/badzbogdan/TheFirst
