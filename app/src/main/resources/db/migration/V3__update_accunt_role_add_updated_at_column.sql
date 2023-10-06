ALTER TABLE account_role
ADD COLUMN IF NOT EXISTS updated_at timestamp without time zone;