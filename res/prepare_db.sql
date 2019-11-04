CREATE TABLE IF NOT EXISTS `User`(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    firstName TEXT NOT NULL,
    lastName TEXT NOT NULL,
    dateOfBirth TIMESTAMP,
    privilege INTEGER
);

CREATE TABLE IF NOT EXISTS `Trick`(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT
);

CREATE TABLE IF NOT EXISTS `AnimalTrick`(
    animalId INTEGER NOT NULL,
    trickId INTEGER NOT NULL,
    proficiency INTEGER NOT NULL,
    FOREIGN KEY (animalId) REFERENCES Animal(id),
    FOREIGN KEY (trickId) REFERENCES Trick(id)
);

CREATE TABLE IF NOT EXISTS `Vaccine`(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT
);

CREATE TABLE IF NOT EXISTS `AnimalVaccine`(
    animalId INTEGER NOT NULL,
    vaccineId INTEGER NOT NULL,
    dateReceived TIMESTAMP,
    FOREIGN KEY(animalId) REFERENCES Animal(id),
    FOREIGN KEY(vaccineId) REFERENCES Vaccine(id)
);

CREATE TABLE IF NOT EXISTS `Animal`(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT,
    species TEXT,
    description TEXT,

    -- One letter character to represent the animals gender (M, F)
    gender VARCHAR(1),

    -- A list of colors, separated by a pipe character: '|'
    colors TEXT,
    adopted INTEGER,
    dateArrived TIMESTAMP,
    dateAdopted TIMESTAMP,
    dateOfBirth TIMESTAMP,
    serviceTrained INTEGER,
    weight REAL,
    height REAL,
    breeds TEXT,
    bathroomTraining INTEGER,
    aggression INTEGER
);

CREATE TABLE IF NOT EXISTS `UserAnimalFavorite`(
    userId INTEGER,
    animalId INTEGER,
    FOREIGN KEY(userId) REFERENCES User(id)
);

CREATE TABLE IF NOT EXISTS `EmployeeHour`(
    userId INTEGER,
    dayWorked TIMESTAMP,
    hoursWorked REAL,
    FOREIGN KEY(userId) REFERENCES User(id)
);

CREATE TABLE IF NOT EXISTS `VolunteerHour`(
    userId INTEGER,
    hoursEarned REAL,
    FOREIGN KEY(userId) REFERENCES User(id)
);

CREATE TABLE IF NOT EXISTS `Event`(
    id             INTEGER PRIMARY KEY AUTOINCREMENT,
    creatorId      INTEGER,
    name           TEXT,

    -- The scheduled date for the event.
    date           TIMESTAMP,

    -- If the event is published and visible yet.
    published      INTEGER,

    -- Target audience is the level of authorization required for people to view this event.
    -- For instance, an event might only be available for employees to see, but an event for normal
    -- users is visible by everyone.
    targetAudience INTEGER,
    description    TEXT,
    FOREIGN KEY (creatorId) REFERENCES User (id)
);

CREATE TABLE IF NOT EXISTS `Question`(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    userId INTEGER,
    answered INTEGER,
    employeeId INTEGER,
    question TEXT,
    answer TEXT,
    FOREIGN KEY(userId) REFERENCES User(id),
    FOREIGN KEY(employeeId) REFERENCES User(id)
);

CREATE TABLE IF NOT EXISTS `AdoptionRequest`(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    customerId INTEGER,
    animalId INTEGER,
    approved INTEGER,
    dateRequested TIMESTAMP,
    dateApproved TIMESTAMP,
    FOREIGN KEY (customerId) REFERENCES User(id),
    FOREIGN KEY (animalId) REFERENCES Animal(id)
);

CREATE TABLE IF NOT EXISTS `VolunteerApplication`(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    applicantId INTEGER,
    approved INTEGER,
    dateRequested TIMESTAMP,
    dateApproved TIMESTAMP,
    FOREIGN KEY (applicantId) REFERENCES USER(id)
);