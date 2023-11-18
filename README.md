# APIAutotest_SDET

## Тест-кейс. Создание поста
#### Предусловие:
1. Развернуть проект WordPress локально согласно инструкции: [https://drive.google.com/file/d/1AtQP_ZMiHeUmGX6zE4PVr2P2JFaOiaxP/view](https://drive.google.com/file/d/1AtQP_ZMiHeUmGX6zE4PVr2P2JFaOiaxP/view)
2. Получить логин и пароль для авторизации через Basic Authentification
3. Headers request:
- Content-Type: application/json  
- Accept: application/json  
- Authorization: Basic Auth

| Шаги                                                                                                           | Ожидаемый результат                                                       |
| ---------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------|
| Выполнить POST запрос **httр://localhost:8000/index.php?rest_route=/wp/v2/posts** с телом в формате JSON: <br> { <br> "title": "Пост", <br> "content": "Описание", <br> "status": "publish" <br> } | Запрос успешно отправлен на сервер |
| Проверить код состояния | HTTP Status: 201 Created |
| Проверить тело ответа от сервера | Тело ответа возвращается в формате JSON от сервера и значения полей "title", "content", "status" соответствуют заданным в запросе, ~~а значение поля "type" равно значению "post"~~ |

## Тест-кейс. Редактирование поста
#### Предусловие:
1. Развернуть проект WordPress локально согласно инструкции: [https://drive.google.com/file/d/1AtQP_ZMiHeUmGX6zE4PVr2P2JFaOiaxP/view](https://drive.google.com/file/d/1AtQP_ZMiHeUmGX6zE4PVr2P2JFaOiaxP/view)
2. Получить логин и пароль для авторизации через Basic Authentification
3. Headers request:
- Content-Type: application/json  
- Accept: application/json  
- Authorization: Basic Auth

| Шаги                                                                                                           | Ожидаемый результат                                                       |
| ---------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------|
| Выполнить POST запрос **httр://localhost:8000/index.php?rest_route=/wp/v2/posts/\<id>** с телом в формате JSON: <br> { <br> "title": "My New Title" <br> } | Запрос успешно отправлен на сервер |
| Проверить код состояния | HTTP Status: 200 OK |
| Проверить тело ответа от сервера | Тело ответа возвращается в формате JSON от сервера и значения полей "id", "title" соответствуют заданным в запросе, ~~а значение поля "type" равно значению "post"~~ |

## Тест-кейс. Удаление поста
#### Предусловие:
1. Развернуть проект WordPress локально согласно инструкции: [https://drive.google.com/file/d/1AtQP_ZMiHeUmGX6zE4PVr2P2JFaOiaxP/view](https://drive.google.com/file/d/1AtQP_ZMiHeUmGX6zE4PVr2P2JFaOiaxP/view)
2. Получить логин и пароль для авторизации через Basic Authentification
3. Headers request:
- Content-Type: application/json  
- Accept: application/json  
- Authorization: Basic Auth

| Шаги                                                                                                           | Ожидаемый результат                                                       |
| ---------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------|
| Выполнить DELETE запрос **httр://localhost:8000/index.php?rest_route=/wp/v2/posts/\<id>**                      | Запрос успешно отправлен на сервер |
| Проверить код состояния | HTTP Status: 200 OK |
| Проверить тело ответа от сервера | Тело ответа возвращается в формате JSON от сервера и значение поля "id" соответствует заданному в запросе, значение поля "status" равно значению "trash", ~~а значение поля "type" равно значению "post"~~ |

## Тест-кейс. Создание комментария
#### Предусловие:
1. Развернуть проект WordPress локально согласно инструкции: [https://drive.google.com/file/d/1AtQP_ZMiHeUmGX6zE4PVr2P2JFaOiaxP/view](https://drive.google.com/file/d/1AtQP_ZMiHeUmGX6zE4PVr2P2JFaOiaxP/view)
2. Получить логин и пароль для авторизации через Basic Authentification
3. Headers request:
- Content-Type: application/json  
- Accept: application/json  
- Authorization: Basic Auth

| Шаги                                                                                                           | Ожидаемый результат                                                       |
| ---------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------|
| Выполнить POST запрос **httр://localhost:8000/index.php?rest_route=/wp/v2/comments** с телом в формате JSON: <br> { <br> "content": "Комментарий", <br> "status": "approved", <br> "post": "1" <br> } | Запрос успешно отправлен на сервер |
| Проверить код состояния | HTTP Status: 201 Created |
| Проверить тело ответа от сервера | Тело ответа возвращается в формате JSON от сервера и значения полей "content", "status", "post" соответствуют заданным в запросе, ~~а значение поля "type" равно значению "comment"~~ |

## Тест-кейс. Редактирование комментария
#### Предусловие:
1. Развернуть проект WordPress локально согласно инструкции: [https://drive.google.com/file/d/1AtQP_ZMiHeUmGX6zE4PVr2P2JFaOiaxP/view](https://drive.google.com/file/d/1AtQP_ZMiHeUmGX6zE4PVr2P2JFaOiaxP/view)
2. Получить логин и пароль для авторизации через Basic Authentification
3. Headers request:
- Content-Type: application/json  
- Accept: application/json  
- Authorization: Basic Auth

| Шаги                                                                                                           | Ожидаемый результат                                                       |
| ---------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------|
| Выполнить POST запрос **httр://localhost:8000/index.php?rest_route=/wp/v2/comments/\<id>** с телом в формате JSON: <br> { <br> "content": "Изменённый комментарий" <br> } | Запрос успешно отправлен на сервер |
| Проверить код состояния | HTTP Status: 200 OK |
| Проверить тело ответа от сервера | Тело ответа возвращается в формате JSON от сервера и значения полей "id", "content" соответствуют заданным в запросе, ~~а значение поля "type" равно значению "comment"~~ |

## Тест-кейс. Удаление комментария
#### Предусловие:
1. Развернуть проект WordPress локально согласно инструкции: [https://drive.google.com/file/d/1AtQP_ZMiHeUmGX6zE4PVr2P2JFaOiaxP/view](https://drive.google.com/file/d/1AtQP_ZMiHeUmGX6zE4PVr2P2JFaOiaxP/view)
2. Получить логин и пароль для авторизации через Basic Authentification
3. Headers request:
- Content-Type: application/json  
- Accept: application/json  
- Authorization: Basic Auth

| Шаги                                                                                                           | Ожидаемый результат                                                       |
| ---------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------|
| Выполнить DELETE запрос **httр://localhost:8000/index.php?rest_route=/wp/v2/comments/\<id>**                   | Запрос успешно отправлен на сервер |
| Проверить код состояния | HTTP Status: 200 OK |
| Проверить тело ответа от сервера | Тело ответа возвращается в формате JSON от сервера и значение поля "id" соответствует заданному в запросе, значение поля "status" равно значению "trash", ~~значение поля "type" равно значению "comment"~~ |
