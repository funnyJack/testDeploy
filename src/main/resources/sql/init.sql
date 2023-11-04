create
database if not exists testDeploy;
use
test;
create table if not exists test
(
    name varchar
(
    128
) not null,
    message varchar
(
    128
) not null,
    primary key
(
    name
)
    );

insert into test value ("鹏力","你好，我叫鹏力");
