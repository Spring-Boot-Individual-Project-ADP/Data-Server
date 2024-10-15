insert into CUSTOMERS (CUSTOMER_NAME, EMAIL, PASSWORD) VALUES ('Bruce', 'bruce@example.com', 'password1');
insert into CUSTOMERS (CUSTOMER_NAME, EMAIL, PASSWORD) VALUES ('Paul', 'paul@example.com', 'password2');
insert into CUSTOMERS (CUSTOMER_NAME, EMAIL, PASSWORD) VALUES ('Rick', 'rick@example.com', 'password3');

insert into EVENTS (EVENT_CODE, TITLE, DESCRIPTION) VALUES ('CNA001', 'Games night', 'Really fun event!');
insert into EVENTS (EVENT_CODE, TITLE, DESCRIPTION) VALUES ('CNB001', 'Standup', 'Not as fun event');

insert into REGISTRATIONS (EVENT_ID, CUSTOMER_ID, REGISTRATION_DATE, NOTES) VALUES (1, 1, '2019-01-17 00:00:00.0', 'Very fun');
