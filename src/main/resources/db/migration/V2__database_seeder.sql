INSERT INTO perfis (nome) VALUES ('Perfil Default');

INSERT INTO plataformas (nome) VALUES ('Web e Back-end'), ('iOS'), ('Android');

INSERT INTO categorias (nome, tipo) 
VALUES 
('Tamanho do App', 'dia'), 
('Nível de UI', 'percentual'), 
('Usuários & Contas', 'dia'), 
('Conteúdo gerado pelo usuário', 'dia'),
('Data de localizações', 'dia'),
('Social e engajamento', 'dia'),
('Faturamento & e-commerce', 'dia'),
('Administração, feedback & análises', 'dia'),
('API externas e integrações', 'dia'),
('Segurança', 'dia'),
('Desenvolvimento específico do app', 'dia'),
('Equipe', 'monetario');

INSERT INTO funcionalidades (categoria_id, nome) 
VALUES 
    ((SELECT id FROM categorias WHERE nome = 'Tamanho do App'), 'Pequeno'),
    ((SELECT id FROM categorias WHERE nome = 'Tamanho do App'), 'Médio'),
    ((SELECT id FROM categorias WHERE nome = 'Tamanho do App'), 'Médio'),

    ((SELECT id FROM categorias WHERE nome = 'Nível de UI'), 'MVP'),
    ((SELECT id FROM categorias WHERE nome = 'Nível de UI'), 'Básico'),
    ((SELECT id FROM categorias WHERE nome = 'Nível de UI'), 'Profissional'),

    ((SELECT id FROM categorias WHERE nome = 'Usuários & Contas'), 'Cadastro por e-mail e senha'),
    ((SELECT id FROM categorias WHERE nome = 'Usuários & Contas'), 'Cadastro pelo Facebook'),
    ((SELECT id FROM categorias WHERE nome = 'Usuários & Contas'), 'Cadastro pelo Twitter'),
    ((SELECT id FROM categorias WHERE nome = 'Usuários & Contas'), 'Cadastro pelo Google'),
    ((SELECT id FROM categorias WHERE nome = 'Usuários & Contas'), 'Cadastro pelo LinkedIn'),
    ((SELECT id FROM categorias WHERE nome = 'Usuários & Contas'), 'Cadastro pelo Github'),
    ((SELECT id FROM categorias WHERE nome = 'Usuários & Contas'), 'E-mails de convite de usuário'),
    ((SELECT id FROM categorias WHERE nome = 'Usuários & Contas'), 'Contas Multi-tenant'),
    ((SELECT id FROM categorias WHERE nome = 'Usuários & Contas'), 'Subdomínios'),
    ((SELECT id FROM categorias WHERE nome = 'Usuários & Contas'), 'Domínios personalizados'),

    ((SELECT id FROM categorias WHERE nome = 'Conteúdo gerado pelo usuário'), 'Painel (Dashboard)'),
    ((SELECT id FROM categorias WHERE nome = 'Conteúdo gerado pelo usuário'), 'Feed de Atividades (Activity Feed)'),
    ((SELECT id FROM categorias WHERE nome = 'Conteúdo gerado pelo usuário'), 'Upload de Arquivos (File Uploading)'),
    ((SELECT id FROM categorias WHERE nome = 'Conteúdo gerado pelo usuário'), 'Upload de Mídia (Media Uploading)'),
    ((SELECT id FROM categorias WHERE nome = 'Conteúdo gerado pelo usuário'), 'Perfis de Usuário (User Profiles)'),
    ((SELECT id FROM categorias WHERE nome = 'Conteúdo gerado pelo usuário'), 'E-mails Transacionais (Transactional Emails)'),
    ((SELECT id FROM categorias WHERE nome = 'Conteúdo gerado pelo usuário'), 'Tags'),
    ((SELECT id FROM categorias WHERE nome = 'Conteúdo gerado pelo usuário'), 'Avaliações ou Comentários (Ratings or Reviews)'),
    ((SELECT id FROM categorias WHERE nome = 'Conteúdo gerado pelo usuário'), 'Processamento de Áudio/Vídeo'),
    ((SELECT id FROM categorias WHERE nome = 'Conteúdo gerado pelo usuário'), 'Pesquisa de Texto Livre (Free text searching)'),
    ((SELECT id FROM categorias WHERE nome = 'Conteúdo gerado pelo usuário'), 'Pesquisa (Searching)'),

    ((SELECT id FROM categorias WHERE nome = 'Data de localizações'), 'Calendário (Calendaring)'),
    ((SELECT id FROM categorias WHERE nome = 'Data de localizações'), 'Exibição de Dados de Mapa / Geolocalização'),
    ((SELECT id FROM categorias WHERE nome = 'Data de localizações'), 'Exibição de Marcadores/Regiões de Mapa Personalizados'),
    ((SELECT id FROM categorias WHERE nome = 'Data de localizações'), 'Reservas (Bookings)'),

    ((SELECT id FROM categorias WHERE nome = 'Social e engajamento'), 'Mensagens (Messaging)'),
    ((SELECT id FROM categorias WHERE nome = 'Social e engajamento'), 'Fóruns ou Comentários'),
    ((SELECT id FROM categorias WHERE nome = 'Social e engajamento'), 'Compartilhamento Social (Social Sharing)'),
    ((SELECT id FROM categorias WHERE nome = 'Social e engajamento'), 'Integração com Facebook Open Graph (Push to Facebook Open Graph)'),
    ((SELECT id FROM categorias WHERE nome = 'Social e engajamento'), 'Notificações Push (Push Notifications)'),

    ((SELECT id FROM categorias WHERE nome = 'Faturamento & e-commerce'), 'Planos de Assinatura (Subscription plans)'),
    ((SELECT id FROM categorias WHERE nome = 'Faturamento & e-commerce'), 'Processamento de Pagamentos (Payment processing)'),
    ((SELECT id FROM categorias WHERE nome = 'Faturamento & e-commerce'), 'Carrinho de Compras (Shopping Cart)'),
    ((SELECT id FROM categorias WHERE nome = 'Faturamento & e-commerce'), 'Marketplace de Usuários (User Marketplace)'),
    ((SELECT id FROM categorias WHERE nome = 'Faturamento & e-commerce'), 'Gerenciamento de Produtos (Product Management)'),
    ((SELECT id FROM categorias WHERE nome = 'Faturamento & e-commerce'), 'Compras dentro do Aplicativo (In-App Purchasing)'),
    ((SELECT id FROM categorias WHERE nome = 'Faturamento & e-commerce'), 'Coleta de Informações de Pagamento'),

    ((SELECT id FROM categorias WHERE nome = 'Administração, feedback & análises'), 'Integração com CMS (CMS Integration)'),
    ((SELECT id FROM categorias WHERE nome = 'Administração, feedback & análises'), 'Páginas de Administração de Usuários (User Admin pages)'),
    ((SELECT id FROM categorias WHERE nome = 'Administração, feedback & análises'), 'Moderação / Aprovação de Conteúdo (Moderation / Content Approval)'),
    ((SELECT id FROM categorias WHERE nome = 'Administração, feedback & análises'), 'Intercom'),
    ((SELECT id FROM categorias WHERE nome = 'Administração, feedback & análises'), 'Análises de Uso (Usage Analytics)'),
    ((SELECT id FROM categorias WHERE nome = 'Administração, feedback & análises'), 'Relatório de Erros (Crash Reporting)'),
    ((SELECT id FROM categorias WHERE nome = 'Administração, feedback & análises'), 'Monitoramento de Performance (Performance Monitoring)'),
    ((SELECT id FROM categorias WHERE nome = 'Administração, feedback & análises'), 'Suporte Multilíngue (Multilingual Support)'),

    ((SELECT id FROM categorias WHERE nome = 'API externas e integrações'), 'Conectar a um ou mais serviços de terceiros'),
    ((SELECT id FROM categorias WHERE nome = 'API externas e integrações'), 'Uma API para que terceiros integrem ao seu app'),
    ((SELECT id FROM categorias WHERE nome = 'API externas e integrações'), 'Envio de SMS (SMS Messaging)'),
    ((SELECT id FROM categorias WHERE nome = 'API externas e integrações'), 'Mascaramento de Número de Telefone (Phone Number Masking)'),

    ((SELECT id FROM categorias WHERE nome = 'Segurança'), 'Segurança baseada em Certificado SSL'),
    ((SELECT id FROM categorias WHERE nome = 'Segurança'), 'Proteção contra DoS (DoS protection)'),
    ((SELECT id FROM categorias WHERE nome = 'Segurança'), 'Autenticação em Duas Etapas (Two Factor Authentication)'),

    ((SELECT id FROM categorias WHERE nome = 'Desenvolvimento específico do app'), 'Apple Watch'),
    ((SELECT id FROM categorias WHERE nome = 'Desenvolvimento específico do app'), 'Dados de Saúde (Health Data)'),
    ((SELECT id FROM categorias WHERE nome = 'Desenvolvimento específico do app'), 'Design de Ícone do App (App Icon Design)'),
    ((SELECT id FROM categorias WHERE nome = 'Desenvolvimento específico do app'), 'Sincronização em Nuvem (Cloud Syncing)'),
    ((SELECT id FROM categorias WHERE nome = 'Desenvolvimento específico do app'), 'Dados de Sensores do Dispositivo (Device Sensor Data)'),
    ((SELECT id FROM categorias WHERE nome = 'Desenvolvimento específico do app'), 'Códigos de Barras ou QR Codes'),

    ((SELECT id FROM categorias WHERE nome = 'Equipe'), 'Taxa Diária de Design (Design Day Rate)'),
    ((SELECT id FROM categorias WHERE nome = 'Equipe'), 'Taxa Diária de Gerência de Projeto (Developer Day Rate)'),
    ((SELECT id FROM categorias WHERE nome = 'Equipe'), 'Taxa Diária de Desenvolvimento (Developer Day Rate)');

