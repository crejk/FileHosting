create table files
(
    id   uuid    not null,
    name varchar not null,
    primary key (id)
);

create
index files_name_index on files(name);
