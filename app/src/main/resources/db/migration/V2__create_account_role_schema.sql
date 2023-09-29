CREATE TABLE IF NOT EXISTS account_role
(
    id uuid NOT NULL DEFAULT gen_random_uuid(),
    account_id uuid,
    role character varying(8) NOT NULL,
    status character varying(8) NOT NULL,
    created_at timestamp without time zone DEFAULT now(),
    CONSTRAINT account_role_pkey PRIMARY KEY (id),
    CONSTRAINT account_role_account_id_role_key UNIQUE (account_id, role),
    CONSTRAINT account_role_account_id_fkey FOREIGN KEY (account_id)
        REFERENCES account (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)