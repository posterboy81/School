/* 	
	By:			Matthew Simpson
	For:		COMP 1630		Instructor: Mark Bacchus
	Date:		Fall 2019
*/

USE master;

/* 
	CHECK FOR EXISTING DATABASE AND DROP IT IF IT EXISTS.
	COPIED THIS FROM THE PUBS DATABASE SCRIPT. 
	SEEMED LIKE A GOOD IDEA FOR RE-RUNNING THE SCRIPT 
	OVER AND OVER. 
*/

IF EXISTS (
		SELECT *
		FROM sysdatabases
		WHERE name = 'cus_orders'
		)
BEGIN
	PRINT 'DROPPING EXISTING DATABASES';
	DROP DATABASE cus_orders;
END
GO

/*
	A-1: CREATE THE DATABASE 'cus_orders'. 
*/

CREATE DATABASE cus_orders;
GO

USE cus_orders;
GO

/* 
	A-2: CREATING TWO CUSTOM DATA TYPES TO USE WITH 
	ID FIELDS, ONE FROM CHAR AND ONE FROM INT
*/

CREATE TYPE id_cus
FROM CHAR(5) NOT NULL;

CREATE TYPE id_num
FROM INT NOT NULL;
GO


/*
	A-3: CREATE THE TABLES PLEASE NOTE I HAVE CHANGED 
	THE COLUMN NAMES SLIGHTLY
*/

-- CUSTOMERS TABLE
CREATE TABLE customers (
	customer_id id_cus,
	customer_name VARCHAR(50) NOT NULL,
	customer_contact_name VARCHAR(30),
	title_id CHAR(3),
	customer_address VARCHAR(50) NOT NULL,
	customer_city VARCHAR(20),
	customer_region VARCHAR(15),
	customer_country_code VARCHAR(10),
	customer_country VARCHAR(15),
	customer_phone VARCHAR(20),
	customer_fax VARCHAR(20)
	);
GO

-- ORDERS TABLE
CREATE TABLE orders (
	order_id id_num,
	customer_id id_cus,
	employee_id INT NOT NULL,
	order_shipping_name VARCHAR(50),
	order_shipping_address VARCHAR(50),
	order_shipping_city VARCHAR(20),
	order_shipping_region VARCHAR(15),
	order_shipping_country_code VARCHAR(10),
	order_shipping_country VARCHAR(15),
	shipper_id INT NOT NULL,
	order_date DATETIME,
	order_required_date DATETIME,
	order_shipped_date DATETIME,
	order_freight_charge MONEY
	);
GO

-- ORDER_DETAILS TABLE 
CREATE TABLE order_details (
	order_id id_num,
	product_id id_num,
	quantity INT NOT NULL,
	discount FLOAT NOT NULL
	);
GO

-- PRODUCTS TABLE 
CREATE TABLE products (
	product_id id_num,
	supplier_id INT NOT NULL,
	prod_name VARCHAR(40),
	prod_alt_name VARCHAR(40),
	prod_qty_per_unit VARCHAR(25),
	prod_unit_price MONEY,
	prod_qty_in_stock INT,
	prod_units_on_order INT,
	prod_reorder_level INT
	);
GO

-- SHIPPERS TABLE 
CREATE TABLE shippers (
	shipper_id INT IDENTITY,
	ship_name VARCHAR(20) NOT NULL
	);
GO

-- SUPPLIERS TABLE 
CREATE TABLE suppliers (
	supplier_id INT IDENTITY,
	sup_name VARCHAR(40) NOT NULL,
	sup_address VARCHAR(30),
	sup_city VARCHAR(20), 
	sup_province CHAR(2)
	);
GO

-- TITLES TABLE 
CREATE TABLE titles (
	title_id CHAR(3) NOT NULL,
	title_description VARCHAR(35) NOT NULL
	);
GO

/* 
	A-4: CREATE THE PRIMARY AND FOREIGN KEYS
*/

-- PRIMARY KEYS 

