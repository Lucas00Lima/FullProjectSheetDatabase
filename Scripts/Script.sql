use db000;
describe client;
truncate client;
truncate product;
select * from client;
show tables;
select * from next_id;
alter table client modify column id_doc_number VARCHAR(30) not null;
update client set phone = "";