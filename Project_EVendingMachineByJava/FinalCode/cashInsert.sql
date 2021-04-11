DELETE
from change
where change_id is null;
insert into Change(change_id, change_type, change_quantity)
values (1, '$100', 100),
       (2, '$50', 100),
       (3, '$20', 100),
       (4, '$10', 100),
       (5, '$5', 100),
       (6, '$2', 100),
       (7, '$1', 100),
       (8, '50c', 100),
       (9, '20c', 100),
       (10, '10c', 100),
       (11, '5c', 100);

select change_type, change_quantity
from Change;

update Change
set change_quantity=100
where change_type = '$100';
