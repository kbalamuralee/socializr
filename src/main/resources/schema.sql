create table if not exists posts (
	id uuid default random_uuid() primary key,
	user_id uuid not null,
	content text not null,
	creation_timestamp timestamp with time zone not null default now(),
	last_update_timestamp timestamp with time zone not null default now()
);
