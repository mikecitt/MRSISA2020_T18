INSERT INTO clinic_center_admin (
    id,
    email_address,
    password,
    last_password_reset_date,
    first_name,
    last_name,
    jmbg,
    address,
    city,
    country,
    contact,
    active
) VALUES (
    1, 'ccadmin@gmail.com', '$2a$10$SHepdD5KBoQUkeVLwEJmvu90794GPxBLZ2Ps0hWttClzrM8QGcd4.',
    CURRENT_TIMESTAMP(),
    'Gordan', 'Mačkić', '0210980653452', 'Petra Konjovića 10', 'Novi Sad', 'Srbija', '0611454215', TRUE
);

INSERT INTO clinic (address, description, name) VALUES
    ('Kralja Petra 42', 'Klinika se bavi urološkim problemima i sadrži 1 CT skener.', 'Urologija'),
    ('Gajeva 10', 'Infektivna klinika opremljena novom opremom iz Kine za borbu protiv COVID19 virusa.', 'Infektivna klinika'),
    ('Partizanska 72', 'Radiologija predstavlja novi objekat kliničkog centra i sadrži najnovije uređaje.', 'Radiologija');

INSERT INTO room (room_number, type, clinic_id) VALUES
    ('10001', 'EXAMINATION', 1),
    ('10002', 'EXAMINATION', 1),
    ('10003', 'OPERATION', 1),
    ('10010', 'OPERATION', 1),
    ('10011', 'OPERATION', 1),
    ('10020', 'EXAMINATION', 1),
    ('10021', 'EXAMINATION', 1),
    ('10022', 'EXAMINATION', 1),
    ('20001', 'EXAMINATION', 2),
    ('20002', 'EXAMINATION', 2),
    ('20010', 'EXAMINATION', 2),
    ('20011', 'EXAMINATION', 2),
    ('20012', 'EXAMINATION', 2),
    ('20013', 'EXAMINATION', 2),
    ('30001', 'EXAMINATION', 3),
    ('30002', 'EXAMINATION', 3),
    ('30003', 'EXAMINATION', 3),
    ('30004', 'EXAMINATION', 3),
    ('30010', 'EXAMINATION', 3),
    ('30011', 'EXAMINATION', 3),
    ('30012', 'OPERATION', 3),
    ('30013', 'OPERATION', 3);

INSERT INTO clinic_admin (
    id,
    email_address,
    password,
    last_password_reset_date,
    first_name,
    last_name,
    jmbg,
    address,
    city,
    country,
    contact,
    active,
    clinic_id
) VALUES (
    2, 'ccadmin@gmail.com', '$2a$10$SHepdD5KBoQUkeVLwEJmvu90794GPxBLZ2Ps0hWttClzrM8QGcd4.',
    CURRENT_TIMESTAMP(),
    'Jovan', 'Obradović', '21129796554639', 'Bulevar Oslobođenja 24', 'Novi Sad', 'Srbija', '063546789', TRUE, 1
);

INSERT INTO doctor (
    id,
    email_address,
    password,
    last_password_reset_date,
    first_name,
    last_name,
    jmbg,
    address,
    city,
    country,
    contact,
    active,
    clinic_id
) VALUES (
    3, 'zdravko.dugi@gmail.com', '$2a$10$SHepdD5KBoQUkeVLwEJmvu90794GPxBLZ2Ps0hWttClzrM8QGcd4.',
    CURRENT_TIMESTAMP(),
    'Zdravko', 'Dugonjić', '0402989200984', 'Lenjinova 26', 'Veternik', 'Srbija', '0625467890', TRUE, 1
),
(
    4, 'rodusek021@gmail.com', '$2a$10$SHepdD5KBoQUkeVLwEJmvu90794GPxBLZ2Ps0hWttClzrM8QGcd4.',
    CURRENT_TIMESTAMP(),
    'Vladimir', 'Rodušek', '0511989800018', 'Svetog Save 15', 'Srbobran', 'Srbija', '0615679087', TRUE, 2
);