INSERT INTO perfis_categorias (perfil_id, categoria_id)
VALUES
    ((SELECT id FROM perfis WHERE nome = 'Perfil Default'), (SELECT id FROM categorias WHERE nome = 'Tamanho do App')),
    ((SELECT id FROM perfis WHERE nome = 'Perfil Default'), (SELECT id FROM categorias WHERE nome = 'Nível de UI')),
    ((SELECT id FROM perfis WHERE nome = 'Perfil Default'), (SELECT id FROM categorias WHERE nome = 'Usuários & Contas')),
    ((SELECT id FROM perfis WHERE nome = 'Perfil Default'),(SELECT id FROM categorias WHERE nome = 'Conteúdo gerado pelo usuário')),
    ((SELECT id FROM perfis WHERE nome = 'Perfil Default'), (SELECT id FROM categorias WHERE nome = 'Data de localizações')),
    ((SELECT id FROM perfis WHERE nome = 'Perfil Default'), (SELECT id FROM categorias WHERE nome = 'Social e engajamento')),
    ((SELECT id FROM perfis WHERE nome = 'Perfil Default'), (SELECT id FROM categorias WHERE nome = 'Faturamento & e-commerce')),
    ((SELECT id FROM perfis WHERE nome = 'Perfil Default'), (SELECT id FROM categorias WHERE nome = 'Administração, feedback & análises')),
    ((SELECT id FROM perfis WHERE nome = 'Perfil Default'), (SELECT id FROM categorias WHERE nome = 'API externas e integrações')),
    ((SELECT id FROM perfis WHERE nome = 'Perfil Default'), (SELECT id FROM categorias WHERE nome = 'Segurança')),
    ((SELECT id FROM perfis WHERE nome = 'Perfil Default'), (SELECT id FROM categorias WHERE nome = 'Desenvolvimento específico do app')),
    ((SELECT id FROM perfis WHERE nome = 'Perfil Default'), (SELECT id FROM categorias WHERE nome = 'Equipe'));

