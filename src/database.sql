CREATE OR REPLACE TABLE User (
    user_id INT PRIMARY KEY,
    name VARCHAR(50),
    email VARCHAR(50) UNIQUE,
    password VARCHAR(50),
    age INT,
    type varchar(10) -- student or teacher
);

CREATE OR REPLACE TABLE Student (
    student_id INT PRIMARY KEY,
    history TEXT,
    FOREIGN KEY (student_id) REFERENCES User(user_id) --
);

CREATE OR REPLACE TABLE Teacher (
    teacher_id INT PRIMARY KEY,
	-- add way to store the tests created- (completed / upcoming test)
 
    FOREIGN KEY (teacher_id) REFERENCES User(user_id) --
);

CREATE OR REPLACE TABLE Question (
    question_id INT PRIMARY KEY,
    title VARCHAR(255),
    answer TEXT,
    creator_id INT, -- --
    FOREIGN KEY (creator_id) REFERENCES Teacher(teacher_id) -- --
);

CREATE OR REPLACE TABLE Submission (
    submission_id INT PRIMARY KEY,
    answer TEXT,
    sender_id INT,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    question_id INT,
    FOREIGN KEY (sender_id) REFERENCES Student(student_id), -- --
    FOREIGN KEY (question_id) REFERENCES Question(question_id)
);

CREATE OR REPLACE TABLE Test_Log (
    test_id INT PRIMARY KEY,
    creator_id INT, 
	-- add way to store list of question id's (multivalued attribute)
    FOREIGN KEY (creator_id) REFERENCES Teacher(teacher_id)
);

CREATE OR REPLACE TABLE Test_Question (
    test_id INT,
    question_id INT,
    FOREIGN KEY (test_id) REFERENCES Test_log(test_id),
    FOREIGN KEY (question_id) REFERENCES Question(question_id)
);