#Creating Database 
create database bank_management;
use bank_management;

#Creating loantype table
create table loantypes(id varchar(255) primary key,description varchar(255));
-- desc loantypes; 
-- alter table loantypes 
-- modify column id varchar(255);

#Creating branches table
create table branches(id varchar(50) primary key,address varchar(255),phone varchar(10));
-- desc branches;

#creating customers table
create table customer(id int primary key,login varchar(255),passhash varchar(20),name varchar(255),phone varchar(10),email varchar(255),registrationdate date);
-- desc customers;

#creating Loan table
create table loan(id int primary key,customer_id int,type varchar(255),amount decimal(10,2),branch_id varchar(50), foreign key(customer_id) references customers(id),
foreign key(branch_id) references branches(id),foreign key(type) references loantypes(id));
-- desc loan;


#Creating ACcount_types table
create table accounttypes(id  varchar(50) primary key,description varchar(255),interestrate decimal(10,2)); 
-- desc accounttypes;

#creating Account table
create table account(customer_id int,branch_id varchar(50),opening_date date,current_balance decimal(10,2), interest_rate decimal(5,2),foreign key(customer_id) references customer(id),foreign key(branch_id) references branches(id));
-- desc accounts;
-- drop table accounts;


#creating Overdraftlog table
create table overdraftlog(id int primary key , account_id int,description varchar(50), foreign key(account_id) references accounts(id));
-- drop table overdraftlog;
-- desc overdraftlog;

#creating transactiontypes
create table transactiontypes(id int primary key,description varchar(255));
-- desc transactiontypes;

#Creating Employees table 
create table employee(id int primary key, name varchar(255),phone varchar(10),position varchar(255),branch_id varchar(50),manager_id int,login varchar(255),passhash varchar(20), foreign key(manager_id) references employee(id),foreign key(branch_id) references branches(id));
-- desc employees;

#Creating  transcations table
create table transaction(id int primary key,datetime datetime,amount decimal(10,2),account_id int,type int,teller_emp_id int, foreign key(teller_emp_id) references employee(id));
-- desc transactions;
-- drop table transactions;

#creating Failedtransactionlog table
create table failedtransactionlog(transaction_id int,errortype varchar(255),timestamp datetime,foreign key(transaction_id) references transaction(id));
-- desc failedtransactionlog;
-- drop table failedtransactionlog;

-- alter table employees
-- MODIFY COLUMN passhash varchar(20);
select * from account;
select * from customer;
select *from employee;
insert into employee (id, name, phone, position, login, passhash) values(12,"vipul",123456,"sde", "vipul", 1244);
insert into employee (id, name, phone, position, login, passhash) values(13,"vipul",123456,"sde", "vipul", 1244);
insert into employeeemployeeemployee (id, name, phone, position, branch_id, login, passhash) values(14,"vipul",123456,"sde", 12, "vipul", 1244);

UPDATE employee SET manager_id = 123 WHERE id = 12;
SELECT * FROM employee WHERE manager_id IS NOT NULL;
select * from branches;
desc branches;
desc employee;
truncate customer;
delete from customer where id =13;
ALTER TABLE employee DROP FOREIGN KEY employee_ibfk_1;
alter table account add foreign key(customer_id) references customer(id);
ALTER TABLE customer AUTO_INCREMENT = 1;
desc employee;
desc account;
SELECT *, branches.id as branch_id
FROM employee
INNER JOIN branches ON employee.id = branches.id;

