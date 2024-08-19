CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS setor (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS lotacao (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS categorias (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS inventario (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    descricao TEXT,
    tipo VARCHAR(50) CHECK (tipo IN ('equipamento', 'mat.consumo', 'outros')),
    lotacao_id INT REFERENCES lotacao(id),
    setor_id INT REFERENCES setor(id),
    patrimonio VARCHAR(100) UNIQUE,
    numero_serie VARCHAR(100) UNIQUE,
    responsavel VARCHAR(100),
    categoria_id INT REFERENCES categorias(id),
    status VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS entradas (
    id SERIAL PRIMARY KEY,
    inventario_id INT REFERENCES inventario(id),
    tipo_entrada VARCHAR(50) CHECK (tipo_entrada IN ('doacao', 'licitacao')),
	data_entrada DATE NOT NULL,
    quantidade SMALLINT NOT NULL,
    termo_recebimento VARCHAR(50) NULL
);

CREATE TABLE IF NOT EXISTS saidas (
    id SERIAL PRIMARY KEY,
    inventario_id INT REFERENCES inventario(id),
	tipo_saida VARCHAR(50) CHECK (tipo_saida IN ('recebimento', 'descarte')),
    data_saida DATE NOT NULL,
    quantidade SMALLINT NOT NULL,
    termo_saida VARCHAR(50) NULL
);

CREATE TABLE IF NOT EXISTS garantia (
    id SERIAL PRIMARY KEY,
    inventario_id INT REFERENCES inventario(id),
    data_inicio DATE NOT NULL,
    data_fim DATE NOT NULL,
    detalhes VARCHAR(250) NULL
);
