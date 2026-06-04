create table task
(
    id        serial primary key,
    task_name varchar(255) not null,
    status    varchar(50)  not null
);

create or replace procedure add_task(task_name_in varchar, status_in varchar)
    language plpgsql
as
$$
begin
    insert into task(task_name, status) values (task_name_in, status_in);
end;
$$;

create or replace function list_tasks()
    returns table
            (
                id        int,
                task_name varchar,
                status    varchar
            )
    language plpgsql
as
$$
begin
    return query select t.id, t.task_name, t.status from task t order by t.id;
end;
$$;

create or replace procedure update_task_status(id_in int, status_in varchar)
    language plpgsql
as
$$
begin
    update task set status = status_in where id = id_in;
end;
$$;

create or replace procedure delete_task(id_in int)
    language plpgsql
as
$$
begin
    delete from task where id = id_in;
end;
$$;

create or replace function search_task_by_name(p_name varchar)
    returns table
            (
                id        int,
                task_name varchar,
                status    varchar
            )
    language plpgsql
as
$$
begin
    return query select t.id, t.task_name, t.status from task t where t.task_name ilike '%' || p_name || '%';
end;
$$;

create or replace function task_statistics()
    returns table
            (
                status varchar,
                total  BIGINT
            )
    language plpgsql
as
$$
begin
    return query select t.status, count(t.id) from task t group by t.status;
end;
$$

