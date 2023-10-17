use vtube_webapp;
ALTER TABLE user
    ADD COLUMN name varchar(255);

ALTER TABLE user
    MODIFY COLUMN avatar varchar(10000);