ALTER TABLE customers
	ADD PRIMARY KEY (customer_id);
GO

ALTER TABLE orders
	ADD PRIMARY KEY (order_id);
GO

ALTER TABLE order_details
	ADD PRIMARY KEY (order_id, product_id);
GO

ALTER TABLE products
	ADD PRIMARY KEY (product_id);
GO

ALTER TABLE shippers
	ADD PRIMARY KEY (shipper_id);
GO

ALTER TABLE suppliers
	ADD PRIMARY KEY (supplier_id);
GO

ALTER TABLE titles
	ADD PRIMARY KEY (title_id);
GO

-- FOREIGN KEYS

ALTER TABLE customers
	ADD CONSTRAINT FK_customer_title FOREIGN KEY (title_id) 
	REFERENCES titles (title_id);
GO

ALTER TABLE orders
	ADD CONSTRAINT FK_orders_customer FOREIGN KEY (customer_id)
	REFERENCES customers (customer_id);
GO

ALTER TABLE orders
	ADD CONSTRAINT FK_orders_shippers FOREIGN KEY (shipper_id)
	REFERENCES shippers (shipper_id);
GO
	
ALTER TABLE order_details
	ADD CONSTRAINT FK_orddet_order FOREIGN KEY (order_id)
	REFERENCES orders (order_id);
GO

ALTER TABLE order_details
	ADD CONSTRAINT FK_orddet_product FOREIGN KEY (product_id)
	REFERENCES products (product_id);
GO

ALTER TABLE products 
	ADD CONSTRAINT FK_product_supplier FOREIGN KEY (supplier_id)
	REFERENCES suppliers (supplier_id);
GO
	
/* 
	A-5: ADD DEFAULT CONSTRAINTS
*/

-- SET CUSTOMERS DEFAULT COUNTRY AS "CANADA"
ALTER TABLE customers
	ADD CONSTRAINT default_country DEFAULT ('Canada') FOR customer_country;
GO

-- SET ORDERS DEFAULT REQUIRED DATE AS TODAY + 10 DAYS. 
ALTER TABLE orders
	ADD CONSTRAINT default_req_date DEFAULT (DATEADD(DAY, 10, GETDATE())) FOR order_required_date;
GO

-- SET DEFAULT ORDER DETAILS QUANTITY TO ONE OR MODE
ALTER TABLE order_details 
	ADD CONSTRAINT default_qty CHECK (quantity >= 1);
GO 

-- SET PRODUCTS DEFAULT REORDER POINT TO ONE OR MORE
ALTER TABLE products
	ADD CONSTRAINT default_reorder CHECK (prod_reorder_level >= 1);
GO

-- SET PRODUCT MAX IN STOCK TO 150 OR FEWER
ALTER TABLE products
	ADD CONSTRAINT max_in_stock CHECK (prod_qty_in_stock <= 150);
GO

-- SET SUPPLIERS DEFAULT PROVINCE TO BC
ALTER TABLE suppliers
	ADD CONSTRAINT default_prov DEFAULT ('BC') FOR sup_province;
GO

/*
	A-6: BULK LOAD THE DATA FOR CREATED TABLES 
*/

-- CUSTOMERS
BULK INSERT customers
FROM 'C:\textfiles\customers.txt' WITH (
		CODEPAGE = 1252,
		DATAFILETYPE = 'char',
		FIELDTERMINATOR = '\t',
		KEEPNULLS,
		ROWTERMINATOR = '\n'
		);
GO

-- ORDERS
BULK INSERT orders
FROM 'C:\textfiles\orders.txt' WITH (
		CODEPAGE = 1252,
		DATAFILETYPE = 'char',
		FIELDTERMINATOR = '\t',
		KEEPNULLS,
		ROWTERMINATOR = '\n'
		);
GO

-- ORDER DETAILS 
BULK INSERT order_details
FROM 'C:\textfiles\order_details.txt' WITH (
		CODEPAGE = 1252,
		DATAFILETYPE = 'char',
		FIELDTERMINATOR = '\t',
		KEEPNULLS,
		ROWTERMINATOR = '\n'
		);
