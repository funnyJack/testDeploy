CREATE
    DATABASE IF NOT EXISTS testDeploy;
USE
    testDeploy;
CREATE TABLE IF NOT EXISTS test
(
    id
            BIGINT
        AUTO_INCREMENT,
    name
            VARCHAR(128) NOT NULL,
    message VARCHAR(128) NOT NULL,
    PRIMARY KEY
        (
         id
            )
);

INSERT INTO test VALUE (1, "鹏力", "你好，我叫鹏力");
