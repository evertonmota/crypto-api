CREATE TABLE coin (
    ID INTEGER PRIMARY KEY AUTO_INCREMENT,
    NAME VARCHAR(100),
    PRICE NUMERIC(20,2),
    QUANTITY NUMERIC(20,10),
    DATETIME TIMESTAMP
);