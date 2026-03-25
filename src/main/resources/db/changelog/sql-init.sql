CREATE TABLE IF NOT EXISTS public.movie (
                              id bigserial NOT NULL,
                              title varchar(120) NOT NULL,
                              director varchar(120) NOT NULL,
                              release_year int4 NOT NULL,
                              rating numeric(3, 1) NOT NULL,
                              available bool DEFAULT true NOT NULL
);


CREATE TABLE IF NOT EXISTS public.users (
                              id bigserial NOT NULL,
                              login varchar(120) NOT NULL,
                              hash_password text NOT NULL,
                              is_active bool DEFAULT true NOT NULL,
                              CONSTRAINT users_login_key UNIQUE (login),
                              CONSTRAINT users_pkey PRIMARY KEY (id)
);