GO

-- PRODUCTS 
BULK INSERT products
FROM 'C:\textfiles\products.txt' WITH (
		CODEPAGE = 1252,
		DATAFILETYPE = 'char',
		FIELDTERMINATOR = '\t',
		KEEPNULLS,
		ROWTERMINATOR = '\n'
		);
GO

-- SHIPPERS
BULK INSERT shippers
FROM 'C:\textfiles\shippers.txt' WITH (
		CODEPAGE = 1252,
		DATAFILETYPE = 'char',
		FIELDTERMINATOR = '\t',
		KEEPNULLS,
		ROWTERMINATOR = '\n'
		);
GO

-- SUPPLIERS
BULK INSERT suppliers
FROM 'C:\textfiles\suppliers.txt' WITH (
		CODEPAGE = 1252,
		DATAFILETYPE = 'char',
		FIELDTERMINATOR = '\t',
		KEEPNULLS,
		ROWTERMINATOR = '\n'
		);
GO

-- TITLES
BULK INSERT titles
FROM 'C:\textfiles\titles.txt' WITH (
		CODEPAGE = 1252,
		DATAFILETYPE = 'char',
		FIELDTERMINATOR = '\t',
		KEEPNULLS,
		ROWTERMINATOR = '\n'
		);
GO

/* 
	B-1: LIST CUSTOMER ID, NAME, CITY, COUNTRY FROM 
	THE CUSTOMERS TABLE ORDERED BY CUSTOMER ID.
*/

SELECT 
	'Customer ID'	= customer_id, 
	'Name'			= customer_name, 
	'City'			= customer_city, 
	'Country'		= customer_country
FROM 
	customers
ORDER BY 
	customer_id;
GO

/* 
	B-2: ADD NEW COLUMN 'ACTIVE' TO CUSTOMERS TABLE. 
	ONLY VALID VALUES 1 OR 0.  DEFAULT VALUE 1.
*/

ALTER TABLE customers
ADD active int NOT NULL
CONSTRAINT default_active DEFAULT 1 WITH VALUES
CONSTRAINT one_or_zero CHECK (active = 1 OR active = 0);
GO

/* 
	B-3: LIST ORDERS BETWEEN JANUARY 1 AND DECEMBER 31 
	2001. DISPLAY ORDER ID, PRODUCT NAME, CUSTOMER NAME, 
	NEW ORDER SHIPPED DATE (ORDER DATE + 17 DAYS) AND 
	ORDER COST (QUANTITY * UNIT PRICE)
*/

SELECT 
	'Order ID'			= orders.order_id,
	'Product Name'		= products.prod_name,
	'Customer Name'		= customers.customer_name,
	'Order Date'		= CONVERT(CHAR(12), orders.order_date, 109),
	'New Shipped Date'	= CONVERT(CHAR(12), DATEADD(DAY, 17, orders.order_date)),
	'Order Cost'		= order_details.quantity * products.prod_unit_price
FROM 
	order_details
INNER JOIN orders ON order_details.order_id=orders.order_id
INNER JOIN products ON order_details.product_id=products.product_id
INNER JOIN customers ON orders.customer_id=customers.customer_id
WHERE orders.order_date BETWEEN 'January 1 2001' AND 'December 31 2001';
GO

/* 
	B-4: LIST ORDERS THAT HAVE NOT SHIPPED. ORDER BY SHIPPED DATE. 
*/

SELECT 
	'Customer ID'	= customers.customer_id,
	'Name'			= customers.customer_name,
	'Phone'			= customers.customer_phone,
	'Order ID'		= orders.order_id,
	'Order Date'	= CONVERT(CHAR(12), orders.order_date, 109)
FROM 
	orders
INNER JOIN customers ON orders.customer_id=customers.customer_id
WHERE orders.order_shipped_date IS NULL;

