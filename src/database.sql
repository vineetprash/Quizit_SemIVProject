CREATE TABLE users (
    user_id INT PRIMARY KEY,
    username VARCHAR2(50) UNIQUE NOT NULL,
    password VARCHAR2(100) NOT NULL,
    role VARCHAR2(10) NOT NULL CHECK (role IN ('teacher', 'student'))
);

CREATE TABLE student (
    username VARCHAR2(50) PRIMARY KEY,
    total_score INT,
    age INT,
    class VARCHAR2(20),
);

CREATE TABLE teacher (
    teacher_id INT PRIMARY KEY,
    username VARCHAR2(50),
    tests_created CLOB, -- JSON or other structured format (for multiple tests)
    salary INT,
);

CREATE TABLE quizzes (
    quiz_id INT PRIMARY KEY,
    quiz_name VARCHAR2(100) NOT NULL,
    time_limit INT, -- in minutes
    scoring_criteria INT
);

CREATE TABLE questions (
    question_id INT PRIMARY KEY,
    quiz_id INT,
    question_text CLOB NOT NULL,
    question_type VARCHAR2(20) NOT NULL,
    option1 VARCHAR2(20),
    option2 VARCHAR2(20),
    option3 VARCHAR2(20),
    option4 VARCHAR2(20),
    correct_answer VARCHAR2(255) -- For short answer, true/false, or other types
);

CREATE TABLE results (
    student_id VARCHAR2(20),
    quiz_id INT,
    quiz_name VARCHAR2(20),
    score INT
)

-- automate question_id
CREATE SEQUENCE question_id_seq START WITH 1 INCREMENT BY 1;
CREATE OR REPLACE TRIGGER questions_trigger
BEFORE INSERT ON questions
FOR EACH ROW
BEGIN
:NEW.question_id := question_id_seq.NEXTVAL;
END;

-- automate quiz id
CREATE SEQUENCE quiz_id_seq START WITH 1 INCREMENT BY 1;
CREATE OR REPLACE TRIGGER quizzes_trigger
BEFORE INSERT ON quizzes
FOR EACH ROW
BEGIN
:NEW.quiz_id := quiz_id_seq.NEXTVAL;
END;

-- automate user id
-- CREATE OR REPLACE SEQUENCE user_id_seq START WITH 1 INCREMENT BY 1;
-- CREATE OR REPLACE TRIGGER users_trigger
-- BEFORE INSERT ON users
-- FOR EACH ROW
-- BEGIN
--     IF :NEW.role = 'student' THEN
--         INSERT INTO student (student_id, username)
--         VALUES (:NEW.user_id, :NEW.username);
--     ELSIF :NEW.role = 'teacher' THEN
--         INSERT INTO teacher (teacher_id, username)
--         VALUES (:NEW.user_id, :NEW.username);
--     END IF;
-- END;
