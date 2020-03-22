CREATE TABLE users (
    user_id serial PRIMARY KEY,
    user_name VARCHAR(32)
);

CREATE TABLE quizzes (
    quiz_id serial PRIMARY KEY,
    user_id INTEGER,
    quiz_name VARCHAR(256),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE questions (
    question_id serial PRIMARY KEY,
    quiz_id INTEGER,
    question_text VARCHAR(355),
    FOREIGN KEY (quiz_id) REFERENCES quizzes(quiz_id) ON DELETE CASCADE
);

CREATE TABLE question_answers (
    answer_id serial PRIMARY KEY,
    question_id INTEGER,
    answer_text VARCHAR(255),
    correct BOOLEAN,
    FOREIGN KEY (question_id) REFERENCES questions(question_id) ON DELETE CASCADE
);

