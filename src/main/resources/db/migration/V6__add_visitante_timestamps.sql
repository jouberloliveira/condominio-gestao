-- Add entry/exit timestamps and additional fields to visitantes
ALTER TABLE visitantes ADD COLUMN IF NOT EXISTS data_hora_entrada TIMESTAMP;
ALTER TABLE visitantes ADD COLUMN IF NOT EXISTS data_hora_saida TIMESTAMP;
ALTER TABLE visitantes ADD COLUMN IF NOT EXISTS placa VARCHAR(15);
ALTER TABLE visitantes ADD COLUMN IF NOT EXISTS observacao TEXT;
