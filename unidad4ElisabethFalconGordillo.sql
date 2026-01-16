drop database if exists festival_musica;
create database festival_musica;
use festival_musica;

CREATE TABLE escenario (
    id_escenario INT UNSIGNED PRIMARY KEY check(id_escenario between 1 and 999),
    nombre varchar(50) unique not null,
    aforo int unsigned check(aforo between 100 and 100000) not null
);

CREATE TABLE artista (
    id_artista INT UNSIGNED PRIMARY KEY,
    genero varchar(40) not null , 
    check(genero = 'rock' or genero = 'pop' or genero = 'indie' or genero = 'electronica' or genero = 'rap'),
    nombre_artista varchar(50) not null,
    pais varchar(15) not null
);

alter table artista modify nombre_artista varchar(50) unique not null ;

CREATE TABLE concierto (
    id_concierto INT UNSIGNED PRIMARY KEY,
    fecha date not null,
    hora time not null,
    id_escenario int unsigned check(id_escenario between 1 and 999),
    constraint fk_id_escenario
    foreign key (id_escenario) references escenario(id_escenario) on delete cascade on update restrict
);

CREATE TABLE actuacion (
    id_artista INT UNSIGNED,
    id_concierto int unsigned,
    precio decimal(5,2) null,
    primary key (id_artista , id_concierto),
    constraint fk_id_artista
    foreign key (id_artista) references artista(id_artista) on delete cascade on update restrict,
    constraint fk_id_concierto
    foreign key (id_concierto) references concierto(id_concierto) on delete cascade on update restrict
    );
    
    alter table artista modify pais varchar(40) not null;
    alter table escenario add column tipo enum('principal','secundario','acustico') not null;
    alter table actuacion modify precio decimal(5,2) null check(precio>0);
     
	alter table concierto drop constraint fk_id_escenario;
    alter table concierto add constraint fk_id_escenario foreign key (id_escenario) references escenario(id_escenario) on delete restrict on update cascade;
    alter table actuacion drop constraint fk_id_artista;
    alter table actuacion add constraint fk_id_artista foreign key (id_artista) references artista(id_artista) on delete restrict on update cascade;
    alter table actuacion drop constraint fk_id_concierto;
    alter table actuacion add constraint fk_id_concierto foreign key (id_concierto) references concierto(id_concierto) on delete restrict on update cascade;
    
    
    