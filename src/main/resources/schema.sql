CREATE TABLE "CUSTOMERS" (
    "ID" INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    "CUSTOMER_NAME" VARCHAR(255),
    EMAIL VARCHAR(255),
    PASSWORD VARCHAR(255),
    PRIMARY KEY ("ID")
);

CREATE TABLE "EVENTS" (
    "ID" INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    "EVENT_CODE" VARCHAR(255),
    "TITLE" VARCHAR(255),
    "DESCRIPTION" VARCHAR(255),
    PRIMARY KEY ("ID")
);

CREATE TABLE "REGISTRATIONS" (
    "ID" INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
     "EVENT_ID" INTEGER NOT NULL ,
     "CUSTOMER_ID" INTEGER NOT NULL,
     "REGISTRATION_DATE" TIMESTAMP,
    "NOTES" VARCHAR(255),
    PRIMARY KEY ("ID"),
    FOREIGN KEY ("EVENT_ID") REFERENCES "EVENTS" ("ID"),
    FOREIGN KEY ("CUSTOMER_ID") REFERENCES "CUSTOMERS" ("ID")
);
