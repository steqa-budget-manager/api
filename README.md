[ru](https://github.com/steqa-cashcache/api) [en](https://github.com/steqa-cashcache/.github/blob/main/locale/api/README.en.md)

# CashCache :coin: API
:warning: Этот репозиторий является частью проекта [CashCache](https://github.com/steqa-cashcache) :warning:  
Для информации по установке и запуску см. [основной README](https://github.com/steqa-cashcache)


![GitHub Release](https://img.shields.io/github/v/release/steqa-cashcache/api)
![License](https://img.shields.io/badge/license-MIT-green)


![Java](https://img.shields.io/badge/Java-f58312.svg?style=flat&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6db240?style=flat&logo=springboot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-326790.svg?style=flat&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ed?logo=docker&logoColor=white)
![gRPC](https://img.shields.io/badge/gRPC-2ca6af.svg?style=flat&logo=grpc&logoColor=white)


## Содержание
1. [Описание](#описание)
2. [Возможности](#возможности)
3. [REST API Эндпоинты](#rest-api-эндпоинты)
4. [gRPC Взаимодействие](#grpc-взаимодействие)
5. [Лицензия](#лицензия)

## Описание
Основной REST API для управления личными финансами.  
Позволяет работать со счетами, транзакциями, шаблонами и регулярными операциями.

## Возможности
- Регистрация и аутентификация (JWT)
- Управление счетами
- Управление категориями транзакций
- Управление транзакциями и переводами между счетами
- Управление шаблонами транзакций
- Поддержка регулярных транзакций
- gRPC интерфейс для взаимодействия с микросервисом [Repetition Service](https://github.com/steqa-cashcache/repetition-service)


## REST API Эндпоинты

### Аутентификация  
| Метод | Путь                 | Описание                                 |
| ----- | -------------------- | ---------------------------------------- |
| POST  | /api/v1/auth/signup  | Регистрация пользователя                 |
| POST  | /api/v1/auth/login   | Вход, получение access и refresh токенов |
| POST  | /api/v1/auth/refresh | Обновление токенов                       |

### Аккаунты  
| Метод | Путь | Описание |
|-------|-------|---------|
| GET   | /api/v1/accounts | Получить все аккаунты |
| GET   | /api/v1/accounts/{id} | Получить аккаунт по ID |
| POST  | /api/v1/accounts | Создать новый аккаунт |
| PATCH | /api/v1/accounts/{id} | Обновить аккаунт |
| DELETE| /api/v1/accounts/{id} | Удалить аккаунт |
| GET   | /api/v1/accounts/balances | Получить балансы по аккаунтам |

### Категории транзакций  
| Метод  | Путь                                 | Описание                 |
| ------ | ------------------------------------ | ------------------------ |
| GET    | /api/v1/transactions/categories      | Получить все категории   |
| GET    | /api/v1/transactions/categories/{id} | Получить категорию по ID |
| POST   | /api/v1/transactions/categories      | Создать категорию        |
| PATCH  | /api/v1/transactions/categories/{id} | Обновить категорию       |
| DELETE | /api/v1/transactions/categories/{id} | Удалить категорию        |

### Транзакции  
| Метод | Путь | Описание |
|-------|-------|---------|
| GET   | /api/v1/transactions | Получить все транзакции |
| GET   | /api/v1/transactions/{id} | Получить транзакцию по ID |
| POST  | /api/v1/transactions | Создать транзакцию |
| PATCH | /api/v1/transactions/{id} | Обновить транзакцию |
| DELETE| /api/v1/transactions/{id} | Удалить транзакцию |

### Шаблоны транзакций  
| Метод | Путь | Описание |
|-------|-------|---------|
| GET   | /api/v1/transactions/templates | Получить все шаблоны |
| GET   | /api/v1/transactions/templates/{id} | Получить шаблон по ID |
| POST  | /api/v1/transactions/templates | Создать шаблон |
| PATCH | /api/v1/transactions/templates/{id} | Обновить шаблон |
| DELETE| /api/v1/transactions/templates/{id} | Удалить шаблон |

### Переводы  
| Метод | Путь | Описание |
|-------|-------|---------|
| GET   | /api/v1/transfers | Получить все переводы |
| GET   | /api/v1/transfers/{id} | Получить перевод по ID |
| POST  | /api/v1/transfers | Создать перевод |
| PATCH | /api/v1/transfers/{id} | Обновить перевод |
| DELETE| /api/v1/transfers/{id} | Удалить перевод |

### Регулярные транзакции  
| Метод | Путь | Описание |
|-------|-------|---------|
| GET   | /api/v1/transactions/regulars | Получить все регулярные транзакции |
| GET   | /api/v1/transactions/regulars/{id} | Получить регулярную транзакцию по ID |
| POST  | /api/v1/transactions/regulars | Создать регулярную транзакцию |
| PATCH | /api/v1/transactions/regulars/{id} | Обновить регулярную транзакцию |
| DELETE| /api/v1/transactions/regulars/{id} | Удалить регулярную транзакцию |


## gRPC Взаимодействие

API-сервис предоставляет gRPC сервис `ApiService`, который используется сервисом повторений для добавления транзакций на основе правил.

[Repetition Service](https://github.com/steqa-cashcache/repetition-service) вызывает `AddTransaction`, чтобы зафиксировать транзакцию, созданную по правилу.

Это позволяет API-сервису хранить и отображать все транзакции, включая регулярные.


## Лицензия
Этот проект распространяется под лицензией MIT. Подробнее см. в файле [LICENSE](LICENSE).
