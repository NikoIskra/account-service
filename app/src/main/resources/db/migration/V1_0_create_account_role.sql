CREATE TABLE IF NOT EXISTS account_role
(
    id uuid,
    account_id uuid,
    role character varying(8) NOT NULL,
    status character varying(8) NOT NULL,
    create_at timestamp without time zone NOT NULL DEFAULT now(),
    PRIMARY KEY (id),
    UNIQUE (account_id),
    FOREIGN KEY (account_id)
        REFERENCES users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);