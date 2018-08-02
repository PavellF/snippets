CREATE TABLE events (
ssoid uuid PRIMARY KEY,
ts TIMESTAMP NOT NULL,
grp VARCHAR(64) NOT NULL,
type VARCHAR(64) NOT NULL,
subtype VARCHAR(64) NOT NULL,
url VARCHAR(1024) NOT NULL,
orgid VARCHAR(64) NOT NULL,
formid VARCHAR(64) NOT NULL,
code VARCHAR(64) NOT NULL,
ltpa VARCHAR(64) DEFAULT NULL,
sudirresponse VARCHAR(64) DEFAULT NULL,
ymdh TIMESTAMP NOT NULL

);
--ssoid – Уникальный идентификатор пользователей
--ts – Время
--grp - Группа события
--type – Тип события
--subtype – Подтип события
--url – Адрес с которого пришло событие
--orgid – Организация предоставляющая услугу
--formid – Идентификатор формы
--ltpa – Ключ сессии (в данном наборе пустой)
--sudirresponse – Ответ от сервиса авторизации (в данном наборе пустой)
--ymdh – Дата в формате YYYY-MM-DD-HH