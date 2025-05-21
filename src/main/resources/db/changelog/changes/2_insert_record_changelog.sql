-- creating function to encode password to BCrypt
create extension if not exists pgcrypto;

-- creating function to mask bank cards
CREATE OR REPLACE FUNCTION mask_card(card_number TEXT)
    RETURNS TEXT
    LANGUAGE plpgsql AS $$
BEGIN
    -- This regular expression replaces the first 12 digits with asterisks and retains the last 4 digits.
    RETURN regexp_replace(card_number, '(\d{12})(\d{4})', '************\2');
END;
$$;

-- Insert Users (30 entries with plain text passwords)
INSERT INTO users (username, password, firstname, lastname, passport_no, birth_date, role)
VALUES ('morsetimothy@moreno.com', crypt('password1',gen_salt('bf',10)), 'Jaclyn', 'Mcgee', 'Qs01738880', '2004-12-05', 'USER');
INSERT INTO users (username, password, firstname, lastname, passport_no, birth_date, role)
VALUES ('xfitzgerald@phillips.biz', crypt('password2',gen_salt('bf',10)), 'Robert', 'Wright', 'lV88205969', '1976-08-08', 'USER');
INSERT INTO users (username, password, firstname, lastname, passport_no, birth_date, role)
VALUES ('joellyons@williams-mcconnell.biz', crypt('password3',gen_salt('bf',10)), 'Alicia', 'Martin', 'CH76433901', '1978-01-08', 'USER');
INSERT INTO users (username, password, firstname, lastname, passport_no, birth_date, role)
VALUES ('joshuachavez@brown.org', crypt('password4',gen_salt('bf',10)), 'Anita', 'Jones', 'cB68775464', '1997-11-14', 'USER');
INSERT INTO users (username, password, firstname, lastname, passport_no, birth_date, role)
VALUES ('ofoster@guzman.net', crypt('password5',gen_salt('bf',10)), 'Robert', 'West', 'pY45985774', '1961-06-14', 'ADMIN');
INSERT INTO users (username, password, firstname, lastname, passport_no, birth_date, role)
VALUES ('tony84@singh.com', crypt('password6',gen_salt('bf',10)), 'Nicholas', 'Mcgee', 'il57968668', '1997-05-24', 'USER');
INSERT INTO users (username, password, firstname, lastname, passport_no, birth_date, role)
VALUES ('scottbush@tran-johnson.org', crypt('password7',gen_salt('bf',10)), 'Lisa', 'Ramirez', 'AX49288264', '1966-01-11', 'ADMIN');
INSERT INTO users (username, password, firstname, lastname, passport_no, birth_date, role)
VALUES ('jgrant@li.com', crypt('password8',gen_salt('bf',10)), 'Adam', 'Vazquez', 'jt58004463', '1994-03-09', 'USER');
INSERT INTO users (username, password, firstname, lastname, passport_no, birth_date, role)
VALUES ('jennifer00@estes-rowe.com', crypt('password9',gen_salt('bf',10)), 'Kevin', 'Cole', 'kZ57097494', '1972-09-25', 'USER');
INSERT INTO users (username, password, firstname, lastname, passport_no, birth_date, role)
VALUES ('tracy07@bryant.com', crypt('password10',gen_salt('bf',10)), 'Reginald', 'Hudson', 'JX27596750', '1980-08-07', 'USER');
INSERT INTO users (username, password, firstname, lastname, passport_no, birth_date, role)
VALUES ('daisy40@hotmail.com', crypt('password11',gen_salt('bf',10)), 'Nancy', 'Ellis', 'MM81767759', '1991-06-15', 'USER');
INSERT INTO users (username, password, firstname, lastname, passport_no, birth_date, role)
VALUES ('smithtaylor@gmail.com', crypt('password12',gen_salt('bf',10)), 'Christine', 'Wright', 'jP30751645', '1974-06-15', 'USER');
INSERT INTO users (username, password, firstname, lastname, passport_no, birth_date, role)
VALUES ('bjones@torres.com', crypt('password13',gen_salt('bf',10)), 'Anthony', 'Oconnor', 'RB67769398', '1991-12-03', 'USER');
INSERT INTO users (username, password, firstname, lastname, passport_no, birth_date, role)
VALUES ('wwilliams@morales.info', crypt('password14',gen_salt('bf',10)), 'Amber', 'Larsen', 'VV03949970', '1962-10-13', 'USER');
INSERT INTO users (username, password, firstname, lastname, passport_no, birth_date, role)
VALUES ('david43@yahoo.com', crypt('password15',gen_salt('bf',10)), 'Daniel', 'Reyes', 'MW24365518', '1987-12-09', 'USER');
INSERT INTO users (username, password, firstname, lastname, passport_no, birth_date, role)
VALUES ('sreynolds@yahoo.com', crypt('password16',gen_salt('bf',10)), 'Connie', 'Bates', 'YD08488549', '1972-11-20', 'USER');
INSERT INTO users (username, password, firstname, lastname, passport_no, birth_date, role)
VALUES ('kmorris@yahoo.com', crypt('password17',gen_salt('bf',10)), 'Lori', 'Wilcox', 'Vn23967691', '1972-04-07', 'USER');
INSERT INTO users (username, password, firstname, lastname, passport_no, birth_date, role)
VALUES ('katherine31@yahoo.com', crypt('password18',gen_salt('bf',10)), 'Jack', 'Roberts', 'et60617681', '2003-06-18', 'ADMIN');
INSERT INTO users (username, password, firstname, lastname, passport_no, birth_date, role)
VALUES ('grantjeffrey@yahoo.com', crypt('password19',gen_salt('bf',10)), 'Kevin', 'Freeman', 'Ld47496695', '1968-12-16', 'USER');
INSERT INTO users (username, password, firstname, lastname, passport_no, birth_date, role)
VALUES ('wvaughan@gmail.com', crypt('password20',gen_salt('bf',10)), 'Mary', 'Bell', 'ue23517732', '1995-12-18', 'USER');
INSERT INTO users (username, password, firstname, lastname, passport_no, birth_date, role)
VALUES ('cynthia24@pacheco.com', crypt('password21',gen_salt('bf',10)), 'Cristina', 'Gibson', 'Fg83525695', '2006-07-26', 'USER');
INSERT INTO users (username, password, firstname, lastname, passport_no, birth_date, role)
VALUES ('bsingh@solomon.org', crypt('password22',gen_salt('bf',10)), 'Diana', 'Collins', 'Hn72442161', '1961-09-04', 'USER');
INSERT INTO users (username, password, firstname, lastname, passport_no, birth_date, role)
VALUES ('schneiderelizabeth@freeman.com', crypt('password23',gen_salt('bf',10)), 'Jacob', 'Martinez', 'JT20813595', '1974-07-28', 'USER');
INSERT INTO users (username, password, firstname, lastname, passport_no, birth_date, role)
VALUES ('daniel15@yahoo.com', crypt('password24',gen_salt('bf',10)), 'Lori', 'Webb', 'ys62933337', '1964-05-13', 'USER');
INSERT INTO users (username, password, firstname, lastname, passport_no, birth_date, role)
VALUES ('adelgado@gmail.com', crypt('password25',gen_salt('bf',10)), 'Kristopher', 'Wilson', 'Ef66812756', '1984-10-24', 'USER');
INSERT INTO users (username, password, firstname, lastname, passport_no, birth_date, role)
VALUES ('vmurray@clark.com', crypt('password26',gen_salt('bf',10)), 'Kristen', 'Cruz', 'Ll39939442', '1976-03-09', 'USER');
INSERT INTO users (username, password, firstname, lastname, passport_no, birth_date, role)
VALUES ('fwalsh@brown-monroe.com', crypt('password27',gen_salt('bf',10)), 'Tyler', 'Jackson', 'wr91284903', '1961-05-24', 'USER');
INSERT INTO users (username, password, firstname, lastname, passport_no, birth_date, role)
VALUES ('karamcmahon@yahoo.com', crypt('password28',gen_salt('bf',10)), 'Wanda', 'Best', 'VN06357299', '1972-05-25', 'ADMIN');
INSERT INTO users (username, password, firstname, lastname, passport_no, birth_date, role)
VALUES ('josephnorman@brown.com', crypt('password29',gen_salt('bf',10)), 'Ryan', 'Valentine', 'AI17582488', '1990-02-03', 'USER');
INSERT INTO users (username, password, firstname, lastname, passport_no, birth_date, role)
VALUES ('ebrowning@pham.biz', crypt('password30',gen_salt('bf',10)), 'Jason', 'Baker', 'Up76243644', '1985-08-03', 'USER');

