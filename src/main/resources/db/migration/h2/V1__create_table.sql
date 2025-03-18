CREATE TABLE IF NOT EXISTS usuarios (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    nome TEXT NOT NULL,
    email TEXT UNIQUE NOT NULL,
    senha TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS perfis (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    nome TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS plataformas (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    nome TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS categorias (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    nome TEXT NOT NULL,
    tipo TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS projetos (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    usuario_id INTEGER NOT NULL,
    perfil_id INTEGER NOT NULL,
    nome TEXT NOT NULL,
    custo_hardware REAL NOT NULL DEFAULT 0,
    custo_software REAL NOT NULL DEFAULT 0,
    custo_riscos REAL NOT NULL DEFAULT 0,
    custo_garantia REAL NOT NULL DEFAULT 0,
    fundo_reserva REAL NOT NULL DEFAULT 0,
    outros_custos REAL NOT NULL DEFAULT 0,
    percentual_imposto REAL NOT NULL DEFAULT 0,
    percentual_lucro REAL NOT NULL DEFAULT 0,
    total_dias INTEGER NOT NULL DEFAULT 0,
    valor_total REAL NOT NULL DEFAULT 0,
    data_criacao TEXT NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    FOREIGN KEY (perfil_id) REFERENCES perfis(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS perfis_categorias (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    perfil_id INTEGER NOT NULL,
    categoria_id INTEGER NOT NULL,
    FOREIGN KEY (perfil_id) REFERENCES perfis(id) ON DELETE CASCADE,
    FOREIGN KEY (categoria_id) REFERENCES categorias(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS perfis_plataformas (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    perfil_id INTEGER NOT NULL,
    plataforma_id INTEGER NOT NULL,
    FOREIGN KEY (perfil_id) REFERENCES perfis(id) ON DELETE CASCADE,
    FOREIGN KEY (plataforma_id) REFERENCES plataformas(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS funcionalidades (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    categoria_id INTEGER NOT NULL,
    nome TEXT NOT NULL,
    FOREIGN KEY (categoria_id) REFERENCES categorias(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS funcionalidade_perfil_plataforma (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    perfil_plataforma_id INTEGER NOT NULL,
    funcionalidade_id INTEGER NOT NULL,
    valor REAL NOT NULL DEFAULT 0,
    FOREIGN KEY (perfil_plataforma_id) REFERENCES perfis_plataformas(id) ON DELETE CASCADE,
    FOREIGN KEY (funcionalidade_id) REFERENCES funcionalidades(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS projetos_plataformas (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    projeto_id INTEGER NOT NULL,
    plataforma_id INTEGER NOT NULL,
    FOREIGN KEY (projeto_id) REFERENCES projetos(id) ON DELETE CASCADE,
    FOREIGN KEY (plataforma_id) REFERENCES plataformas(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS funcionalidade_projeto_plataforma (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    funcionalidade_id INTEGER NOT NULL,
    projeto_plataforma_id INTEGER NOT NULL,
    valor REAL NOT NULL DEFAULT 0,
    FOREIGN KEY (funcionalidade_id) REFERENCES funcionalidades(id) ON DELETE CASCADE,
    FOREIGN KEY (projeto_plataforma_id) REFERENCES projetos_plataformas(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS projetos_compartilhados (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    projeto_id INTEGER NOT NULL,
    usuario_id INTEGER NOT NULL,
    FOREIGN KEY (projeto_id) REFERENCES projetos(id) ON DELETE CASCADE,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
);