/* 
	B-5: LIST ALL CUSTOMERS WHERE REGION IS NULL
*/

SELECT 
	'Customer ID'	= customer_id,
	'Name'			= customer_name,
	'City'			= customer_city,
	'Description'	= title_description
FROM 
	customers
INNER JOIN titles ON customers.title_id=titles.title_id
WHERE customer_region IS NULL;
GO

/* 
	B-6: LIST ALL PRODUCTS WHERE REORDER POINT 
	IS HIGHER HTAN QUANTITY IN STOCK 
*/

SELECT 
	'Supplier Name'	= sup_name,
	'Product Name'	= prod_name,
	'Reorder Level'	= prod_reorder_level,
	'Qty In Stock'	= prod_qty_in_stock
FROM 
	products
INNER JOIN suppliers ON products.supplier_id=suppliers.supplier_id
WHERE prod_reorder_level > prod_qty_in_stock
ORDER BY [Supplier Name];
GO

/* 
	B-7: CALCULATE LENGTH IN YEARS FROM JANUARY 1 2008
	TO WHEN AN ORDER WAS SHIPPED WHERE THE SHIPPED DATE 
	IS NOT NULL.
*/

SELECT 
	'Order ID'		= orders.order_id,
	'Name'			= customers.customer_name,
	'Contact Name'	= customers.customer_contact_name,
	'Shipped Date'	= CONVERT(CHAR(12), orders.order_date, 109),
	'Years Elapsed'	= DATEDIFF(year, orders.order_date, 'January 1 2008')
FROM 
	orders
INNER JOIN customers ON orders.customer_id=customers.customer_id
WHERE orders.order_shipped_date IS NOT NULL;
GO

/* 
	B-8: LIST NUMBER OF CUSTOMERS WITH NAMES BEGINNING 
	WITH EACH LETTER OF THE ALPHABET.  
	IGNORE THE LETTER S. 
	IGNORE LETTERS WITH FEWER THAN 2 ENTRIES. 
*/

SELECT
	'Name'			= SUBSTRING(customers.customer_name, 1, 1),
	'Total'			= count(*)
FROM 
	customers
GROUP BY SUBSTRING(customers.customer_name, 1, 1)
HAVING count(*) >= 2
AND SUBSTRING(customers.customer_name,1,1) != 's';


/* 
	B-9: LIST ORDER DETAILS FOR ORDERS WHERE QUANTITY 
	IS GREATER THAN 100 ORDER RESULT BY ORDER ID
*/

SELECT 
	'Order ID'		= order_details.order_id,
	'Quantity'		= order_details.quantity,
	'Product ID'	= products.product_id,
	'Reorder Level'	= products.prod_reorder_level,
	'Supplier ID'	= products.supplier_id
FROM 
	order_details
INNER JOIN products ON order_details.product_id=products.product_id
WHERE order_details.quantity > 100
ORDER BY order_details.order_id;
GO

/* 
	B-10- LIST ALL PRODUCTS WITH SUBSTRING 'TOFU' OR 
	'CHEF' IN THE NAME ORDER BY PRODUCT NAME 
*/

SELECT
	'Product ID'	= products.product_id,
	'Product Name'	= products.prod_name,
	'Qty Per Unit'	= products.prod_qty_per_unit,
	'Unit Price'	= products.prod_unit_price
FROM 
	products
WHERE 
	products.prod_name LIKE '%chef%'
	OR products.prod_name LIKE '%tofu%'
ORDER BY 
	products.prod_name;
GO

/* 
	C-1, C-2: CREATE THE EMPLOYEE TABLE AND SET THE 
	EMPLOYEE ID AS THE PRIMARY KEY. 
*/ 

CREATE TABLE employee (
	employee_id INT NOT NULL,
	emp_last_name varchar(30) NOT NULL,
	emp_first_name varchar(15) NOT NULL,
	emp_address varchar(30),
	emp_city varchar(20), 
	emp_province char(2),
	emp_postal_code varchar(7),
	emp_phone varchar(10), 
	emp_birth_date datetime NOT NULL
);
GO

