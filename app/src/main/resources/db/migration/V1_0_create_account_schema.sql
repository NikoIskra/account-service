CREATE TABLE IF NOT EXISTS account
(
    id uuid,
    email character varying(128) NOT NULL,
    username character varying(64) NOT NULL,
    password character varying(128) NOT NULL,
    status character varying(8) NOT NULL,
    create_at timestamp without time zone DEFAULT now(),
    update_at timestamp without time zone,
    PRIMARY KEY (id),
    UNIQUE (email, username)
);