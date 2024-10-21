CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
	email VARCHAR(50) NOT NULL,
	isactive BOOLEAN NOT NULL,
	api BOOLEAN NOT NULL,
    roles VARCHAR(50) NOT NULL,
	lastTokenDate TIMESTAMPTZ NOT NULL
);

CREATE TABLE IF NOT EXISTS setor (
    setorid SERIAL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS lotacao (
    lotacaoid SERIAL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS categorias (
    categoriaid SERIAL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS inventario (
    inventarioid SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    descricao TEXT,
    tipo VARCHAR(50) CHECK (tipo IN ('equipamento', 'mat.consumo', 'outros')),
    lotacaoid INT REFERENCES lotacao(lotacaoid) NOT NULL,
    setorid INT REFERENCES setor(setorid) NOT NULL,
    patrimonio VARCHAR(100) UNIQUE,
    numero_serie VARCHAR(100) UNIQUE,
    responsavel VARCHAR(100),
    categoriaid INT REFERENCES categorias(categoriaid) NOT NULL,
    status VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS entradas (
    entradaid SERIAL PRIMARY KEY,
    inventarioid INT NOT NULL REFERENCES inventario(inventarioid),
    tipoentrada VARCHAR(50) CHECK (tipoentrada IN ('doacao', 'licitacao')),  -- Corrigido o nome da coluna
    dataentrada DATE NOT NULL,
    quantidade SMALLINT NOT NULL,
    termorecebimento VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS saidas (
    saidaid SERIAL PRIMARY KEY,
    inventarioid INT REFERENCES inventario(inventarioid) NOT NULL,
	tiposaida VARCHAR(50) CHECK (tiposaida IN ('recebimento', 'descarte')),
    datasaida DATE NOT NULL,
    quantidade SMALLINT NOT NULL,
    termosaida VARCHAR(50) NULL
);

CREATE TABLE IF NOT EXISTS garantia (
    garantiaid SERIAL PRIMARY KEY,
    inventarioid INT REFERENCES inventario(inventarioid) NOT NULL,
    datainicio DATE NOT NULL,
    datafim DATE NOT NULL,
    detalhes VARCHAR(250) NULL
);