ALTER TABLE employee
	ADD PRIMARY KEY (employee_id);
GO

/* 
	C-3: LOAD THE EMPLOYEE DATA INTO THE EMPLOYEE TABLE. 
	SET UP THE FOREIGN KEY RELATIONSHIP
*/

BULK INSERT employee
FROM 'C:\textfiles\employee.txt' WITH (
		CODEPAGE = 1252,
		DATAFILETYPE = 'char',
		FIELDTERMINATOR = '\t',
		KEEPNULLS,
		ROWTERMINATOR = '\n'
		);
GO

ALTER TABLE orders
	ADD CONSTRAINT FK_ord_emp FOREIGN KEY (employee_id)
	REFERENCES employee (employee_id);
GO

/* 
	C-4: INSERT THE NEW SHIPPER 'QUICK EXPRESS' INTO THE 
	SHIPPERS TABLE. SINCE SHIPPERS HAS AN IDENTITY COLUMN, 
	EVERYTHING ELSE SHOULD BE TAKEN CARE OF AUTOMAGICALLY 
*/

INSERT INTO shippers
	VALUES('Quick Express');
GO

SELECT * FROM shippers;

/* 
	C-5: INCREASE THE PRICE OF ALL PRODUCTS WITH CURRENT 
	PRICE BETWEEN 5$ AND 10$ BY 5%
*/

UPDATE 
	products
SET products.prod_unit_price = ROUND(products.prod_unit_price * 1.05, 2)
WHERE products.prod_unit_price >= 5 AND products.prod_unit_price <= 10; 
GO

/*
	C-6: UPDATE THE FAX VALUE TO 'UNKNOWN' FOR ALL 
	CUSTOMERS WHO HAVE A NULL FAX VALUE. 
*/

UPDATE 
	customers
SET customers.customer_fax = 'UNKNOWN'
WHERE customers.customer_fax IS NULL;
GO

/*
	C-7a: CREATE VIEW VW_ORDER_COST TO LIST THE COST OF ORDERS.
*/ 

CREATE VIEW vw_order_cost
AS
SELECT 
	'Order ID'		= orders.order_id,
	'Order Data'	= orders.order_date,
	'Product ID'	= products.product_id,
	'Customer Name'	= customers.customer_name,
	'Order Cost'	= (order_details.quantity * products.prod_unit_price)
FROM order_details
INNER JOIN orders ON order_details.order_id=orders.order_id
INNER JOIN products ON order_details.product_id=products.product_id
INNER JOIN customers ON customers.customer_id=orders.customer_id;
GO

/*
	C-7b: RUN VW_ORDER_COST FOR ORDER IDS BETWEEN 10000 AND 10200
*/

SELECT *
FROM vw_order_cost
WHERE vw_order_cost.[Order ID] BETWEEN 10000 AND 10200;
GO

/*
	C-8a: CREATE VIEW VW_LIST_EMPLOYEES TO LIST EMPLOYEE DETAILS. 
	
	THIS QUESTION DESCRIPTION ASKS FOR ALL THE EMPLOYEE COLUMNS 
	TO BE LISTED,BUT THEN ALSO FOR FORMATTED OUTPUT FROM THE 
	VIEW.  SEE CHALLENGES SECTION 
*/

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

/*
	C-9a: CREATE VIEW VW_ALL_ORDERS, 
	FORMAT SHIPPED DATE MON DD YYYY
*/

CREATE VIEW vw_all_orders
AS
SELECT
	'Order ID'		= orders.order_id,
	'Customer ID'	= customers.customer_id,
	'Customer Name'	= customers.customer_name,
	'City'			= customers.customer_city,
	'Country'		= customers.customer_country,
	'Shipped Date'	= orders.order_shipped_date
FROM orders
INNER JOIN customers ON orders.customer_id=customers.customer_id;
GO

