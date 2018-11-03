drop database `lab_6`; 

CREATE SCHEMA `lab_6` DEFAULT CHARACTER SET utf8;
USE `lab_6`;

CREATE TABLE Group_of_student
(
  name_of_group VARCHAR(50) NOT NULL,
  PRIMARY KEY (name_of_group)
) ENGINE = InnoDB;

CREATE TABLE  Student
(
  student_id INT NOT NULL AUTO_INCREMENT,
  last_name VARCHAR(50) NOT NULL,
  first_name VARCHAR(50) NOT NULL,
  name_of_group VARCHAR(50) NOT NULL,
  specialty VARCHAR(50) NULL,
  PRIMARY KEY (student_id),
  CONSTRAINT FOREIGN KEY (name_of_group)
    REFERENCES Group_of_student (name_of_group) ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE = InnoDB;

CREATE TABLE Lecturer
(
  lecturer_id INT NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(50) NOT NULL,
  last_name VARCHAR(50) NOT NULL,
  degree VARCHAR(50) NOT NULL,
  PRIMARY KEY (lecturer_id)
) ENGINE = InnoDB;

CREATE TABLE  Student_and_lecturer (
  student_id INT NOT NULL,
  lecturer_id INT NOT NULL,
  PRIMARY KEY (student_id, lecturer_id),
  CONSTRAINT  FOREIGN KEY (student_id)
    REFERENCES  Student (student_id) ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT   FOREIGN KEY (lecturer_id)
    REFERENCES  lecturer (lecturer_id) ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE = InnoDB;

INSERT INTO Group_of_Student(name_of_group)
VALUES('КН-21'),
	('КН-22'),
	('КН-23'),
	('КН-24'),
	('КН-25'),
	('КН-26');


INSERT INTO Student(student_id, first_name, last_name, name_of_group, specialty)
VALUES(1, 'Дмитро','Мельник', 'КН-21', 'Engineer'),
    (2, 'Оксана','Шевченко', 'КН-22', 'Engineer'),
    (3, 'Олександр','Бойко', 'КН-23', 'Engineer'),
    (4, 'Людмила','Коваленко', 'КН-24', 'Engineer'),
    (5, 'Андрій','Бондаренко', 'КН-25', 'Engineer'),
    (6, 'Наталія','Ткаченко', 'КН-26', 'Engineer'),
    (7, 'Петро','Ковальчук', 'КН-21', 'Engineer'),
    (8, 'Ангеліна','Кравченко', 'КН-22', 'Engineer'),
    (9, 'Віталій','Олійник', 'КН-23', 'Engineer'),
	(10, 'Надія','Шевчук', 'КН-24', 'Engineer'),
    (11, 'Дмитро','Коваль', 'КН-25', 'Engineer'),
    (12, 'Оксана','Поліщук', 'КН-26', 'Engineer'),
    (13, 'Олександр','Бондар', 'КН-21', 'Engineer'),
    (14, 'Людмила','Ткачук', 'КН-22', 'Engineer'),
    (15, 'Андрій','Морозов', 'КН-23', 'Engineer'),
    (16, 'Наталія','Марченко', 'КН-24', 'Engineer'),
    (17, 'Петро','Кравчук', 'КН-25', 'Engineer'),
    (18, 'Ангеліна','Клименок', 'КН-26', 'Engineer'),
    (19, 'Віталій','Павленко', 'КН-21', 'Engineer'),
	(20, 'Надія','Савчук', 'КН-22', 'Engineer');


INSERT INTO Lecturer(lecturer_id, first_name, last_name, degree)
VALUES(1, 'Дмитро','Бондар', 'Професор'),
    (2, 'Оксана','Марченко', 'Професор'),
    (3, 'Олександр','Ткаченко', 'Професор'),
    (4, 'Людмила','Бондаренко', 'Професор'),
    (5, 'Андрій','Поліщук', 'Професор'),
    (6, 'Наталія','Клименок', 'Професор');


INSERT INTO Student_and_lecturer(student_id, lecturer_id)
VALUES(1,1),
	(2,2),
	(3,3),
	(4,4),
	(5,5),
	(6,6),
	(7,1),
	(8,2),
	(9,3),
	(10,4),
	(11,5),
	(12,6),
	(13,1),
	(14,2),
	(15,3),
	(16,4),
    (17,5),
    (18,6),
    (19,1),
    (20,2);

DELIMITER //
CREATE PROCEDURE InsertStudent_and_lecturer
(
IN last_name_student_in varchar(50),
IN first_name_lecturer_in varchar(50)
)
BEGIN
	DECLARE msg varchar(50);

  IF NOT EXISTS( SELECT * FROM Student WHERE last_name=last_name_student_in)
  THEN SET msg = 'This last_name is absent';

	ELSEIF NOT EXISTS( SELECT * FROM Lecturer WHERE first_name=first_name_lecturer_in)
		THEN SET msg = 'This Lecturer is absent';

	ELSEIF EXISTS( SELECT * FROM student_and_lecturer
		WHERE student_id = (SELECT student_id FROM Student WHERE last_name=last_name_student_in)
        AND lecturer_id = (SELECT lecturer_id FROM Lecturer WHERE first_name=first_name_lecturer_in)
        )
        THEN SET msg = 'This Student already has this lecturer';

	ELSEIF (SELECT degree FROM Lecturer WHERE first_name=first_name_lecturer_in )
    <= (SELECT COUNT(*) FROM student_and_lecturer WHERE student_id=(SELECT lecturer_id FROM Lecturer WHERE first_name=first_name_lecturer_in) )
    THEN SET msg = 'There are no this Lecturer already';

    ELSE
		INSERT student_and_lecturer (student_id, lecturer_id)
        Value ( (SELECT student_id FROM Lecturer WHERE Surname=last_name_student_in),
			     (SELECT lecturer_id FROM Lecturer WHERE first_name=first_name_lecturer_in) );
		SET msg = 'OK';

	END IF;

	SELECT msg AS msg;

END //
DELIMITER ;