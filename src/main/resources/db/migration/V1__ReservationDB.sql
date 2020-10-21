create table public.company (id  bigserial not null, created_at timestamp not null, updated_at timestamp not null, description varchar(255), name varchar(255), user_id int8, primary key (id));
create table public.group (id  bigserial not null, created_at timestamp not null, updated_at timestamp not null, description varchar(255), name varchar(255), primary key (id));
create table public.group_user (group_id int8 not null, user_id int8 not null);
create table public.reservation (id  bigserial not null, created_at timestamp not null, updated_at timestamp not null, created_by varchar(255), date date, description varchar(255), occupied_seats int4, updated_by varchar(255), used_by varchar(255), room_id int8, user_id int8, primary key (id));
create table public.room (id  bigserial not null, created_at timestamp not null, updated_at timestamp not null, distance_min int8, emergency_exits int8, name varchar(255), no_usable_locations int8, number_seats int4, size int8, venue_id int8, primary key (id));
create table public.seat (id  bigserial not null, created_at timestamp not null, updated_at timestamp not null, equipment varchar(255), number int4, taken boolean, room_id int8, primary key (id));
create table public.user (id  bigserial not null, created_at timestamp not null, updated_at timestamp not null, address varchar(255), email varchar(255), name varchar(255) not null, password varchar(255) not null, primary key (id));
create table public.venue (id  bigserial not null, created_at timestamp not null, updated_at timestamp not null, address varchar(255), name varchar(255), number_rooms int8, company_id int8, primary key (id));
	
alter table public.user drop constraint if exists UK_gj2fy3dcix7ph7k8684gka40c;
alter table public.user add constraint UK_gj2fy3dcix7ph7k8684gka40c unique (name);
alter table public.company add constraint FKdy4v2yb46hefqicjpyj7b7e4s foreign key (user_id) references public.user;
alter table public.group_user add constraint FK6u7jb50qa69gr3505uttxm86x foreign key (user_id) references public.user;
alter table public.group_user add constraint FKdh1aiux0pc0qmj4ht4yws77d7 foreign key (group_id) references public.group;
alter table public.reservation add constraint FKm8xumi0g23038cw32oiva2ymw foreign key (room_id) references public.room;
alter table public.reservation add constraint FKm4oimk0l1757o9pwavorj6ljg foreign key (user_id) references public.user;
alter table public.room add constraint FKpej81gq1p63tpv7edk69i68fg foreign key (venue_id) references public.venue;
alter table public.seat add constraint FKd7f42843rt05tt66t6vcb7s9u foreign key (room_id) references public.room;
alter table public.venue add constraint FKc52i91d7toxnb6kskbnk60dny foreign key (company_id) references public.company;