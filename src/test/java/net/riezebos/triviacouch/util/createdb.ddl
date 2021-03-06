create table speler (id decimal(10),profielNaam varchar(20),wachtwoord varchar(6),winPercentage decimal(10),correctPercentage decimal(10),PRIMARY KEY(id));
create table spelsessie (id decimal(10),vraagID decimal(10),spelerdID_winnaar decimal(10), status varchar(10), PRIMARY KEY(id));
create table deelnemer (id decimal(10),sessieID decimal(10),spelerID decimal(10),spelerScore decimal(10), PRIMARY KEY(id));
create table vraag (id decimal(10),vraag varchar(255),PRIMARY KEY(id));
create table antwoord (id decimal(10),correct_jn varchar(1),antwoord varchar(255), vraagID decimal(10), PRIMARY KEY(id));
create table spelvraag (id decimal(10),sessieID decimal(10),vraagID decimal(10), PRIMARY KEY(id));
create table deelnemerantwoord (id decimal(10),antwoordID decimal(10),deelnemerID decimal(10), PRIMARY KEY(id));
create table highscores (id decimal(10), spelerid decimal(10), score decimal(10));
alter table highscores add constraint highscores_speler foreign key (spelerID) references speler(id);
alter table spelsessie add constraint spelsessie_spelvraag foreign key (vraagID) references spelvraag(id);
alter table spelvraag add constraint spelvraag_uk unique (sessieID, vraagID);
alter table antwoord add constraint antwoord_vraag foreign key (vraagID) references vraag(id);
alter table deelnemer add constraint deelnemer_spelsessie foreign key (sessieID) references spelsessie(id);
alter table deelnemerantwoord add constraint deelnemerantwoord_deelnemer foreign key (deelnemerID) references deelnemer(id) ;
alter table deelnemerantwoord add constraint deelnemerantwoord_antwoord foreign key (antwoordID) references antwoord(id);