/*
	C-9b: RUN VW_ALL_ORDERS FOR ALL ORDERS BETWEEN 
	JAN 1 2002 AND DEC 31 2002
*/

SELECT 
	vw_all_orders.[Order ID],
	vw_all_orders.[Customer ID],
	vw_all_orders.[Customer Name],
	vw_all_orders.[City],
	vw_all_orders.[Country],
	format(vw_all_orders.[Shipped Date], 'MMM dd yyyy') AS 'Shipped Date'
FROM vw_all_orders
WHERE [Shipped Date] BETWEEN 'Jan 1 2002' AND 'Dec 31 2002'
ORDER BY [Customer Name], [Country];
GO

/* 
	C-10a: CREATE VW_SUPPLIERS_SHIPPED DO DISPLAY 
	DETAILS OF ORDERS SHIPPED. 
*/

CREATE VIEW vw_suppliers_shipped
AS
SELECT 
	'Supplier ID'	= suppliers.supplier_id,
	'Supplier Name'	= suppliers.sup_name,
	'Product ID'	= products.product_id,
	'Product Name'	= products.prod_name
FROM products
INNER JOIN suppliers ON products.supplier_id=suppliers.supplier_id;
GO

/* 
	C-10b: RUN VW_SUPPLIERS_SHIPPED
*/

SELECT * 
FROM vw_suppliers_shipped;
GO

/* 
	D-1a: CREATE STORED PROCEDURE SP_CUSTOMER_CITY  TO LIST CUSTOMERS BY SELECTED CITY. 
	@CHECKCITY IS A STORED VARIABLE, THE CITY TO BE LISTED. 
*/

CREATE PROCEDURE sp_customer_city
(
	@checkcity	varchar(20)
)
AS
	SELECT 
		'Customer ID'		= customers.customer_id,
		'Customer Name'		= customers.customer_name,
		'Customer Address'	= customers.customer_address,
		'Customer City'		= customers.customer_city,
		'Customer Phone'	= customers.customer_phone
	FROM customers
	WHERE customers.customer_city = @checkcity;
GO

/*
	D-1b: RUN SP_CUSTOMER_CITY FOR THE CITY 'LONDON'
*/

EXECUTE sp_customer_city 'London';
GO


/* 
	D-2a: CREATE SP_ORDERS_BY_DATES TO DISPLAY ORDERS 
	SHIPPED BETWEEN SPECIFIED DATES. @STARTDATE AND 
	@ENDDATE ARE STORED VARIABLES, THE DATES TO CHECK 
	BETWEEN. 
*/

CREATE PROCEDURE sp_orders_by_dates
(
	@startdate	datetime,
	@enddate	datetime
)
AS 
	SELECT 
		'Order ID'				= orders.order_id,
		'Customer ID'			= orders.customer_id,
		'Customer Name'			= customers.customer_name,
		'Shipper Name'			= shippers.ship_name,
		'Shipped Date'			= FORMAT(orders.order_shipped_date, 'MMM dd yyyy')
	FROM orders
	INNER JOIN customers ON orders.customer_id=customers.customer_id
	INNER JOIN shippers ON orders.shipper_id=shippers.shipper_id
	WHERE orders.order_shipped_date BETWEEN @startdate AND @enddate
	ORDER BY orders.order_shipped_date;
GO

/* 
	D-2b: RUN SP_ORDERS_BY_DATES FOR JAN 1 2003 THROUGH JUN 30 2003
*/

EXECUTE sp_orders_by_dates 'January 1 2003', 'June 30 2003';
GO

/* 
	D-3a: CREATE SP_PRODUCT_LISTING TO LIST A PRODUCT ORDERED 
	DURING SPECIFIED MONTH AND YEAR.  
	@CHECKPROD IS THE SUBSTRING OF A PRODUCT NAME TO BE SEARCHED
	@CHECMONTH AND @CHECKYEAR ARE THE MONTH AND YEAR TO BE SEARCHED. 
*/

