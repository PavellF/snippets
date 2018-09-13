CREATE TABLE "user" (
	"id" serial NOT NULL,
	"email" varchar(64) NOT NULL UNIQUE,
	"password" varchar(64) NOT NULL,
	"role" varchar(36) NOT NULL,
	"date" TIMESTAMP NOT NULL,
	CONSTRAINT user_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "chat_message" (
	"id" serial,
	"author" integer NOT NULL,
	"chat_id" integer NOT NULL,
	"date" TIMESTAMP NOT NULL,
	"message" varchar(16384) NOT NULL,
	"status" varchar(64) NOT NULL,
	CONSTRAINT chat_message_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "chat" (
	"id" serial NOT NULL,
	"opener" integer NOT NULL,
	"chat_with" integer NOT NULL,
	"title" varchar(36) NOT NULL,
	CONSTRAINT chat_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);




ALTER TABLE "chat_message" ADD CONSTRAINT "chat_message_fk0" FOREIGN KEY ("author") REFERENCES "user"("id");
ALTER TABLE "chat_message" ADD CONSTRAINT "chat_message_fk1" FOREIGN KEY ("chat_id") REFERENCES "chat"("id");

ALTER TABLE "chat" ADD CONSTRAINT "chat_fk0" FOREIGN KEY ("opener") REFERENCES "user"("id");
ALTER TABLE "chat" ADD CONSTRAINT "chat_fk1" FOREIGN KEY ("chat_with") REFERENCES "user"("id");
