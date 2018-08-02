# Запуск с in-memory h2

1. открыть в любимом ide
2. раскомментировать первую строку application.properties, отредактировать application-dev.properties если нужно
3. зайти на localhost:8080

# запуск с postgres 

1. открыть в любимом ide
2. убедится что все проперти устовновлены верно в application.properties (первая строка должна быть закомментирована)
3. постгрес должен быть запущен
4. зайти на localhost:8080

Выскочит следующий exception - игнорировать:

`java.sql.SQLFeatureNotSupportedException: Метод org.postgresql.jdbc.PgConnection.createClob() ещё не реализован`

# примечания

- test_case.csv ожидается в ${user.dir}/test_case.csv
- не валидные uuid заменены сгенерированными
- отсутствие значения в csv сопоставляется как пустая строка
- через браузер апи не протестировать, так как апи версионируется через кастомный header, используй postman
- можно дать hibernate сгенерировать схему или воспользоваться pg_schema.sql (не забыть при этом отредактировать property фаил)