CREATE PROCEDURE sp_product_listing
(
	@checkprod	varchar(30),
	@checkmonth datetime,
	@checkyear	datetime
)
AS
SELECT 
	'Product Name'				= products.prod_name,
	'Unit Price'				= products.prod_unit_price,
	'Qty in Stock'				= products.prod_qty_in_stock,
	'Supplier Name'				= suppliers.sup_name
FROM products
INNER JOIN suppliers ON products.supplier_id=suppliers.supplier_id
INNER JOIN order_details ON products.product_id=order_details.product_id
INNER JOIN orders ON order_details.order_id=orders.order_id
WHERE products.prod_name LIKE '%' + @checkprod + '%'
	AND DATEPART(MONTH, orders.order_date) = @checkmonth
	AND DATEPART(YEAR, orders.order_date) = @checkyear;
GO

/* 
	D-3b: RUN SP_PRODUCT_LISTING SEARCHING FOR PRODUCTS 
	CONTAINING 'JACK' IN JUNE 2001.
*/

EXECUTE sp_product_listing 'jack', 06, 2001;
GO

/*
	D-4a: CREATE DELETE TRIGGER ON ORDER_DETAILS TO SHOW 
	PRODUCT_ID, PRODUCT NAME, QUANTITY DELETED, AND STOCK 
	AFTER DELETION WHEN AN ORDER_DETAILS ROW IS DELETED. 
*/

CREATE TRIGGER tr_dl_qty_update
ON order_details
FOR DELETE
AS
DECLARE 
	@prod_id		id_num, 
	@ord_id			id_num,
	@qty_deleted	int
SELECT 
	@prod_id		= deleted.product_id,
	@ord_id			= deleted.order_id,
	@qty_deleted	= deleted.quantity
FROM deleted 
IF @@ROWCOUNT = 0 
	PRINT 'NOTHING FOUND!'
ELSE
	BEGIN
	UPDATE products  
		SET products.prod_qty_in_stock = products.prod_qty_in_stock + @qty_deleted
		WHERE products.product_id = @prod_id
	END 
	BEGIN
		SELECT 
			'Product ID'						= products.product_id,
			'Product Name'						= products.prod_name,
			'Quantity Being Deleted'			= @qty_deleted,
			'Quantity In Stock After Deletion'	= products.prod_qty_in_stock
		FROM deleted
		INNER JOIN products ON deleted.product_id=products.product_id
		WHERE products.product_id = 25;
	END;
GO

/* 
	D-4b: TRIGGER THE TRIGGER WITH A DELETE STATEMENT. 
*/

DELETE order_details 
WHERE order_id = 10001 AND product_id = 25;
GO

/* 
	D-5a: CREATE TRIGGER TR_QTY_CHECK FOR ORDER_DETAILS 
	WHICH WILL REJECT ANY QUANTITY UPDATE THAT CANNOT 
	BE SUPPLIED BY THE PRODUCT QUANTITY IN STOCK WILL 
	ALSO REPORT SHORTFALL IF IN STOCK QUANTITY IS NOT 
	ENOUGH 
*/

CREATE TRIGGER tr_qty_check 
ON order_details 
FOR UPDATE
AS
DECLARE 
	@prod_id		id_num,
	@quantity		INT,
	@instock		INT
SELECT 
	@prod_id		= inserted.product_id,
	@quantity		= inserted.quantity - deleted.quantity,
	@instock		= products.prod_qty_in_stock
FROM inserted
INNER JOIN deleted ON inserted.product_id=deleted.product_id
INNER JOIN products ON inserted.product_id=products.product_id
IF @quantity > @instock
	BEGIN
		PRINT 'NOT ENOUGH STOCK. YOU NEED ' + CONVERT(varchar(10), (@quantity - @instock)) + ' MORE.'
		ROLLBACK TRANSACTION
	END
