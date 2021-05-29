CREATE TABLE `MEETING_SCHEDULES` (
    `ID` BIGINT NOT NULL AUTO_INCREMENT,
    `THERAPIST_ID` BIGINT NOT NULL,
    `CLIENT_ID` BIGINT NOT NULL,
    `WHEN` TEXT NOT NULL,
    `DURATION` INT NOT NULL,
    `TODO` TEXT,
    PRIMARY KEY (`ID`),
    KEY (`THERAPIST_ID`)
);

CREATE TABLE `USER` (
    `ID` BIGINT NOT NULL AUTO_INCREMENT,
    `DISPLAY_NAME` TEXT,
    `FIRST_NAME` TEXT NOT NULL,
    `LAST_NAME` TEXT,
    `EMAIL` VARCHAR(50) NOT NULL,
    `PASSWORD` TEXT NOT NULL,
    `CREATED_TS` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(`ID`),
    KEY (`email`)
);


CREATE TABLE `CLIENT` (
    `ID` BIGINT NOT NULL AUTO_INCREMENT,
    `THERAPIST_ID` BIGINT NOT NULL,
    `FIRST_NAME` TEXT NOT NULL,
    `LAST_NAME` TEXT,
    `EMAIL` TEXT NOT NULL,
    `CONTACT_NUMBER` TEXT NOT NULL,
    `BIRTH_DATE` TEXT,
    `CREATED_TS` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`ID`),
    KEY (`THERAPIST_ID`)
);

CREATE TABLE `CLIENT_HISTORY` (
    `ID` BIGINT NOT NULL AUTO_INCREMENT,
    `CLIENT_ID` BIGINT NOT NULL,
    `YEAR` INT,
    `MONTH` INT,
    `DATE` INT,
    `HISTORY` JSON,
    PRIMARY KEY (`ID`),
    KEY (`CLIENT_ID`)
);