java.sql.SQLFeatureNotSupportedException: Метод org.postgresql.jdbc.PgConnection.createClob() ещё не реализован игнорировать
дать hibernate сгенерировать схему или воспользоваться pg_schema.sql (не забыть при этом отредактировать property фаил)

#Запуск с in-memory h2:
1. 

test_case.csv ожидается в ${user.dir}/test_case.csv

не валидные uuid заменены сгенерированными
отсутствие значения сопоставляется как пустая строка