INSERT INTO perfis_plataformas (perfil_id, plataforma_id)
VALUES 
    ((SELECT id FROM perfis WHERE nome = 'Perfil Default'), (SELECT id FROM plataformas WHERE nome = 'Web e Back-end')),
    ((SELECT id FROM perfis WHERE nome = 'Perfil Default'), (SELECT id FROM plataformas WHERE nome = 'iOS')),
    ((SELECT id FROM perfis WHERE nome = 'Perfil Default'), (SELECT id FROM plataformas WHERE nome = 'Android'));

WITH perfil_plataforma_ids AS (
    SELECT pp.id AS perfil_plataforma_id, pl.nome AS plataforma_nome
    FROM perfis_plataformas pp
    JOIN perfis p ON pp.perfil_id = p.id
    JOIN plataformas pl ON pp.plataforma_id = pl.id
    WHERE p.nome = 'Perfil Default'
),
funcionalidades_ids AS (
    SELECT id AS funcionalidade_id, nome AS funcionalidade_nome FROM funcionalidades
)
INSERT INTO funcionalidade_perfil_plataforma (perfil_plataforma_id, funcionalidade_id, valor)
SELECT 
    ppi.perfil_plataforma_id, 
    fi.funcionalidade_id, 
    CASE 
        WHEN fi.funcionalidade_nome = 'Pequeno' THEN 10
        WHEN fi.funcionalidade_nome = 'Médio' THEN 30
        WHEN fi.funcionalidade_nome = 'Grande' THEN 50

        WHEN fi.funcionalidade_nome = 'MVP' THEN 0.3
        WHEN fi.funcionalidade_nome = 'Básico' THEN 0.5
        WHEN fi.funcionalidade_nome = 'Profissional' THEN 0.7

        WHEN fi.funcionalidade_nome = 'Cadastro por e-mail e senha' THEN 1
        WHEN fi.funcionalidade_nome = 'Cadastro pelo Facebook' THEN 2
        WHEN fi.funcionalidade_nome = 'Cadastro pelo Twitter' THEN 2
        WHEN fi.funcionalidade_nome = 'Cadastro pelo Google' THEN 2
        WHEN fi.funcionalidade_nome = 'Cadastro pelo LinkedIn' THEN 2
        WHEN fi.funcionalidade_nome = 'Cadastro pelo Github' THEN 2
        WHEN fi.funcionalidade_nome = 'E-mails de convite de usuário' AND ppi.plataforma_nome = 'Web e Back-end' THEN 2
        WHEN fi.funcionalidade_nome = 'E-mails de convite de usuário' AND ppi.plataforma_nome = 'iOS' THEN 0
        WHEN fi.funcionalidade_nome = 'E-mails de convite de usuário' AND ppi.plataforma_nome = 'Android' THEN 0
        WHEN fi.funcionalidade_nome = 'Contas Multi-tenant' AND ppi.plataforma_nome = 'Web e Back-end' THEN 3
        WHEN fi.funcionalidade_nome = 'Contas Multi-tenant' AND ppi.plataforma_nome = 'iOS' THEN 0
        WHEN fi.funcionalidade_nome = 'Contas Multi-tenant' AND ppi.plataforma_nome = 'Android' THEN 0
        WHEN fi.funcionalidade_nome = 'Subdomínios' AND ppi.plataforma_nome = 'Web e Back-end' THEN 4
        WHEN fi.funcionalidade_nome = 'Subdomínios' AND ppi.plataforma_nome = 'iOS' THEN 0
        WHEN fi.funcionalidade_nome = 'Subdomínios' AND ppi.plataforma_nome = 'Android' THEN 0
        WHEN fi.funcionalidade_nome = 'Domínios personalizados' AND ppi.plataforma_nome = 'Web e Back-end' THEN 4
        WHEN fi.funcionalidade_nome = 'Domínios personalizados' AND ppi.plataforma_nome = 'iOS' THEN 0
        WHEN fi.funcionalidade_nome = 'Domínios personalizados' AND ppi.plataforma_nome = 'Android' THEN 0

        WHEN fi.funcionalidade_nome = 'Painel (Dashboard)' THEN 5
        WHEN fi.funcionalidade_nome = 'Feed de Atividades (Activity Feed)' THEN 4
        WHEN fi.funcionalidade_nome = 'Upload de Arquivos (File Uploading)' AND ppi.plataforma_nome = 'Web e Back-end' THEN 4
        WHEN fi.funcionalidade_nome = 'Upload de Arquivos (File Uploading)' AND ppi.plataforma_nome = 'iOS' THEN 0
        WHEN fi.funcionalidade_nome = 'Upload de Arquivos (File Uploading)' AND ppi.plataforma_nome = 'Android' THEN 0
        WHEN fi.funcionalidade_nome = 'Upload de Mídia (Media Uploading)' AND ppi.plataforma_nome = 'Web e Back-end' THEN 0
        WHEN fi.funcionalidade_nome = 'Upload de Mídia (Media Uploading)' AND ppi.plataforma_nome = 'iOS' THEN 4
        WHEN fi.funcionalidade_nome = 'Upload de Mídia (Media Uploading)' AND ppi.plataforma_nome = 'Android' THEN 4
        WHEN fi.funcionalidade_nome = 'Perfis de Usuário (User Profiles)' THEN 2
        WHEN fi.funcionalidade_nome = 'E-mails Transacionais (Transactional Emails)' AND ppi.plataforma_nome = 'Web e Back-end' THEN 2
        WHEN fi.funcionalidade_nome = 'E-mails Transacionais (Transactional Emails)' AND ppi.plataforma_nome = 'iOS' THEN 0
        WHEN fi.funcionalidade_nome = 'E-mails Transacionais (Transactional Emails)' AND ppi.plataforma_nome = 'Android' THEN 0
        WHEN fi.funcionalidade_nome = 'Tags' THEN 2
        WHEN fi.funcionalidade_nome = 'Avaliações ou Comentários (Ratings or Reviews)' THEN 5
        WHEN fi.funcionalidade_nome = 'Processamento de Áudio/Vídeo' AND ppi.plataforma_nome = 'Web e Back-end' THEN 5
        WHEN fi.funcionalidade_nome = 'Processamento de Áudio/Vídeo' AND ppi.plataforma_nome = 'iOS' THEN 0
        WHEN fi.funcionalidade_nome = 'Processamento de Áudio/Vídeo' AND ppi.plataforma_nome = 'Android' THEN 0
        WHEN fi.funcionalidade_nome = 'Pesquisa de Texto Livre (Free text searching)' AND ppi.plataforma_nome = 'Web e Back-end' THEN 5
        WHEN fi.funcionalidade_nome = 'Pesquisa de Texto Livre (Free text searching)' AND ppi.plataforma_nome = 'iOS' THEN 0
        WHEN fi.funcionalidade_nome = 'Pesquisa de Texto Livre (Free text searching)' AND ppi.plataforma_nome = 'Android' THEN 0
        WHEN fi.funcionalidade_nome = 'Pesquisa (Searching)' AND ppi.plataforma_nome = 'Web e Back-end' THEN 0
        WHEN fi.funcionalidade_nome = 'Pesquisa (Searching)' AND ppi.plataforma_nome = 'iOS' THEN 3
        WHEN fi.funcionalidade_nome = 'Pesquisa (Searching)' AND ppi.plataforma_nome = 'Android' THEN 3

        WHEN fi.funcionalidade_nome = 'Calendário (Calendaring)' AND ppi.plataforma_nome = 'Web e Back-end' THEN 7
        WHEN fi.funcionalidade_nome = 'Calendário (Calendaring)' AND ppi.plataforma_nome = 'iOS' THEN 6
        WHEN fi.funcionalidade_nome = 'Calendário (Calendaring)' AND ppi.plataforma_nome = 'Android' THEN 6
        WHEN fi.funcionalidade_nome = 'Exibição de Dados de Mapa / Geolocalização' THEN 3
        WHEN fi.funcionalidade_nome = 'Exibição de Marcadores/Regiões de Mapa Personalizados' THEN 3
        WHEN fi.funcionalidade_nome = 'Reservas (Bookings)' AND ppi.plataforma_nome = 'Web e Back-end' THEN 8
        WHEN fi.funcionalidade_nome = 'Reservas (Bookings)' AND ppi.plataforma_nome = 'iOS' THEN 5
        WHEN fi.funcionalidade_nome = 'Reservas (Bookings)' AND ppi.plataforma_nome = 'Android' THEN 5

        WHEN fi.funcionalidade_nome = 'Mensagens (Messaging)' AND ppi.plataforma_nome = 'Web e Back-end' THEN 6
        WHEN fi.funcionalidade_nome = 'Mensagens (Messaging)' AND ppi.plataforma_nome = 'iOS' THEN 5
        WHEN fi.funcionalidade_nome = 'Mensagens (Messaging)' AND ppi.plataforma_nome = 'Android' THEN 5
        WHEN fi.funcionalidade_nome = 'Fóruns ou Comentários' THEN 5
        WHEN fi.funcionalidade_nome = 'Compartilhamento Social (Social Sharing)' AND ppi.plataforma_nome = 'Web e Back-end' THEN 2
        WHEN fi.funcionalidade_nome = 'Compartilhamento Social (Social Sharing)' AND ppi.plataforma_nome = 'iOS' THEN 1
        WHEN fi.funcionalidade_nome = 'Compartilhamento Social (Social Sharing)' AND ppi.plataforma_nome = 'Android' THEN 1
        WHEN fi.funcionalidade_nome = 'Integração com Facebook Open Graph (Push to Facebook Open Graph)' AND ppi.plataforma_nome = 'Web e Back-end' THEN 5
        WHEN fi.funcionalidade_nome = 'Integração com Facebook Open Graph (Push to Facebook Open Graph)' AND ppi.plataforma_nome = 'iOS' THEN 3
        WHEN fi.funcionalidade_nome = 'Integração com Facebook Open Graph (Push to Facebook Open Graph)' AND ppi.plataforma_nome = 'Android' THEN 3
        WHEN fi.funcionalidade_nome = 'Notificações Push (Push Notifications)' AND ppi.plataforma_nome = 'Web e Back-end' THEN 0
        WHEN fi.funcionalidade_nome = 'Notificações Push (Push Notifications)' AND ppi.plataforma_nome = 'iOS' THEN 3
        WHEN fi.funcionalidade_nome = 'Notificações Push (Push Notifications)' AND ppi.plataforma_nome = 'Android' THEN 3

        WHEN fi.funcionalidade_nome = 'Planos de Assinatura (Subscription plans)' AND ppi.plataforma_nome = 'Web e Back-end' THEN 8
        WHEN fi.funcionalidade_nome = 'Planos de Assinatura (Subscription plans)' AND ppi.plataforma_nome = 'iOS' THEN 0
        WHEN fi.funcionalidade_nome = 'Planos de Assinatura (Subscription plans)' AND ppi.plataforma_nome = 'Android' THEN 0
        WHEN fi.funcionalidade_nome = 'Processamento de Pagamentos (Payment processing)' THEN 5
        WHEN fi.funcionalidade_nome = 'Carrinho de Compras (Shopping Cart)' AND ppi.plataforma_nome = 'Web e Back-end' THEN 8
        WHEN fi.funcionalidade_nome = 'Carrinho de Compras (Shopping Cart)' AND ppi.plataforma_nome = 'iOS' THEN 5
        WHEN fi.funcionalidade_nome = 'Carrinho de Compras (Shopping Cart)' AND ppi.plataforma_nome = 'Android' THEN 5
        WHEN fi.funcionalidade_nome = 'Marketplace de Usuários (User Marketplace)' AND ppi.plataforma_nome = 'Web e Back-end' THEN 12
        WHEN fi.funcionalidade_nome = 'Marketplace de Usuários (User Marketplace)' AND ppi.plataforma_nome = 'iOS' THEN 0
        WHEN fi.funcionalidade_nome = 'Marketplace de Usuários (User Marketplace)' AND ppi.plataforma_nome = 'Android' THEN 0
        WHEN fi.funcionalidade_nome = 'Gerenciamento de Produtos (Product Management)' AND ppi.plataforma_nome = 'Web e Back-end' THEN 4
        WHEN fi.funcionalidade_nome = 'Gerenciamento de Produtos (Product Management)' AND ppi.plataforma_nome = 'iOS' THEN 0
        WHEN fi.funcionalidade_nome = 'Gerenciamento de Produtos (Product Management)' AND ppi.plataforma_nome = 'Android' THEN 0
        WHEN fi.funcionalidade_nome = 'Compras dentro do Aplicativo (In-App Purchasing)' AND ppi.plataforma_nome = 'Web e Back-end' THEN 0
        WHEN fi.funcionalidade_nome = 'Compras dentro do Aplicativo (In-App Purchasing)' AND ppi.plataforma_nome = 'iOS' THEN 5
        WHEN fi.funcionalidade_nome = 'Compras dentro do Aplicativo (In-App Purchasing)' AND ppi.plataforma_nome = 'Android' THEN 5
        WHEN fi.funcionalidade_nome = 'Coleta de Informações de Pagamento' AND ppi.plataforma_nome = 'Web e Back-end' THEN 0
        WHEN fi.funcionalidade_nome = 'Coleta de Informações de Pagamento' AND ppi.plataforma_nome = 'iOS' THEN 3
        WHEN fi.funcionalidade_nome = 'Coleta de Informações de Pagamento' AND ppi.plataforma_nome = 'Android' THEN 3

        WHEN fi.funcionalidade_nome = 'Integração com CMS (CMS Integration)' AND ppi.plataforma_nome = 'Web e Back-end' THEN 7
        WHEN fi.funcionalidade_nome = 'Integração com CMS (CMS Integration)' AND ppi.plataforma_nome = 'iOS' THEN 0
        WHEN fi.funcionalidade_nome = 'Integração com CMS (CMS Integration)' AND ppi.plataforma_nome = 'Android' THEN 0
        WHEN fi.funcionalidade_nome = 'Páginas de Administração de Usuários (User Admin pages)' AND ppi.plataforma_nome = 'Web e Back-end' THEN 3
        WHEN fi.funcionalidade_nome = 'Páginas de Administração de Usuários (User Admin pages)' AND ppi.plataforma_nome = 'iOS' THEN 0
        WHEN fi.funcionalidade_nome = 'Páginas de Administração de Usuários (User Admin pages)' AND ppi.plataforma_nome = 'Android' THEN 0
        WHEN fi.funcionalidade_nome = 'Moderação / Aprovação de Conteúdo (Moderation / Content Approval)' AND ppi.plataforma_nome = 'Web e Back-end' THEN 4
        WHEN fi.funcionalidade_nome = 'Moderação / Aprovação de Conteúdo (Moderation / Content Approval)' AND ppi.plataforma_nome = 'iOS' THEN 0
        WHEN fi.funcionalidade_nome = 'Moderação / Aprovação de Conteúdo (Moderation / Content Approval)' AND ppi.plataforma_nome = 'Android' THEN 0
        WHEN fi.funcionalidade_nome = 'Intercom' THEN 3
        WHEN fi.funcionalidade_nome = 'Análises de Uso (Usage Analytics)' THEN 3
        WHEN fi.funcionalidade_nome = 'Relatório de Erros (Crash Reporting)' AND ppi.plataforma_nome = 'Web e Back-end' THEN 0
        WHEN fi.funcionalidade_nome = 'Relatório de Erros (Crash Reporting)' AND ppi.plataforma_nome = 'iOS' THEN 1
        WHEN fi.funcionalidade_nome = 'Relatório de Erros (Crash Reporting)' AND ppi.plataforma_nome = 'Android' THEN 1
        WHEN fi.funcionalidade_nome = 'Monitoramento de Performance (Performance Monitoring)' AND ppi.plataforma_nome = 'Web e Back-end' THEN 1
        WHEN fi.funcionalidade_nome = 'Monitoramento de Performance (Performance Monitoring)' AND ppi.plataforma_nome = 'iOS' THEN 0
        WHEN fi.funcionalidade_nome = 'Monitoramento de Performance (Performance Monitoring)' AND ppi.plataforma_nome = 'Android' THEN 0
        WHEN fi.funcionalidade_nome = 'Suporte Multilíngue (Multilingual Support)' THEN 4

        WHEN fi.funcionalidade_nome = 'Conectar a um ou mais serviços de terceiros' AND ppi.plataforma_nome = 'Web e Back-end' THEN 6
        WHEN fi.funcionalidade_nome = 'Conectar a um ou mais serviços de terceiros' AND ppi.plataforma_nome = 'iOS' THEN 3
        WHEN fi.funcionalidade_nome = 'Conectar a um ou mais serviços de terceiros' AND ppi.plataforma_nome = 'Android' THEN 3
        WHEN fi.funcionalidade_nome = 'Uma API para que terceiros integrem ao seu app' AND ppi.plataforma_nome = 'Web e Back-end' THEN 8
        WHEN fi.funcionalidade_nome = 'Uma API para que terceiros integrem ao seu app' AND ppi.plataforma_nome = 'iOS' THEN 0
        WHEN fi.funcionalidade_nome = 'Uma API para que terceiros integrem ao seu app' AND ppi.plataforma_nome = 'Android' THEN 0
        WHEN fi.funcionalidade_nome = 'Envio de SMS (SMS Messaging)' THEN 4
        WHEN fi.funcionalidade_nome = 'Mascaramento de Número de Telefone (Phone Number Masking)' THEN 4

        WHEN fi.funcionalidade_nome = 'Segurança baseada em Certificado SSL' AND ppi.plataforma_nome = 'Web e Back-end' THEN 3
        WHEN fi.funcionalidade_nome = 'Segurança baseada em Certificado SSL' AND ppi.plataforma_nome = 'iOS' THEN 0
        WHEN fi.funcionalidade_nome = 'Segurança baseada em Certificado SSL' AND ppi.plataforma_nome = 'Android' THEN 0
        WHEN fi.funcionalidade_nome = 'Proteção contra DoS (DoS protection)' AND ppi.plataforma_nome = 'Web e Back-end' THEN 5
        WHEN fi.funcionalidade_nome = 'Proteção contra DoS (DoS protection)' AND ppi.plataforma_nome = 'iOS' THEN 0
        WHEN fi.funcionalidade_nome = 'Proteção contra DoS (DoS protection)' AND ppi.plataforma_nome = 'Android' THEN 0
        WHEN fi.funcionalidade_nome = 'Autenticação em Duas Etapas (Two Factor Authentication)' THEN 5

        WHEN fi.funcionalidade_nome = 'Design de Ícone do App (App Icon Design)' AND ppi.plataforma_nome = 'Web e Back-end' THEN 0
        WHEN fi.funcionalidade_nome = 'Design de Ícone do App (App Icon Design)' AND ppi.plataforma_nome = 'iOS' THEN 7
        WHEN fi.funcionalidade_nome = 'Design de Ícone do App (App Icon Design)' AND ppi.plataforma_nome = 'Android' THEN 7
        WHEN fi.funcionalidade_nome = 'Sincronização em Nuvem (Cloud Syncing)' AND ppi.plataforma_nome = 'Web e Back-end' THEN 0
        WHEN fi.funcionalidade_nome = 'Sincronização em Nuvem (Cloud Syncing)' AND ppi.plataforma_nome = 'iOS' THEN 5
        WHEN fi.funcionalidade_nome = 'Sincronização em Nuvem (Cloud Syncing)' AND ppi.plataforma_nome = 'Android' THEN 5
        WHEN fi.funcionalidade_nome = 'Dados de Sensores do Dispositivo (Device Sensor Data)' AND ppi.plataforma_nome = 'Web e Back-end' THEN 0
        WHEN fi.funcionalidade_nome = 'Dados de Sensores do Dispositivo (Device Sensor Data)' AND ppi.plataforma_nome = 'iOS' THEN 5
        WHEN fi.funcionalidade_nome = 'Dados de Sensores do Dispositivo (Device Sensor Data)' AND ppi.plataforma_nome = 'Android' THEN 5
        WHEN fi.funcionalidade_nome = 'Códigos de Barras ou QR Codes' AND ppi.plataforma_nome = 'Web e Back-end' THEN 0
        WHEN fi.funcionalidade_nome = 'Códigos de Barras ou QR Codes' AND ppi.plataforma_nome = 'iOS' THEN 2
        WHEN fi.funcionalidade_nome = 'Códigos de Barras ou QR Codes' AND ppi.plataforma_nome = 'Android' THEN 2
        WHEN fi.funcionalidade_nome = 'Dados de Saúde (Health Data)' AND ppi.plataforma_nome = 'Web e Back-end' THEN 0
        WHEN fi.funcionalidade_nome = 'Dados de Saúde (Health Data)' AND ppi.plataforma_nome = 'iOS' THEN 4
        WHEN fi.funcionalidade_nome = 'Dados de Saúde (Health Data)' AND ppi.plataforma_nome = 'Android' THEN 4
        WHEN fi.funcionalidade_nome = 'Apple Watch' AND ppi.plataforma_nome = 'Web e Back-end' THEN 0
        WHEN fi.funcionalidade_nome = 'Apple Watch' AND ppi.plataforma_nome = 'iOS' THEN 7
        WHEN fi.funcionalidade_nome = 'Apple Watch' AND ppi.plataforma_nome = 'Android' THEN 0

        WHEN fi.funcionalidade_nome = 'Taxa Diária de Design (Design Day Rate)' AND ppi.plataforma_nome = 'Web e Back-end' THEN 450
        WHEN fi.funcionalidade_nome = 'Taxa Diária de Design (Design Day Rate)' AND ppi.plataforma_nome = 'iOS' THEN 450
        WHEN fi.funcionalidade_nome = 'Taxa Diária de Design (Design Day Rate)' AND ppi.plataforma_nome = 'Android' THEN 450
        WHEN fi.funcionalidade_nome = 'Taxa Diária de Gerência de Projeto (Developer Day Rate)' AND ppi.plataforma_nome = 'Web e Back-end' THEN 300
        WHEN fi.funcionalidade_nome = 'Taxa Diária de Gerência de Projeto (Developer Day Rate)' AND ppi.plataforma_nome = 'iOS' THEN 300
        WHEN fi.funcionalidade_nome = 'Taxa Diária de Gerência de Projeto (Developer Day Rate)' AND ppi.plataforma_nome = 'Android' THEN 300
        WHEN fi.funcionalidade_nome = 'Taxa Diária de Desenvolvimento (Developer Day Rate)' AND ppi.plataforma_nome = 'Web e Back-end' THEN 450
        WHEN fi.funcionalidade_nome = 'Taxa Diária de Desenvolvimento (Developer Day Rate)' AND ppi.plataforma_nome = 'iOS' THEN 450
        WHEN fi.funcionalidade_nome = 'Taxa Diária de Desenvolvimento (Developer Day Rate)' AND ppi.plataforma_nome = 'Android' THEN 450

        WHEN fi.funcionalidade_nome = 'Gerente de projeto' THEN 10
    END AS valor