-- Insert Cards (45 entries)
INSERT INTO card (card_number, expire_date, card_status, balance, user_id)
VALUES ('4037231367751062', '2027-11-12 04:21:45', 'BLOCKED', 1552.56, 8);
INSERT INTO card (card_number, expire_date, card_status, balance, user_id)
VALUES ('4624235301416', '2026-05-17 04:21:45', 'BLOCKED', 4749.14, 9);
INSERT INTO card (card_number, expire_date, card_status, balance, user_id)
VALUES ('4858467088149', '2027-06-16 04:21:45', 'ACTIVE', 4116.17, 19);
INSERT INTO card (card_number, expire_date, card_status, balance, user_id)
VALUES ('3539430205911103', '2026-01-21 04:21:45', 'ACTIVE', 7281.51, 10);
INSERT INTO card (card_number, expire_date, card_status, balance, user_id)
VALUES ('4580031365387', '2024-09-18 04:21:45', 'ACTIVE', 2503.32, 23);
INSERT INTO card (card_number, expire_date, card_status, balance, user_id)
VALUES ('675900247650', '2027-10-03 04:21:45', 'EXPIRED', 8839.9, 10);
INSERT INTO card (card_number, expire_date, card_status, balance, user_id)
VALUES ('2226177873201958', '2029-12-31 04:21:45', 'BLOCKED', 7929.35, 4);
INSERT INTO card (card_number, expire_date, card_status, balance, user_id)
VALUES ('213129801954710', '2028-02-17 04:21:45', 'BLOCKED', 8819.03, 29);
INSERT INTO card (card_number, expire_date, card_status, balance, user_id)
VALUES ('6011267636240946', '2027-11-12 04:21:45', 'ACTIVE', 6443.84, 26);
INSERT INTO card (card_number, expire_date, card_status, balance, user_id)
VALUES ('36667456481690', '2026-03-22 04:21:45', 'EXPIRED', 3143.91, 23);
INSERT INTO card (card_number, expire_date, card_status, balance, user_id)
VALUES ('3580985974554960', '2026-03-16 04:21:45', 'BLOCKED', 1009.42, 15);
INSERT INTO card (card_number, expire_date, card_status, balance, user_id)
VALUES ('6011563285624251', '2025-06-25 04:21:45', 'BLOCKED', 8623.81, 20);
INSERT INTO card (card_number, expire_date, card_status, balance, user_id)
VALUES ('060457098071', '2029-06-20 04:21:45', 'EXPIRED', 9372.06, 11);
INSERT INTO card (card_number, expire_date, card_status, balance, user_id)
VALUES ('4497645890070972921', '2028-05-03 04:21:45', 'EXPIRED', 3948.77, 11);
INSERT INTO card (card_number, expire_date, card_status, balance, user_id)
VALUES ('4024337704239', '2026-07-17 04:21:45', 'BLOCKED', 535.1, 12);
INSERT INTO card (card_number, expire_date, card_status, balance, user_id)
VALUES ('4118165025254238', '2026-06-07 04:21:45', 'ACTIVE', 9139.36, 14);
INSERT INTO card (card_number, expire_date, card_status, balance, user_id)
VALUES ('180094197852432', '2025-09-05 04:21:45', 'EXPIRED', 1727.61, 23);
INSERT INTO card (card_number, expire_date, card_status, balance, user_id)
VALUES ('4082823009510809', '2029-07-03 04:21:45', 'BLOCKED', 8258.06, 22);
INSERT INTO card (card_number, expire_date, card_status, balance, user_id)
VALUES ('378288375622706', '2029-10-12 04:21:45', 'ACTIVE', 4252.31, 10);
INSERT INTO card (card_number, expire_date, card_status, balance, user_id)
VALUES ('3558353747633603', '2028-03-20 04:21:45', 'ACTIVE', 3474.4, 3);
INSERT INTO card (card_number, expire_date, card_status, balance, user_id)
VALUES ('213168316127719', '2027-01-02 04:21:45', 'ACTIVE', 9199.72, 15);
INSERT INTO card (card_number, expire_date, card_status, balance, user_id)
VALUES ('213149158009884', '2029-02-16 04:21:45', 'BLOCKED', 190.02, 3);
INSERT INTO card (card_number, expire_date, card_status, balance, user_id)
VALUES ('5164153533043186', '2027-04-25 04:21:45', 'BLOCKED', 8751.01, 7);
INSERT INTO card (card_number, expire_date, card_status, balance, user_id)
VALUES ('4206549313685109', '2029-07-31 04:21:45', 'BLOCKED', 1666.51, 28);
INSERT INTO card (card_number, expire_date, card_status, balance, user_id)
VALUES ('377028564950141', '2027-04-24 04:21:45', 'BLOCKED', 5995.6, 9);
INSERT INTO card (card_number, expire_date, card_status, balance, user_id)
VALUES ('3515117925266508', '2025-06-13 04:21:45', 'EXPIRED', 7921.41, 7);
INSERT INTO card (card_number, expire_date, card_status, balance, user_id)
VALUES ('5373501741355957', '2028-03-16 04:21:45', 'BLOCKED', 7874.77, 26);
INSERT INTO card (card_number, expire_date, card_status, balance, user_id)
VALUES ('4130974522544536', '2024-09-06 04:21:45', 'BLOCKED', 9210.7, 6);
INSERT INTO card (card_number, expire_date, card_status, balance, user_id)
VALUES ('502009913299', '2025-04-24 04:21:45', 'BLOCKED', 6392.05, 20);
INSERT INTO card (card_number, expire_date, card_status, balance, user_id)
VALUES ('4458934920230405', '2028-05-27 04:21:45', 'EXPIRED', 5912.89, 28);
INSERT INTO card (card_number, expire_date, card_status, balance, user_id)
VALUES ('349673329075752', '2027-03-26 04:21:45', 'BLOCKED', 8862.02, 11);
INSERT INTO card (card_number, expire_date, card_status, balance, user_id)
VALUES ('213106910856994', '2027-10-19 04:21:45', 'EXPIRED', 4592.38, 17);
INSERT INTO card (card_number, expire_date, card_status, balance, user_id)
VALUES ('377249192567723', '2025-10-04 04:21:45', 'BLOCKED', 318.01, 9);
INSERT INTO card (card_number, expire_date, card_status, balance, user_id)
VALUES ('583627167732', '2028-04-19 04:21:45', 'ACTIVE', 3629.48, 30);
INSERT INTO card (card_number, expire_date, card_status, balance, user_id)
VALUES ('4491530027342114385', '2027-05-05 04:21:45', 'ACTIVE', 6519.8, 17);
INSERT INTO card (card_number, expire_date, card_status, balance, user_id)
VALUES ('36537705935426', '2028-10-11 04:21:45', 'BLOCKED', 75.85, 26);
INSERT INTO card (card_number, expire_date, card_status, balance, user_id)
VALUES ('4535344904667233', '2024-11-07 04:21:45', 'BLOCKED', 203.59, 9);
INSERT INTO card (card_number, expire_date, card_status, balance, user_id)
VALUES ('562970171509', '2025-07-15 04:21:45', 'EXPIRED', 5862.51, 11);
INSERT INTO card (card_number, expire_date, card_status, balance, user_id)
VALUES ('4086523316728880', '2029-06-28 04:21:45', 'BLOCKED', 903.48, 30);
INSERT INTO card (card_number, expire_date, card_status, balance, user_id)
VALUES ('6011975403379677', '2028-07-04 04:21:45', 'EXPIRED', 3226.53, 4);
INSERT INTO card (card_number, expire_date, card_status, balance, user_id)
VALUES ('30296620561991', '2028-10-10 04:21:45', 'BLOCKED', 4186.08, 15);
INSERT INTO card (card_number, expire_date, card_status, balance, user_id)
VALUES ('6011570549294417', '2024-07-18 04:21:45', 'BLOCKED', 6038.75, 23);
INSERT INTO card (card_number, expire_date, card_status, balance, user_id)
VALUES ('676277831472', '2026-08-24 04:21:45', 'BLOCKED', 1190.47, 16);
INSERT INTO card (card_number, expire_date, card_status, balance, user_id)
VALUES ('4469327206605852773', '2027-04-26 04:21:45', 'BLOCKED', 9291.32, 13);
INSERT INTO card (card_number, expire_date, card_status, balance, user_id)
VALUES ('3590278840124061', '2028-11-26 04:21:45', 'ACTIVE', 6667.59, 28);