ELSE 
	BEGIN 
		UPDATE products
		SET products.prod_qty_in_stock = products.prod_qty_in_stock - @quantity
		WHERE products.product_id = @prod_id
		SELECT 
			'Name'			= products.prod_name,
			'Updated Qty'	= products.prod_qty_in_stock
		FROM products
		WHERE product_id = @prod_id
	END
GO

/* 
	D-6a: RUN FIRST SUPPLIED QUERY 
*/

UPDATE order_details
SET quantity =50
WHERE order_id = '10044'
     AND product_id = 7;
GO

/* 
	D-6B: RUN SECOND SUPPLIED QUERY 
*/

UPDATE order_details
SET quantity = 40
WHERE order_id = '10044'
     AND product_id = 7;
GO


/* 
	D-7: STORED PROCEDURE TO DELETE INACTIVE CUSTOMERS. 
*/

CREATE PROCEDURE sp_del_inactive_cust
AS
DELETE 
	FROM customers
	WHERE customers.customer_id NOT IN
	(
		SELECT orders.customer_id
		FROM orders
	);
GO

--	RUN THIS PROCEDURE

EXECUTE sp_del_inactive_cust;
GO

/*
	D-8: LIST DETAILS OF SPECIFIED EMPLOYEE. @checkEmp IS THE 
	EMPLOYEE ID OF THE EMPLOYEE IN QUESTION
*/

CREATE PROCEDURE sp_employee_information 
(
	@checkEmp id_num
)
AS
SELECT 
	'Employee ID'		= employee.employee_id,
	'Last Name'			= employee.emp_last_name,
	'First Name'		= employee.emp_first_name,
	'Address'			= employee.emp_address,
	'City'				= employee.emp_city,
	'Province'			= employee.emp_province,
	'Postal Code'		= employee.emp_postal_code,
	'Phone #'			= employee.emp_phone,
	'DOB'				= employee.emp_birth_date
FROM employee
WHERE employee.employee_id = @checkEmp;
GO

-- RUN THE PROCEDURE

EXECUTE sp_employee_information 5;
GO

/*
	D-9: CREATE PROCEDURE sp_reorder_qty TO SHOW WHEN REORDER 
	LEVEL SUBTRACTED FROM QUANTITY IN STOCK IS LESS THAN A 
	SPECIFIED VALUE. @checkNum IS THE VALUE TO CHECK
*/

CREATE PROCEDURE sp_reorder_qty
(
	@checkNUm int
)
AS
	SELECT
		'Product ID'		= products.product_id,
		'Supplier Name'		= suppliers.sup_name,
		'Address'			= suppliers.sup_address,
		'City'				= suppliers.sup_city,
		'Province'			= suppliers.sup_province,
		'Qty'				= products.prod_qty_in_stock,
		'Reorder Lvl'		= products.prod_reorder_level
	FROM products 
	INNER JOIN suppliers ON products.supplier_id=suppliers.supplier_id
	WHERE (products.prod_qty_in_stock - prod_reorder_level) < @checkNUm;
GO

--	 RUN THE PROCEDURE 

EXECUTE sp_reorder_qty 5;
GO

/* 
	D-10: CREATE PRCEUDRE TO SHOW  DETAILS FOR ALL PRODUCTS 
	WITH UNIT PRICE BETWEEN SPECIFIED UPPER AND LOWER VALUES.
	@checkLow WILL BE THE LOW VALUE AND @checkHIgh WILL BE 
	THE HIGH VALUE. 
*/

CREATE PROCEDURE sp_unit_prices
(
	@checkLow		money,
	@checkHigh		money
) 
AS 
	SELECT 
		'Product ID'		= products.product_id,
		'Name'				= products.prod_name,
		'Alternate Name'	= products.prod_alt_name,
		'Unit Price'		= products.prod_unit_price
	FROM products
	WHERE products.prod_unit_price > @checkLow AND products.prod_unit_price < @checkHigh;
	GO

--	RUN THE PROCEDURE. 

EXECUTE sp_unit_prices 5.00, 10.00;
GO