FROM perfil_plataforma_ids ppi
JOIN funcionalidades_ids fi 
ON fi.funcionalidade_nome IN (
    'Pequeno', 
    'Médio', 
    'Grande', 
    'MVP', 
    'Básico', 
    'Profissional', 
    'Cadastro por e-mail e senha', 
    'Cadastro pelo Facebook', 
    'Cadastro pelo Twitter', 
    'Cadastro pelo Google', 
    'Cadastro pelo LinkedIn', 
    'Cadastro pelo Github', 
    'E-mails de convite de usuário', 
    'Contas Multi-tenant', 
    'Subdomínios', 
    'Domínios personalizados',
    'Painel (Dashboard)',
    'Feed de Atividades (Activity Feed)',
    'Upload de Arquivos (File Uploading)',
    'Upload de Mídia (Media Uploading)',
    'Perfis de Usuário (User Profiles',
    'E-mails Transacionais (Transactional Emails)',
    'Tags',
    'Avaliações ou Comentários (Ratings or Reviews)',
    'Processamento de Áudio/Vídeo',
    'Pesquisa de Texto Livre (Free text searching)',
    'Pesquisa (Searching)',
    'Calendário (Calendaring)',
    'Exibição de Dados de Mapa / Geolocalização',
    'Exibição de Marcadores/Regiões de Mapa Personalizados',
    'Reservas (Bookings)',
    'Mensagens (Messaging)',
    'Fóruns ou Comentários',
    'Compartilhamento Social (Social Sharing)',
    'Integração com Facebook Open Graph (Push to Facebook Open Graph)',
    'Notificações Push (Push Notifications',
    'Planos de Assinatura (Subscription plans)',
    'Processamento de Pagamentos (Payment processing)',
    'Carrinho de Compras (Shopping Cart)',
    'Marketplace de Usuários (User Marketplace)',
    'Gerenciamento de Produtos (Product Management)',
    'Compras dentro do Aplicativo (In-App Purchasing)',
    'Coleta de Informações de Pagamento',
    'Integração com CMS (CMS Integration)',
    'Páginas de Administração de Usuários (User Admin pages)',
    'Moderação / Aprovação de Conteúdo (Moderation / Content Approval)',
    'Intercom',
    'Análises de Uso (Usage Analytics)',
    'Relatório de Erros (Crash Reporting)',
    'Monitoramento de Performance (Performance Monitoring)',
    'Suporte Multilíngue (Multilingual Support)',
    'Conectar a um ou mais serviços de terceiros',
    'Uma API para que terceiros integrem ao seu app',
    'Envio de SMS (SMS Messaging)',
    'Mascaramento de Número de Telefone (Phone Number Masking)',
    'Segurança baseada em Certificado SSL',
    'Proteção contra DoS (DoS protection)',
    'Autenticação em Duas Etapas (Two Factor Authentication)',
    'Design de Ícone do App (App Icon Design)',
    'Sincronização em Nuvem (Cloud Syncing)',
    'Dados de Sensores do Dispositivo (Device Sensor Data)',
    'Códigos de Barras ou QR Codes',
    'Dados de Saúde (Health Data)',
    'Apple Watch',
    'Gerente de projeto',
    'Taxa Diária de Design (Design Day Rate)',
    'Taxa Diária de Gerência de Projeto (Developer Day Rate)',
    'Taxa Diária de Desenvolvimento (Developer Day Rate)'
)
WHERE ppi.plataforma_nome IN ('Web e Back-end', 'iOS', 'Android');

