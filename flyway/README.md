# Introduction

This project experiments th flyway migration using H2 and MySQL respectively.
To run the migration, execute:

    mvn flyway:migrate \
        -Dflyway.url="jdbc:mysql://localhost:3306/person" \
        -Dflyway.user=mfg \
        -Dflyway.password=abc

## MySQL Setup

Provision a local MySQL database using docker:

    docker-compose up -d -f docker-compose.yml

or use the [localenv][1]:
    git clone https://github.com/schnell18/localenv.git
    cd localenv
    ./infractl.sh start mariadb

Create a database named `person` as follows:

~~~~sql
drop database if exists person;
create database if not exists person
    default character set utf8mb4
    default collate utf8mb4_general_ci;
create user if not exists mfg@'%' identified by 'abc';
create user if not exists mfg@'localhost' identified by 'abc';
grant all on person.* to 'mfg'@'%';
grant all on person.* to 'mfg'@'localhost';
~~~~


[1]: https://github.com/schnell18/localenv.git
