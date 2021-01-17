INSERT INTO Users(name, password) VALUES ('Nikita', 'qwerty');
INSERT INTO Users(name, password) VALUES ('Ivan', 'qwerty');
INSERT INTO Users(name, password) VALUES ('BlockedUser', 'qwerty');
INSERT INTO Users(name, password) VALUES ('ReadonlyUser', 'qwerty');

INSERT INTO Messages(senderid, receiverid, message, timestamp) VALUES (1, 2, 'Hi', NOW());
INSERT INTO Messages(senderid, receiverid, message, timestamp) VALUES (2, 1, 'Hello', NOW());
INSERT INTO Messages(senderid, receiverid, message, timestamp) VALUES (1, 2, 'Sup?', NOW());