# APIAutotest_SDET
Автоматизированное тестирование API и БД тренировочного проекта **WordPress**. Подробную документацию к API WordPress можно получить на [офф. сайте](https://developer.wordpress.org/rest-api/).
## Используемые инструменты:
1. Язык программирования *Java 17*
2. IntelliJ IDEA Community Edition 2023.2
3. Сборщик *Maven*
4. Тестовый фреймворк *TestNG*
5. Инструмент для тестирования API *REST-assured*
6. JDBC *MySQL Connector Java*
7. Библиотека *Jackson*
8. Библиотека *Lombok*
9. Фреймворк *Allure*
10. Программное обеспечение *Docker*
11. Программное обеспечение *DBeaver*
## Как начать
Для использования проекта нужен Docker и Docker-compose.  
Инструкция по развертыванию проекта: [https://drive.google.com/file/d/1AtQP_ZMiHeUmGX6zE4PVr2P2JFaOiaxP/view](https://drive.google.com/file/d/1AtQP_ZMiHeUmGX6zE4PVr2P2JFaOiaxP/view)  
После развертывания проекта для доступа к API WordPress можно воспользоваться ссылкой: [http://localhost:8000/index.php?rest_route=/](http://localhost:8000/index.php?rest_route=/)

## Тест-кейс. Создание поста
#### Предусловие:
1. Развернуть проект WordPress локально согласно инструкции: [https://drive.google.com/file/d/1AtQP_ZMiHeUmGX6zE4PVr2P2JFaOiaxP/view](https://drive.google.com/file/d/1AtQP_ZMiHeUmGX6zE4PVr2P2JFaOiaxP/view)
2. Получить логин и пароль для авторизации через Basic Authentification
3. Headers request:
- Content-Type: application/json  
- Accept: application/json  
- Authorization: Basic Auth
#### Постусловие:
Удалить пост с помощью sql-запроса:  
"DELETE FROM wp_posts ORDER BY ID DESC LIMIT 1;"
| Шаги                                                                                                           | Ожидаемый результат                                                       |
| ---------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------|
| Выполнить POST запрос **httр://localhost:8000/index.php?rest_route=/wp/v2/posts** с телом в формате JSON: <br> { <br> "title": "Title", <br> "content": "Description", <br> "status": "publish" <br> } | Запрос успешно отправлен на сервер |
| Проверить код состояния | HTTP Status: 201 Created |
| Проверить тело ответа от сервера | Тело ответа возвращается в формате JSON от сервера и значения полей "title", "content", "status" соответствуют заданным в запросе, а значение поля "type" равно значению "post" |

## Тест-кейс. Редактирование поста
#### Предусловие:
1. Развернуть проект WordPress локально согласно инструкции: [https://drive.google.com/file/d/1AtQP_ZMiHeUmGX6zE4PVr2P2JFaOiaxP/view](https://drive.google.com/file/d/1AtQP_ZMiHeUmGX6zE4PVr2P2JFaOiaxP/view)
2. Получить логин и пароль для авторизации через Basic Authentification
3. Headers request:
- Content-Type: application/json  
- Accept: application/json  
- Authorization: Basic Auth
4. Создать пост с помощью sql-запроса:  
"INSERT INTO wp_posts (post_date, post_date_gmt, post_content, post_title, post_excerpt, to_ping, pinged, post_modified, post_modified_gmt, post_content_filtered) VALUES (NOW(), NOW(), 'Описание', 'Пост', '', '', '', NOW(), NOW(), '');"
#### Постусловие:
Удалить пост с помощью sql-запроса:  
"DELETE FROM wp_posts ORDER BY ID DESC LIMIT 1;"

| Шаги                                                                                                           | Ожидаемый результат                                                       |
| ---------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------|
| Выполнить POST запрос **httр://localhost:8000/index.php?rest_route=/wp/v2/posts/\<id>** с телом в формате JSON: <br> { <br> "title": "Edit Title" <br> } | Запрос успешно отправлен на сервер |
| Проверить код состояния | HTTP Status: 200 OK |
| Проверить тело ответа от сервера | Тело ответа возвращается в формате JSON от сервера и значения полей "id", "title" соответствуют заданным в запросе, а значение поля "type" равно значению "post" |

## Тест-кейс. Удаление поста
#### Предусловие:
1. Развернуть проект WordPress локально согласно инструкции: [https://drive.google.com/file/d/1AtQP_ZMiHeUmGX6zE4PVr2P2JFaOiaxP/view](https://drive.google.com/file/d/1AtQP_ZMiHeUmGX6zE4PVr2P2JFaOiaxP/view)
2. Получить логин и пароль для авторизации через Basic Authentification
3. Headers request:
- Content-Type: application/json  
- Accept: application/json  
- Authorization: Basic Auth
4. Создать пост с помощью sql-запроса:  
"INSERT INTO wp_posts (post_date, post_date_gmt, post_content, post_title, post_excerpt, to_ping, pinged, post_modified, post_modified_gmt, post_content_filtered) VALUES (NOW(), NOW(), 'Описание', 'Пост', '', '', '', NOW(), NOW(), '');"

| Шаги                                                                                                           | Ожидаемый результат                                                       |
| ---------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------|
| Выполнить DELETE запрос **httр://localhost:8000/index.php?rest_route=/wp/v2/posts/\<id>**                      | Запрос успешно отправлен на сервер |
| Проверить код состояния | HTTP Status: 200 OK |
| Проверить тело ответа от сервера | Тело ответа возвращается в формате JSON от сервера и значение поля "id" соответствует заданному в запросе, значение поля "status" равно значению "trash", а значение поля "type" равно значению "post" |

## Тест-кейс. Создание комментария
#### Предусловие:
1. Развернуть проект WordPress локально согласно инструкции: [https://drive.google.com/file/d/1AtQP_ZMiHeUmGX6zE4PVr2P2JFaOiaxP/view](https://drive.google.com/file/d/1AtQP_ZMiHeUmGX6zE4PVr2P2JFaOiaxP/view)
2. Получить логин и пароль для авторизации через Basic Authentification
3. Headers request:
- Content-Type: application/json  
- Accept: application/json  
- Authorization: Basic Auth
#### Постусловие:
Удалить комментарий с помощью sql-запроса:  
"DELETE FROM wp_comments ORDER BY comment_ID DESC LIMIT 1;"

| Шаги                                                                                                           | Ожидаемый результат                                                       |
| ---------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------|
| Выполнить POST запрос **httр://localhost:8000/index.php?rest_route=/wp/v2/comments** с телом в формате JSON: <br> { <br> "content": "Content", <br> "status": "approved", <br> "post": "1" <br> } | Запрос успешно отправлен на сервер |
| Проверить код состояния | HTTP Status: 201 Created |
| Проверить тело ответа от сервера | Тело ответа возвращается в формате JSON от сервера и значения полей "content", "status", "post" соответствуют заданным в запросе, а значение поля "type" равно значению "comment" |

## Тест-кейс. Редактирование комментария
#### Предусловие:
1. Развернуть проект WordPress локально согласно инструкции: [https://drive.google.com/file/d/1AtQP_ZMiHeUmGX6zE4PVr2P2JFaOiaxP/view](https://drive.google.com/file/d/1AtQP_ZMiHeUmGX6zE4PVr2P2JFaOiaxP/view)
2. Получить логин и пароль для авторизации через Basic Authentification
3. Headers request:
- Content-Type: application/json  
- Accept: application/json  
- Authorization: Basic Auth
4. Создать комментарий с помощью sql-запроса:  
"INSERT INTO wp_comments (comment_author, comment_date, comment_date_gmt, comment_content) VALUES ('Eva.Pligina', NOW(), NOW(), 'Text');"
#### Постусловие:
Удалить комментарий с помощью sql-запроса:  
"DELETE FROM wp_comments ORDER BY comment_ID DESC LIMIT 1;"

| Шаги                                                                                                           | Ожидаемый результат                                                       |
| ---------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------|
| Выполнить POST запрос **httр://localhost:8000/index.php?rest_route=/wp/v2/comments/\<id>** с телом в формате JSON: <br> { <br> "content": "Edit content" <br> } | Запрос успешно отправлен на сервер |
| Проверить код состояния | HTTP Status: 200 OK |
| Проверить тело ответа от сервера | Тело ответа возвращается в формате JSON от сервера и значения полей "id", "content" соответствуют заданным в запросе, а значение поля "type" равно значению "comment" |

## Тест-кейс. Удаление комментария
#### Предусловие:
1. Развернуть проект WordPress локально согласно инструкции: [https://drive.google.com/file/d/1AtQP_ZMiHeUmGX6zE4PVr2P2JFaOiaxP/view](https://drive.google.com/file/d/1AtQP_ZMiHeUmGX6zE4PVr2P2JFaOiaxP/view)
2. Получить логин и пароль для авторизации через Basic Authentification
3. Headers request:
- Content-Type: application/json  
- Accept: application/json  
- Authorization: Basic Auth
4. Создать комментарий с помощью sql-запроса:  
"INSERT INTO wp_comments (comment_author, comment_date, comment_date_gmt, comment_content) VALUES ('Eva.Pligina', NOW(), NOW(), 'Text');"

| Шаги                                                                                                           | Ожидаемый результат                                                       |
| ---------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------|
| Выполнить DELETE запрос **httр://localhost:8000/index.php?rest_route=/wp/v2/comments/\<id>**                   | Запрос успешно отправлен на сервер |
| Проверить код состояния | HTTP Status: 200 OK |
| Проверить тело ответа от сервера | Тело ответа возвращается в формате JSON от сервера и значение поля "id" соответствует заданному в запросе, значение поля "status" равно значению "trash", значение поля "type" равно значению "comment" |

## Реализация формирования отчета Allure
Для формирования отчетов Allure необходимо в файле *pom.xml* прописать специальные разделы сборки.
#### Необходимые команды для генерации отчета:
1. `mvn clean test` - проведите тесты
2. `mvn allure:serve` - создать отчет

Таким образом откроется окно в браузере с информацией по отчету Allure.
## Параметризация
В проекте используется *аннотация параметров в TestNG* — метод, используемый для передачи значений методам тестирования в качестве аргументов с использованием файла *.xml*.  
В тесте нужно добавить аннотацию **@Parameters**, а в *.xml* файле добавить необходимые параметры в тегах `<parameter>`.  

Таким образом в проекте в качестве параметра передается тип ресурса *WordPress* для определения дальнейших действий. Использование параметризации было предпринято с целью уменьшения одинакового кода в классе *Steps*.

## Автор
Работу выполнила *Плигина Эвелина*