INSERT INTO nurse (
    id,
    email_address,
    password,
    last_password_reset_date,
    first_name,
    last_name,
    jmbg,
    address,
    city,
    country,
    contact,
    active,
    clinic_id
) VALUES (
    5, 'nevena@gmail.com', '$2a$10$SHepdD5KBoQUkeVLwEJmvu90794GPxBLZ2Ps0hWttClzrM8QGcd4.',
    CURRENT_TIMESTAMP(),
    'Nevena', 'Nevenić', '2506982201967', 'Bulevar Evrope 22', 'Novi Sad', 'Srbija', '065125998', TRUE, 1
),
(
    6, 'petar.p@gmail.com', '$2a$10$SHepdD5KBoQUkeVLwEJmvu90794GPxBLZ2Ps0hWttClzrM8QGcd4.',
    CURRENT_TIMESTAMP(),
    'Petar', 'Filipović', '2506987201967', 'Gajeva 10', 'Novi Sad', 'Srbija', '0664523345', TRUE, 1
);

INSERT INTO patient (
    id,
    email_address,
    password,
    last_password_reset_date,
    first_name,
    last_name,
    jmbg,
    address,
    city,
    country,
    contact,
    active
) VALUES (
    7, 'patient@gmail.com', '$2a$10$SHepdD5KBoQUkeVLwEJmvu90794GPxBLZ2Ps0hWttClzrM8QGcd4.',
    CURRENT_TIMESTAMP(),
    'Obrad', 'Obradović', '1609965568970', 'Resavska 15', 'Kulpin', 'Srbija', '0627689098', TRUE
),
(
    8, 'pacijent@gmail.com', '$2a$10$SHepdD5KBoQUkeVLwEJmvu90794GPxBLZ2Ps0hWttClzrM8QGcd4.',
    CURRENT_TIMESTAMP(),
    'Goran', 'Miković', '1601975000012', 'Patrijarha Pavla 52', 'Bački Petrovac', 'Srbija', '0634567789', TRUE
);

INSERT INTO drug (name) VALUES
    ('Penicilin'),
    ('Nolpaza'),
    ('Aspirin'),
    ('Brufen'),
    ('Klometol'),
    ('Ranisan'),
    ('Humani insulin'),
    ('Bensedin'),
    ('Nafazol kapi'),
    ('Bisoprolol'),
    ('Enterofuryl'),
    ('Amoksicilin');

INSERT INTO medical_diagnosis (name) VALUES
    ('Zapaljenje tankog creva koje uzrokuje Campylobacter'),
    ('Zapaljenje tankog creva koje uzrokuje Yersinia enterocolitica'),
    ('Trovanje hranom uzrokovano stafilokokom'),
    ('Tuberkuloza polno-mokraćnih kanala'),
    ('Urolitijaza'),
    ('Bubrežna insuficijencija'),
    ('Zapaljenje mokraćne bešike'),
    ('Živčani zastoj mokraće u bešici'),
    ('Vrećasto proširenje zida mokraćne bešike'),
    ('Deformacija prsta šake'),
    ('Patološko iščašenje i nepotpuno iščašenje zgloba'),
    ('Maligne neoplazme'),
    ('Promena zgloba, neoznačena'),
    ('Tumor'),
    ('Corona virus'),
    ('Prehlada'),
    ('Crevna infekcija uzrokovana enterohemoragijskom Escherichia coli'),
    ('Lokalizovane infekcije uzrokovane salmonelom');

INSERT INTO examination_type (name, price) VALUES
    ('Opšta praksa', 1000.0),
    ('Ultrazvuk', 2000.0),
    ('Laboratorija', 1500.0),
    ('Sistematski pregled', 1300.0),
    ('Urološki pregled', 1200.0),
    ('Urologija', 10000.0),
    ('Kardiološki pregled', 1400.0),
    ('Kardiološka hirurgija', 12400.0),
    ('Ginekološki pregled', 1700.0),
    ('Ortopedski pregled', 1000.0),
    ('Ortopedija', 8900.0),
    ('Stomatološki pregled', 1400.0),
    ('CT snimanje', 3500.0),
    ('Magnetna rezonanca', 4000.0),
    ('Maksiofacijalna hirurgija', 18000.0);
