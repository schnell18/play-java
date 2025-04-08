alter table PERSON add column(
    EMAIL varchar(100) null
);

update PERSON set email = 'Axel@hotmail.com' where id = 1;
update PERSON set email = 'MrFoo@hotmail.com' where id = 2;
update PERSON set email = 'MsBar@hotmail.com' where id = 3;
update PERSON set email = 'Justin@hotmail.com' where id = 4;
update PERSON set email = 'Sunny@hotmail.com' where id = 5;
update PERSON set email = 'Jessica@hotmail.com' where id = 6;
