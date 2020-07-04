/*
	C-8a: CREATE VIEW VW_LIST_EMPLOYEES TO LIST EMPLOYEE DETAILS. 
	
	THIS QUESTION DESCRIPTION ASKS FOR ALL THE EMPLOYEE COLUMNS 
	TO BE LISTED,BUT THEN ALSO FOR FORMATTED OUTPUT FROM THE 
	VIEW.  SEE CHALLENGES SECTION 
*/

CREATE VIEW vw_list_all_employees
AS
SELECT 
	'Employee ID'	= employee.employee_id,
	'Name'			= (employee.emp_last_name + ', ' + employee.emp_first_name),
	'Birthdate'		= CONVERT(char(12), employee.emp_birth_date, 102),
	'Address'		= employee.emp_address,
	'City'			= employee.emp_city,
	'Province'		= employee.emp_province,
	'Postal'		= employee.emp_postal_code,
	'Phone'			= employee.emp_phone,
	'Birth Date'	= employee.emp_birth_date
FROM employee;

SELECT 
	[Employee ID],
	[Name],
	[Birthdate]
FROM vw_list_all_employees
WHERE [Employee ID] = 5
	OR [Employee ID] = 7
	OR [Employee ID] = 9;

select * from employee;
CREATE VIEW vw_list_employees
AS
SELECT
	'Employee ID'	= employee.employee_id,
	'Name'			= (employee.emp_last_name + ', ' + employee.emp_first_name),
	'Birthdate'		= CONVERT(char(12), employee.emp_birth_date, 102)
FROM employee;
GO

/* 
	C-8b RUN VW_LIST_EMPLOYEES FOR EMPLOYEE IDS 5, 7, 9
*/

SELECT *
FROM vw_list_employees
WHERE [Employee ID] = 5
	OR [Employee ID] = 7
	OR [Employee ID] = 9;
GO
