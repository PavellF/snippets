CREATE TABLE "user" (
    "id" serial NOT NULL,
    "email" varchar(64) NOT NULL UNIQUE,
    "password" varchar(36) NOT NULL,
    "role" varchar(36) NOT NULL,
    CONSTRAINT user_pk PRIMARY KEY ("id")
);



CREATE TABLE "chat_message" (
    "id" serial NOT NULL,
    "author" integer NOT NULL,
    "chat_id" integer NOT NULL,
    "date" TIMESTAMP NOT NULL,
    "message" varchar(16384) NOT NULL,
    "status" varchar(36) NOT NULL,
    CONSTRAINT chat_message_pk PRIMARY KEY ("id")
);



CREATE TABLE "chat" (
    "id" serial NOT NULL,
    "title" varchar(36) NOT NULL,
    CONSTRAINT chat_pk PRIMARY KEY ("id")
);



CREATE TABLE "chat_member" (
    "id" serial NOT NULL,
    "member_id" integer NOT NULL,
    "role_in_chat" varchar(36) NOT NULL,
    "chat_id" integer(36) NOT NULL,
    CONSTRAINT chat_member_pk PRIMARY KEY ("id")
);




ALTER TABLE "chat_message" ADD CONSTRAINT "chat_message_fk0" FOREIGN KEY ("author") REFERENCES "user"("id");
ALTER TABLE "chat_message" ADD CONSTRAINT "chat_message_fk1" FOREIGN KEY ("chat_id") REFERENCES "chat"("id");


ALTER TABLE "chat_member" ADD CONSTRAINT "chat_member_fk0" FOREIGN KEY ("member_id") REFERENCES "user"("id");
ALTER TABLE "chat_member" ADD CONSTRAINT "chat_member_fk1" FOREIGN KEY ("chat_id") REFERENCES "chat"("id");
ALTER TABLE "chat_member" ADD CONSTRAINT "chat_member_one_member_to_chat" UNIQUE ("chat_id", "member_id");
