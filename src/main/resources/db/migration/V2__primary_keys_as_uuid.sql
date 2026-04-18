CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

ALTER TABLE IF EXISTS public.cars DROP COLUMN IF EXISTS id;

ALTER TABLE IF EXISTS public.cars
    ADD COLUMN id uuid;

UPDATE cars
SET id = uuid_generate_v4()
WHERE id IS NULL;

ALTER TABLE public.cars ALTER COLUMN id SET NOT NULL;

ALTER TABLE IF EXISTS public.cars
    ADD PRIMARY KEY (id);


ALTER TABLE IF EXISTS public.motorcycles DROP COLUMN IF EXISTS id;

ALTER TABLE IF EXISTS public.motorcycles
    ADD COLUMN id uuid;

UPDATE motorcycles
SET id = uuid_generate_v4()
WHERE id IS NULL;

ALTER TABLE public.motorcycles ALTER COLUMN id SET NOT NULL;

ALTER TABLE IF EXISTS public.motorcycles
    ADD PRIMARY KEY (id);